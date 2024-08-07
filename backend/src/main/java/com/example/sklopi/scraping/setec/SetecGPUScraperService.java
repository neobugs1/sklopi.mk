package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.GPUService;
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

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class SetecGPUScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private GPUService gpuService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveGPUs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/графички-карти?limit=100&mfp=price[9000,150999]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> gpuElements = driver.findElements(By.cssSelector(".product"));

            if (gpuElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                JavascriptExecutor js = (JavascriptExecutor) driver;

                Part gpuPart = partService.findByName("GPU").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("GPU");
                    return partService.savePart(newPart);
                });

                for (int i = 0; i < gpuElements.size(); i++) {
                    WebElement gpuElement = gpuElements.get(i);

                    js.executeScript("arguments[0].scrollIntoView(true);", gpuElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = gpuElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        // No stock information available, skip this product
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        // Product is out of stock, skip it
                        continue;
                    }

                    WebElement imageElement = gpuElement.findElement(By.cssSelector(".image .zoom-image-effect"));
                    String imageUrl = imageElement.getAttribute("src");

                    String productUrl = gpuElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = gpuElement.findElement(By.cssSelector(".name a")).getText();

                    String priceString = null;

                    try {
                        // Try to find the discounted price
                        WebElement discountPriceElement = gpuElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                        priceString = discountPriceElement.getText().trim();
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        // If the discounted price is not found, try to find the regular price in the new structure
                        try {
                            WebElement regularPriceElement = gpuElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                            priceString = regularPriceElement.getText().trim();
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            // If neither price is found, handle this case (e.g., set a default value or log a message)
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    if (priceString != null) {
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);

                        if (name.contains("-")) {
                            String extractedModel = extractGpuModel(name);
                            Optional<GPU> partModelOptional = determinePartModel(extractedModel, name, gpuPart);

                            if (partModelOptional.isPresent()) {
                                GPU partModel = partModelOptional.get();

                                Product product = new Product();
                                product.setName(name);
                                product.setImageUrl(imageUrl);
                                product.setProductUrl(productUrl);
                                product.setPrice(price);
                                product.setPart(gpuPart);
                                product.setPartModel(partModel);

                                productService.saveProduct(product);
                                System.out.println("Processed product: " + name + " with price: " + price);
                            } else {
                                System.out.println("Skipped product (unknown model): " + name);
                            }
                        } else {
                            System.out.println("Skipped product (no hyphen): " + name);
                        }
                    }

                    gpuElements = driver.findElements(By.cssSelector(".product"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            driver.quit();
        }
    }

    public static String extractGpuModel(String name) {
        String cleanName = name.toUpperCase().replaceAll("[^A-Z0-9]", " ");

        // REGEX za graficki
        Pattern pattern = Pattern.compile("(RTX|GTX|RX)\\s*(\\d{3,4})(TI)?(S)?(XTX|XT|GRE)?");
        Matcher matcher = pattern.matcher(cleanName);

        if (matcher.find()) {
            StringBuilder gpuModel = new StringBuilder();

            if (matcher.group(1) != null) {
                gpuModel.append(matcher.group(1)).append(" ");
            }
            if (matcher.group(2) != null) {
                gpuModel.append(matcher.group(2)).append(" ");
            }
            if (matcher.group(3) != null) {
                gpuModel.append("Ti ");
            }
            if (matcher.group(4) != null) {
                gpuModel.append("SUPER");
            }
            if (matcher.group(5) != null) {
                gpuModel.append(matcher.group(5));
            }

            String result = gpuModel.toString().trim();

            return result;
        }

        return null;
    }

    private Optional<GPU> determinePartModel(String extractedModel, String productName, Part gpuPart) {
        List<GPU> partModels = gpuService.findAll();

        partModels.sort(Comparator.comparingInt((GPU model) -> model.getName().length()).reversed());

        int memorySize = extractMemorySize(productName);

        for (GPU model : partModels) {
            if (extractedModel.equals(model.getName()) && model.getMemorySize() == memorySize) {
                return Optional.of(model);
            }
        }
        return Optional.empty();
    }

    private int extractMemorySize(String productName) {
        // Regular expression to find memory size (e.g., 8G, 16GB, 16 GB)
        Pattern pattern = Pattern.compile("(?<![\\d-])(\\d+)[Gg](?![\\d\\w])");
        Matcher matcher = pattern.matcher(productName.toUpperCase().replaceAll("[^A-Z0-9]", " "));
        if (matcher.find()) {
            return Integer.parseInt(matcher.group(1));
        }
        return -1;
    }

}
