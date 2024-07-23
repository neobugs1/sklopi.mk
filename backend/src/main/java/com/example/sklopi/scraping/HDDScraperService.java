package com.example.sklopi.scraping;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.Storage;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.StorageService;
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
public class HDDScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveHDDs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.anhoch.com/categories/interni-hdd/products?brand=&attribute=&toPrice=274980&inStockOnly=1&sort=latest&perPage=100&page=1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));

            List<WebElement> hddElements = driver.findElements(By.cssSelector(".product-card"));

            if (hddElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                List<String> knownBrands = scrapeBrands(driver);

                Part hddPart = partService.findByName("Storage").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Storage");
                    return partService.savePart(newPart);
                });

                for (WebElement hddElement : hddElements) {
                    String imageUrl = hddElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = hddElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");
                    String name = hddElement.findElement(By.cssSelector(".product-name h6")).getText();

                    WebElement priceElement = hddElement.findElement(By.cssSelector(".product-card-bottom div"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
                    int price = Integer.parseInt(cleanedPriceString);

                    String formFactor = determineFormFactor(name);
                    String brand = determineBrand(name, knownBrands);
                    int capacity = determineCapacity(name);
                    String type = "HDD"; // Set the type to HDD

                    if (brand != null && capacity > 0) {
                        Optional<Storage> partModel = determineAndSavePartModel(name, hddPart, formFactor, brand, capacity, type);

                        if (partModel.isPresent()) {
                            Product product = new Product();
                            product.setName(name);
                            product.setImageUrl(imageUrl);
                            product.setProductUrl(productUrl);
                            product.setPrice(price);
                            product.setPart(hddPart);
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
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".filter-brands .form-check label")));

        List<WebElement> brandElements = driver.findElements(By.cssSelector(".filter-brands .form-check label"));
        for (WebElement brandElement : brandElements) {
            brands.add(brandElement.getAttribute("textContent").trim());
        }
        return brands;
    }

    private String determineFormFactor(String productName) {
        if (productName.contains("2.5\"")) {
            return "2.5\"";
        } else if (productName.contains("3.5\"")) {
            return "3.5\"";
        }
        // Add more form factors as necessary
        return "Unknown";
    }

    private String determineBrand(String productName, List<String> knownBrands) {
        for (String brand : knownBrands) {
            if (productName.contains(brand)) {
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

    private Optional<Storage> determineAndSavePartModel(String productName, Part hddPart, String formFactor, String brand, int capacity, String type) {
        List<Storage> partModels = storageService.findAll();

        for (Storage model : partModels) {
            if (model.getFormFactor().equals(formFactor) && model.getName().equals(brand) &&
                    model.getCapacity() == capacity && model.getType().equals(type)) {
                return Optional.of(model);
            }
        }

        Storage newStorage = new Storage(brand, hddPart, formFactor, capacity, type);
        return Optional.of(storageService.save(newStorage));
    }
}

