package com.example.sklopi.scraping;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
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

import java.time.Duration;
import java.util.Comparator;
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
                    return partService.savePart(newPart);
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
                    Optional<PartModel> partModelOptional = determinePartModel(name, gpuPart);

                    if (partModelOptional.isPresent()) {
                        PartModel partModel = partModelOptional.get();

                        // Save or update product
                        Product product = new Product();
                        product.setName(name);
                        product.setImageUrl(imageUrl);
                        product.setProductUrl(productUrl);
                        product.setPrice(price);
                        product.setPart(gpuPart);
                        product.setPartModel(partModel);
                        productService.saveProduct(product);
                        System.out.println("Processed product: " + name);
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

    private Optional<PartModel> determinePartModel(String productName, Part gpuPart) {
        List<PartModel> partModels = partModelService.findByPart(gpuPart);

        // Sort part models by length in descending order
        partModels.sort(Comparator.comparingInt((PartModel model) -> model.getName().length()).reversed());

        for (PartModel model : partModels) {
            if (productName.contains(model.getName())) {
                return Optional.of(model);
            }
        }
        return Optional.empty();
    }
}