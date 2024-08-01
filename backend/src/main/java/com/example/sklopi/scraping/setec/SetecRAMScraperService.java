package com.example.sklopi.scraping.newwebsite;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.RAM;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.RAMService;
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
public class SetecRAMScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private RAMService ramService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveRAMs() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/рам-меморија?limit=200&mfp=stock_status[5],price[900,14999]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> ramElements = driver.findElements(By.cssSelector(".product"));

            if (ramElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                Part ramPart = partService.findByName("RAM").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("RAM");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement ramElement : ramElements) {
                    js.executeScript("arguments[0].scrollIntoView(true);", ramElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    String imageUrl = ramElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = ramElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = ramElement.findElement(By.cssSelector(".name a")).getText();

                    // Skip SODIMM modules
                    if (name.contains("SO")) {
                        System.out.println("Skipped SODIMM module: " + name);
                        continue;
                    }

                    WebElement priceElement = null;
                    try {
                        // Try to find the discounted price
                        priceElement = ramElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        // If the discounted price is not found, try to find the regular price in the new structure
                        try {
                            priceElement = ramElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            // If neither price is found, handle this case (e.g., set a default value or log a message)
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    int amount = (name.contains("(Kit of 2)") || name.contains("2Rx")) ? 2 : 1;

                    int size = 0;
                    Pattern sizePattern = Pattern.compile("(\\d+GB)");
                    Matcher sizeMatcher = sizePattern.matcher(name);
                    if (sizeMatcher.find()) {
                        size = Integer.parseInt(sizeMatcher.group().replace("GB", ""));
                    }

                    int frequency = 0;
                    Pattern frequencyPattern = Pattern.compile("(\\d+)[mM][hH][zZ]");
                    Matcher frequencyMatcher = frequencyPattern.matcher(name);
                    if (frequencyMatcher.find()) {
                        frequency = Integer.parseInt(frequencyMatcher.group(1));
                    } else {
                        System.out.println("Skipped product (unknown frequency): " + name);
                        continue;
                    }

                    if (priceElement != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);

                        Optional<RAM> partModel = determineAndSavePartModel(name, ramPart, amount, size, frequency);

                        if (partModel.isPresent()) {
                            Product product = new Product();
                            product.setName(name);
                            product.setImageUrl(imageUrl);
                            product.setProductUrl(productUrl);
                            product.setPrice(price);
                            product.setPart(ramPart);
                            product.setPartModel(partModel.get());

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

    private Optional<RAM> determineAndSavePartModel(String productName, Part ramPart, int amount, int size, int frequency) {
        List<RAM> partModels = ramService.findAll();

        String ramType = getRamType(productName);

        for (RAM model : partModels) {
            if (model.getName().equals(ramType) && model.getAmount() == amount && model.getCapacity() == size && model.getFrequency() == frequency) {
                return Optional.of(model);
            }
        }

        if (ramType != null) {
            RAM newRAM = new RAM(ramType, ramPart, amount, size, frequency);
            return Optional.of(ramService.save(newRAM));
        }

        return Optional.empty();
    }

    private String getRamType(String productName) {
        List<String> ramTypes = List.of("DDR4", "DDR5"); // You can expand this list in the future
        for (String type : ramTypes) {
            if (productName.contains(type)) {
                return type;
            }
        }
        return null;
    }
}
