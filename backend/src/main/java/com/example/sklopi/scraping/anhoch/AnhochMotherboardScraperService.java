package com.example.sklopi.scraping.anhoch;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.MotherboardService;
import org.openqa.selenium.*;
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
public class AnhochMotherboardScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private MotherboardService motherboardService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveMotherboards() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.anhoch.com/categories/matichni-plochi/products?brand=&attribute=&toPrice=274980&inStockOnly=1&sort=latest&perPage=100&page=1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));

            List<WebElement> motherboardElements = driver.findElements(By.cssSelector(".product-card"));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            List<WebElement> gpuElements = driver.findElements(By.cssSelector(".product-card"));
            Thread.sleep(1000);
            dismissPopup(driver);
            Thread.sleep(1000);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(200);

            if (motherboardElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                Part motherboardPart = partService.findByName("Motherboard").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Motherboard");
                    return partService.savePart(newPart);
                });

                for (WebElement motherboardElement : motherboardElements) {
                    String imageUrl = motherboardElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = motherboardElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");
                    String name = motherboardElement.findElement(By.cssSelector(".product-name h6")).getText();

                    WebElement priceElement = motherboardElement.findElement(By.cssSelector(".product-card-bottom div"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
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
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private void dismissPopup(WebDriver driver) {
        try {
            WebElement popupBanner = driver.findElement(By.cssSelector(".popup-banner-inner"));
            if (popupBanner.isDisplayed()) {
                WebElement closeButton = popupBanner.findElement(By.cssSelector("button[data-dismiss='modal']"));
                closeButton.click();
                System.out.println("Popup dismissed.");
            }
        } catch (NoSuchElementException e) {
            System.out.println("No popup banner found.");
        }
    }

    private Optional<Motherboard> determinePartModel(String productName, Part motherboardPart) {
        List<Motherboard> partModels = motherboardService.findAll();

        // Sort part models by length in descending order
        partModels.sort(Comparator.comparingInt((Motherboard model) -> model.getName().length()).reversed());

        for (Motherboard model : partModels) {
            if (productName.contains(model.getName())) {
                return Optional.of(model);
            }
        }
        return Optional.empty();
    }
}
