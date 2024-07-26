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
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SetecHDDScraperService {

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
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/тврди-дискови-и-ssd-дискови?mfp=111-form-factor[3.5%20инчи],93-[HDD]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> hddElements = driver.findElements(By.cssSelector(".product"));

            if (hddElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                Part hddPart = partService.findByName("Storage").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Storage");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement hddElement : hddElements) {
                    js.executeScript("arguments[0].scrollIntoView(true);", hddElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = hddElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        continue;
                    }

                    String imageUrl = hddElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = hddElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = hddElement.findElement(By.cssSelector(".name a")).getText();

                    WebElement priceElement = null;
                    try {
                        priceElement = hddElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        try {
                            priceElement = hddElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    String brand = determineBrand(name);
                    int capacity = determineCapacity(name);
                    String type = "HDD"; // Set the type to HDD

                    if (priceElement != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);
                        if (brand != null && capacity > 0) {
                            Optional<Storage> partModel = determineAndSavePartModel(name, hddPart, brand, capacity, type);

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
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private String determineBrand(String productName) {
        String[] words = productName.split(" ");
        if (words.length >= 3) {
            return words[2]; // Assuming the brand is the third word
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

    private Optional<Storage> determineAndSavePartModel(String productName, Part hddPart, String brand, int capacity, String type) {
        List<Storage> partModels = storageService.findAll();

        for (Storage model : partModels) {
            if (model.getName().equals(brand) && model.getCapacity() == capacity && model.getType().equals(type)) {
                return Optional.of(model);
            }
        }

        Storage newStorage = new Storage(brand, hddPart, "3.5\"", capacity, type);
        return Optional.of(storageService.save(newStorage));
    }
}
