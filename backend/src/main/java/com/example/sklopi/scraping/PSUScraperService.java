package com.example.sklopi.scraping;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.PSUService;
import org.openqa.selenium.By;
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
public class PSUScraperService {

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
            driver.get("https://www.anhoch.com/categories/napojuvanja/products?brand=&attribute=&fromPrice=1500&toPrice=274980&inStockOnly=1&sort=latest&perPage=100&page=1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));

            List<WebElement> psuElements = driver.findElements(By.cssSelector(".product-card"));

            if (psuElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                List<String> knownBrands = scrapeBrands(driver);

                Part psuPart = partService.findByName("PSU").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("PSU");
                    return partService.savePart(newPart);
                });

                for (WebElement psuElement : psuElements) {
                    String imageUrl = psuElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = psuElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");
                    String name = psuElement.findElement(By.cssSelector(".product-name h6")).getText();

                    WebElement priceElement = psuElement.findElement(By.cssSelector(".product-card-bottom div"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
                    int price = Integer.parseInt(cleanedPriceString);

                    String brand = determineBrand(name, knownBrands);
                    String efficiencyRating = determineEfficiencyRating(name);
                    int wattage = determineWattage(name);

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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private List<String> scrapeBrands(WebDriver driver) {
        List<String> brands = new ArrayList<>();
        List<WebElement> brandElements = driver.findElements(By.cssSelector(".filter-brands .form-check label"));
        for (WebElement brandElement : brandElements) {
            String brand = brandElement.getAttribute("textContent").trim();
            if (brand.equalsIgnoreCase("SBOX")) { // Anhoch gi kategorizira "White Shark" kako SBOX
                brand = "White Shark";
            }
            brands.add(brand);
        }
        return brands;
    }

    private String determineBrand(String productName, List<String> knownBrands) {
        for (String brand : knownBrands) {
            if (productName.toLowerCase().contains(brand.toLowerCase())) {
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
        Pattern pattern = Pattern.compile("(\\d+)W", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(productName);
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
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
