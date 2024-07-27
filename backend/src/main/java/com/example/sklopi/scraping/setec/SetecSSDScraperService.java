package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.Storage;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.StorageService;
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
public class SetecSSDScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSave25InchSSDs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/тврди-дискови-и-ssd-дискови?mfp=111-form-factor%5BSATA%5D&limit=100");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> ssdElements = driver.findElements(By.cssSelector(".product"));

            if (ssdElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                List<String> knownBrands = scrapeBrands(driver);

                Part ssdPart = partService.findByName("Storage").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Storage");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement ssdElement : ssdElements) {
                    js.executeScript("arguments[0].scrollIntoView(true);", ssdElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = ssdElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        continue;
                    }

                    String imageUrl = ssdElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = ssdElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = ssdElement.findElement(By.cssSelector(".name a")).getText();

                    WebElement priceElement = null;
                    try {
                        priceElement = ssdElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        try {
                            priceElement = ssdElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    String formFactor = "2.5\""; // Fixed form factor for this scraper
                    String brand = determineBrand(name, knownBrands);
                    int capacity = determineCapacity(name);
                    String type = "SSD"; // Set the type to SSD

                    if (priceElement != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);
                        if (brand != null && capacity > 0) {
                            Optional<Storage> partModel = determineAndSavePartModel(name, ssdPart, formFactor, brand, capacity, type);

                            if (partModel.isPresent()) {
                                Product product = new Product();
                                product.setName(name);
                                product.setImageUrl(imageUrl);
                                product.setProductUrl(productUrl);
                                product.setPrice(price);
                                product.setPart(ssdPart);
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
        if (productName.toLowerCase().contains("hyperx")) {
            return "Kingston";
        }
        String lowerCaseProductName = productName.toLowerCase();
        for (String brand : knownBrands) {
            if (lowerCaseProductName.contains(brand.toLowerCase())) {
                return brand;
            }
        }
        return null;
    }

    private int determineCapacity(String productName) {
        Pattern pattern = Pattern.compile("(\\d+)(GB|TB)");
        Matcher matcher = pattern.matcher(productName);
        if (matcher.find()) {
            int size = Integer.parseInt(matcher.group(1));
            if (matcher.group(2).equals("TB")) {
                size *= 1024; // Convert TB to GB
            }
            return size;
        }
        return 0;
    }

    private Optional<Storage> determineAndSavePartModel(String productName, Part ssdPart, String formFactor, String brand, int capacity, String type) {
        List<Storage> partModels = storageService.findAll();

        for (Storage model : partModels) {
            if (model.getFormFactor().equals(formFactor) && model.getName().equals(brand) &&
                    model.getCapacity() == capacity && model.getType().equals(type)) {
                return Optional.of(model);
            }
        }

        Storage newStorage = new Storage(brand, ssdPart, formFactor, capacity, type);
        return Optional.of(storageService.save(newStorage));
    }
}
