package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.MotherboardService;
import com.example.sklopi.repository.PriceHistoryRepository;
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
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class SetecMotherboardScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private MotherboardService motherboardService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public void scrapeAndSaveMotherboards() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/матични-плочи?limit=100&mfp=price[3000,80000]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> motherboardElements = driver.findElements(By.cssSelector(".product"));

            if (motherboardElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                Part motherboardPart = partService.findByName("Motherboard").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Motherboard");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement motherboardElement : motherboardElements) {
                    // Scroll to load all images on the Setec website
                    js.executeScript("arguments[0].scrollIntoView(true);", motherboardElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = motherboardElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        // No stock information available, skip this product
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        // Product is out of stock, skip it
                        continue;
                    }

                    String imageUrl = motherboardElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = motherboardElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = motherboardElement.findElement(By.cssSelector(".name a")).getText();

                    WebElement priceElement = null;
                    try {
                        // Try to find the discounted price
                        priceElement = motherboardElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        // If the discounted price is not found, try to find the regular price in the new structure
                        try {
                            priceElement = motherboardElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            // If neither price is found, handle this case (e.g., set a default value or log a message)
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    if (priceElement != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);

                        Optional<Motherboard> partModelOptional = determinePartModel(name, motherboardPart);

                        if (partModelOptional.isPresent()) {
                            Motherboard partModel = partModelOptional.get();

                            Product product = new Product();
                            product.setName(name);
                            product.setImageUrl(imageUrl);
                            product.setProductUrl(productUrl);
                            product.setPrice(price);
                            product.setPart(motherboardPart);
                            product.setPartModel(partModel);

                            productService.saveProduct(product);
                            System.out.println("Processed product: " + name + " with price: " + price);
                        } else {
                            System.out.println("Skipped product (unknown model): " + name);
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

    private Optional<Motherboard> determinePartModel(String productName, Part motherboardPart) {
        List<Motherboard> partModels = motherboardService.findAll();

        partModels.sort(Comparator.comparingInt((Motherboard model) -> model.getName().length()).reversed());

        for (Motherboard model : partModels) {
            if (productName.contains(model.getName())) {
                return Optional.of(model);
            }
        }
        return Optional.empty();
    }
}
