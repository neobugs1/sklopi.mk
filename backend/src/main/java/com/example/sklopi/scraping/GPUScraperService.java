package com.example.sklopi.scraping;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import com.example.sklopi.repository.PartModelRepository;
import com.example.sklopi.repository.PartRepository;
import com.example.sklopi.repository.ProductRepository;

import com.example.sklopi.service.PartModelService;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Service
public class GPUScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private PartModelService partModelService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveGPUs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.anhoch.com/categories/grafichki-karti/products?brand=&attribute=&toPrice=274980&inStockOnly=1&sort=priceHighToLow&perPage=100&page=1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));

            List<WebElement> gpuElements = driver.findElements(By.cssSelector(".product-card"));

            if (gpuElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                // Ensure GPU part exists
                Part gpuPart = partService.findByName("GPU").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("GPU");
                    partService.savePart(newPart);
                    return newPart;
                });

                for (WebElement gpuElement : gpuElements) {
                    String imageUrl = gpuElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = gpuElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");
                    String name = gpuElement.findElement(By.cssSelector(".product-name h6")).getText();

                    WebElement priceElement = gpuElement.findElement(By.cssSelector(".product-card-bottom div"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
                    int price = Integer.parseInt(cleanedPriceString);

                    // Determine the part model
                    String partModelName = determinePartModelName(name);

                    PartModel partModel = partModelService.findByNameAndPart(partModelName, gpuPart)
                            .orElseGet(() -> {
                                PartModel newPartModel = new PartModel();
                                newPartModel.setName(partModelName);
                                newPartModel.setPart(gpuPart);
                                partModelService.savePartModel(newPartModel);
                                return newPartModel;
                            });

                    // Check if the product already exists
                    Optional<Product> existingProduct = productService.findByName(name);
                    if (existingProduct.isPresent()) {
                        Product product = existingProduct.get();
                        product.setPrice(price);
                        product.setImageUrl(imageUrl);
                        product.setProductUrl(productUrl);
                        productService.save(product);
                        System.out.println("Updated product: " + name);
                    } else {
                        Product product = new Product();
                        product.setName(name);
                        product.setImageUrl(imageUrl);
                        product.setProductUrl(productUrl);
                        product.setPrice(price);
                        product.setPart(gpuPart);
                        product.setPartModel(partModel);
                        productService.save(product);
                        System.out.println("Saved product: " + name);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    private String determinePartModelName(String productName) {
        if (productName.contains("RTX 3060 Ti")) {
            return "RTX 3060 Ti";
        } else if (productName.contains("RTX 3070")) {
            return "RTX 3070";
        } else if (productName.contains("GTX 1650")) {
            return "GTX 1650";
        } else if (productName.contains("RX 7600")) {
            return "RX 7600";
        }
        return "Unknown Model";
    }
}
