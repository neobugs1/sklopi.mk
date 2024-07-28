package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.PSUService;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SetecPSUScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private PSUService psuService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSavePSUs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/напојувања?limit=100");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> psuElements = driver.findElements(By.cssSelector(".product"));

            if (psuElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                List<String> knownBrands = scrapeBrands(driver);
                knownBrands.add("ASUS");

                Part psuPart = partService.findByName("PSU").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("PSU");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement psuElement : psuElements) {
                    js.executeScript("arguments[0].scrollIntoView(true);", psuElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = psuElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        continue;
                    }

                    String imageUrl = psuElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = psuElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = psuElement.findElement(By.cssSelector(".name a")).getText();

                    WebElement priceElement = null;
                    try {
                        priceElement = psuElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        try {
                            priceElement = psuElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    String brand = determineBrand(name, knownBrands);
                    String efficiencyRating = determineEfficiencyRating(name);
                    int wattage = determineWattage(name);

                    if (priceElement != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);

                        if (brand != null && efficiencyRating != null && wattage > 0) {
                            Optional<PSU> partModel = determineAndSavePartModel(name, psuPart, brand, efficiencyRating, wattage);

                            if (partModel.isPresent()) {
                                Product product = new Product();
                                product.setName(name);
                                product.setImageUrl(imageUrl);
                                product.setProductUrl(productUrl);
                                product.setPrice(price);
                                product.setPart(psuPart);
                                product.setPartModel(partModel.get());

                                productService.saveProduct(product);
                                System.out.println("Processed product: " + name + " with price: " + price);
                            } else {
                                System.out.println("Skipped product (unknown model): " + name);
                            }
                        } else {
                            System.out.println("Skipped product (insufficient data): " + name);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private List<String> scrapeBrands(WebDriver driver) {
        List<String> brands = new ArrayList<>();
        List<WebElement> brandElements = driver.findElements(By.cssSelector(".mfilter-options-container label"));
        for (WebElement brandElement : brandElements) {
            String forAttribute = brandElement.getAttribute("for");
            if (forAttribute != null && forAttribute.contains("manufacturers")) {
                String brandName = brandElement.getText().trim();
                if (!brandName.isEmpty()) {
                    brands.add(brandName);
                }
            }
        }
        return brands;
    }

    private String determineBrand(String productName, List<String> knownBrands) {
        String lowerCaseProductName = productName.toLowerCase();
        for (String brand : knownBrands) {
            // Check both with and without spaces
            String lowerCaseBrand = brand.toLowerCase();
            String brandWithoutSpaces = lowerCaseBrand.replace(" ", "");

            if (lowerCaseProductName.contains(lowerCaseBrand) || lowerCaseProductName.contains(brandWithoutSpaces)) {
                return brand;
            }
        }
        return null;
    }

    private String determineEfficiencyRating(String productName) {
        if (productName.toLowerCase().contains("titanium")) {
            return "80+ Titanium";
        } else if (productName.toLowerCase().contains("platinum")) {
            return "80+ Platinum";
        } else if (productName.toLowerCase().contains("gold")) {
            return "80+ Gold";
        } else if (productName.toLowerCase().contains("silver")) {
            return "80+ Silver";
        } else if (productName.toLowerCase().contains("bronze")) {
            return "80+ Bronze";
        } else if (productName.toLowerCase().contains("black") || productName.toLowerCase().contains("white")) {
            return "80+";
        }
        return null;
    }

    private int determineWattage(String productName) {
        // Pattern to match wattage in various formats, ignoring values ending with 'V' (voltage)
        Pattern pattern = Pattern.compile("\\b(\\d{3})(?!V)\\b|\\b(\\d+)(W|w| Watt| watt|Watt|watt)\\b");
        Matcher matcher = pattern.matcher(productName);
        while (matcher.find()) {
            // Check the group that matched the wattage value
            String wattageValue = matcher.group(1) != null ? matcher.group(1) : matcher.group(2);
            if (wattageValue != null) {
                return Integer.parseInt(wattageValue);
            }
        }
        return 0;
    }

    private Optional<PSU> determineAndSavePartModel(String productName, Part psuPart, String brand, String efficiencyRating, int wattage) {
        return psuService.findByBrandAndEfficiencyRatingAndWattage(brand, efficiencyRating, wattage)
                .or(() -> {
                    PSU newPSU = new PSU(psuPart, brand, efficiencyRating, wattage);
                    return Optional.of(psuService.save(newPSU));
                });
    }
}
