package com.example.sklopi.scraping.anhoch;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.CPUService;
import com.example.sklopi.repository.PriceHistoryRepository;
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
public class AnhochCPUScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private CPUService cpuService;

    @Autowired
    private ProductService productService;

    @Autowired
    private PriceHistoryRepository priceHistoryRepository;

    public void scrapeAndSaveCPUs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.anhoch.com/categories/procesori/products?brand=&attribute=&toPrice=274980&inStockOnly=1&sort=latest&perPage=100&page=1"); // Replace with the actual URL

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card"))); // Update the selector as needed

            List<WebElement> cpuElements = driver.findElements(By.cssSelector(".product-card")); // Update the selector as needed

            JavascriptExecutor js = (JavascriptExecutor) driver;

            List<WebElement> gpuElements = driver.findElements(By.cssSelector(".product-card"));
            Thread.sleep(1000);
            dismissPopup(driver);
            Thread.sleep(1000);
            js.executeScript("window.scrollTo(0, document.body.scrollHeight);");
            Thread.sleep(200);

            if (cpuElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                Part cpuPart = partService.findByName("CPU").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("CPU");
                    return partService.savePart(newPart);
                });

                for (WebElement cpuElement : cpuElements) {
                    String imageUrl = cpuElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = cpuElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");
                    String name = cpuElement.findElement(By.cssSelector(".product-name h6")).getText();
                    name = name.replace("-", " ");

                    WebElement priceElement = cpuElement.findElement(By.cssSelector(".product-card-bottom div")); // Update the selector as needed
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
                    int price = Integer.parseInt(cleanedPriceString);

                    Optional<CPU> partModelOptional = determinePartModel(name, cpuPart);

                    if (partModelOptional.isPresent()) {
                        CPU partModel = partModelOptional.get();

                        Product product = new Product();
                        product.setName(name);
                        product.setImageUrl(imageUrl);
                        product.setProductUrl(productUrl);
                        product.setPrice(price);
                        product.setPart(cpuPart);
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

    private Optional<CPU> determinePartModel(String productName, Part cpuPart) {
        List<CPU> partModels = cpuService.findAll();

        // Sort part models by length in descending order
        partModels.sort(Comparator.comparingInt((CPU model) -> model.getName().length()).reversed());

        for (CPU model : partModels) {
            if (productName.contains(model.getName())) {
                return Optional.of(model);
            }
        }
        return Optional.empty();
    }
}
