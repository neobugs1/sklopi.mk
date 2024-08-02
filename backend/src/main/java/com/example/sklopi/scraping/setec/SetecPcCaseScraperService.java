package com.example.sklopi.scraping.setec;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.PCCase;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.PcCaseService;
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
public class SetecPcCaseScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private PcCaseService pcCaseService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSavePcCases() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://setec.mk/компјутери-и-it-опрема/компјутери-и-компјутерски-делови/куќишта?limit=150&mfp=price[1390,59999]");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product")));

            List<WebElement> caseElements = driver.findElements(By.cssSelector(".product"));

            if (caseElements.isEmpty()) {
                System.out.println("No elements found with the selector .product");
            } else {
                List<String> knownBrands = scrapeBrands(driver);
                List<String> ignoreKeywords = List.of("ITX", "NCORE", "F129A", "Controller", "Raspberry");

                Part casePart = partService.findByName("PC Case").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("PC Case");
                    return partService.savePart(newPart);
                });

                JavascriptExecutor js = (JavascriptExecutor) driver;

                for (WebElement caseElement : caseElements) {
                    js.executeScript("arguments[0].scrollIntoView(true);", caseElement);
                    Thread.sleep(200);

                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));
                    wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".image .zoom-image-effect")));

                    WebElement stockElement;
                    try {
                        stockElement = caseElement.findElement(By.cssSelector(".lager div"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        continue;
                    }

                    if (!stockElement.getAttribute("class").contains("ima_zaliha")) {
                        continue;
                    }

                    String imageUrl = caseElement.findElement(By.cssSelector(".image .zoom-image-effect")).getAttribute("src");
                    String productUrl = caseElement.findElement(By.cssSelector(".name a")).getAttribute("href");
                    String name = caseElement.findElement(By.cssSelector(".name a")).getText();

                    // Skip cases with specified keywords
                    if (ignoreKeywords.stream().anyMatch(name::contains)) {
                        System.out.println("Skipped product (ignored keyword): " + name);
                        continue;
                    }

                    WebElement priceElement = null;
                    try {
                        priceElement = caseElement.findElement(By.cssSelector(".category-price-akciska .price-new-new"));
                    } catch (org.openqa.selenium.NoSuchElementException e) {
                        try {
                            priceElement = caseElement.findElement(By.cssSelector(".category-price-redovna .cena_za_kesh"));
                        } catch (org.openqa.selenium.NoSuchElementException e1) {
                            System.out.println("Price not found for product: " + name);
                            continue;
                        }
                    }

                    String brand = determineBrand(name, knownBrands);
                    String formFactor = determineFormFactor(name);

                    if (priceElement != null && brand != null) {
                        String priceString = priceElement.getText().trim();
                        String cleanedPriceString = priceString.replace(" Ден.", "").replace(",", "");
                        int price = Integer.parseInt(cleanedPriceString);

                        Optional<PCCase> partModel = determineAndSavePartModel(name, casePart, brand, formFactor);

                        if (partModel.isPresent()) {
                            Product product = new Product();
                            product.setName(name);
                            product.setImageUrl(imageUrl);
                            product.setProductUrl(productUrl);
                            product.setPrice(price);
                            product.setPart(casePart);
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
            //nekoi brendovi kako Cooler Master, ponekogas se napisani kako CoolerMaster
            String lowerCaseBrand = brand.toLowerCase();
            String brandWithoutSpaces = lowerCaseBrand.replace(" ", "");

            if (lowerCaseProductName.contains(lowerCaseBrand) || lowerCaseProductName.contains(brandWithoutSpaces)) {
                return brand;
            }
        }
        return null;
    }


    private String determineFormFactor(String productName) {
        String lowerCaseProductName = productName.toLowerCase();
        if (lowerCaseProductName.contains("e-atx") || lowerCaseProductName.contains("eatx")) {
            return "E-ATX";
        } else if (lowerCaseProductName.contains("micro") || lowerCaseProductName.contains("m-atx") || lowerCaseProductName.contains("qube") || lowerCaseProductName.contains("mini")) {
            return "mATX";
        } else {
            return "ATX";
        }
    }

    private Optional<PCCase> determineAndSavePartModel(String productName, Part casePart, String brand, String formFactor) {
        return pcCaseService.findByBrandAndFormFactor(brand, formFactor)
                .or(() -> {
                    PCCase newCase = new PCCase(casePart, brand, formFactor);
                    return Optional.of(pcCaseService.save(newCase));
                });
    }
}
