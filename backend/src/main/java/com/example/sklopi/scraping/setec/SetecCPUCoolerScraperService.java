package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.CPUCooler;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.CPUCoolerService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SetecCPUCoolerScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private CPUCoolerService cpuCoolerService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveCPUCoolers() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/кулери?mfp=price%5B1300%2C58999%5D&limit=100");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> coolerElements = driver.findElements(By.cssSelector(".product"));

            if (coolerElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                List<String> knownBrands = scrapeBrands(driver);
                List<String> ignoreKeywords = List.of("120", "140", "200", "CONTROLLER", "SSD"); //ignorirame FANS i RGB Controllers

                // Dodadi brendovi sto ne se izlistani
                knownBrands.add("NZXT");

                Part coolerPart = partService.findByName("CPU Cooler").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("CPU Cooler");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement coolerElement : coolerElements) {
                    js.executeScript("arguments[0].scrollIntoView(true);", coolerElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = coolerElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        continue;
                    }

                    String imageUrl = coolerElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = coolerElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = coolerElement.findElement(By.cssSelector(".name a")).getText();

                    if (ignoreKeywords.stream().anyMatch(name::contains)) {
                        System.out.println("Skipped product (ignored keyword): " + name);
                        continue;
                    }

                    WebElement priceElement = null;
                    try {
                        priceElement = coolerElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        try {
                            priceElement = coolerElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    String brand = determineBrand(name, knownBrands);
                    String coolerType = determineCoolerType(name);

                    if (priceElement != null && brand != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);

                        Optional<CPUCooler> partModel = determineAndSavePartModel(name, coolerPart, brand, coolerType);

                        if (partModel.isPresent()) {
                            Product product = new Product();
                            product.setName(name);
                            product.setImageUrl(imageUrl);
                            product.setProductUrl(productUrl);
                            product.setPrice(price);
                            product.setPart(coolerPart);
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
        List<WebElement> brandElements = driver.findElements(By.cssSelector(".mfilter-options-container label"));
        for (WebElement brandElement : brandElements) {
            String forAttribute = brandElement.getAttribute("for");
            if (forAttribute != null && forAttribute.contains("manufacturers")) {
                String brandName = brandElement.getText().trim();
                if (!brandName.isEmpty()) {
                    brands.add(brandName);
                }
            }
        }
        return brands;
    }

    private String determineBrand(String productName, List<String> knownBrands) {
        String lowerCaseProductName = productName.toLowerCase();
        for (String brand : knownBrands) {
            if (lowerCaseProductName.contains(brand.toLowerCase())) {
                return brand;
            }
        }
        //duplikat kod zatoa sto nekoi brendovi kako Cooler Master, ponekogas se napisani kako CoolerMaster
        for (String brand : knownBrands) {
            if (lowerCaseProductName.contains(brand.toLowerCase().replace(" ", ""))) {
                return brand;
            }
        }
        return null;
    }

    private String determineCoolerType(String productName) {
        String lowerCaseProductName = productName.toLowerCase();
        if (lowerCaseProductName.contains("lc") || lowerCaseProductName.contains("liquid")
                || lowerCaseProductName.contains("ryuo") || lowerCaseProductName.contains("ryujin")
                || lowerCaseProductName.contains("kraken")) {
            return "Liquid";
        }
        return "Air";
    }

    private Optional<CPUCooler> determineAndSavePartModel(String productName, Part coolerPart, String brand, String coolerType) {
        List<CPUCooler> partModels = cpuCoolerService.findAll();

        for (CPUCooler model : partModels) {
            if (model.getName().equalsIgnoreCase(brand) && model.getCoolerType().equalsIgnoreCase(coolerType)) {
                return Optional.of(model);
            }
        }

        CPUCooler newCooler = new CPUCooler(brand, coolerPart, coolerType);
        return Optional.of(cpuCoolerService.save(newCooler));
    }
}