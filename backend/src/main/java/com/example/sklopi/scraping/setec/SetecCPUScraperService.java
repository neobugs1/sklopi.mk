package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.CPUService;
import com.example.sklopi.repository.PriceHistoryRepository;
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
public class SetecCPUScraperService {

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
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/процесори?limit=100&mfp=price[3000,80000]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> cpuElements = driver.findElements(By.cssSelector(".product"));

            if (cpuElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                Part cpuPart = partService.findByName("CPU").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("CPU");
                    return partService.savePart(newPart);
                });

                for (WebElement cpuElement : cpuElements) {
                    String imageUrl = cpuElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = cpuElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = cpuElement.findElement(By.cssSelector(".name a")).getText();
                    name = name.replace("-", " "); // Replace dashes with spaces

                    WebElement priceElement = cpuElement.findElement(By.cssSelector(".category-price-akciska .price-new-new, .category-price-redovna .cena_za_kesh"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
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
