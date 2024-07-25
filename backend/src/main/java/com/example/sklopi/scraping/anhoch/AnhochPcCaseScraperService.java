package com.example.sklopi.scraping.anhoch;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.PcCase;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.PcCaseService;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class AnhochPcCaseScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private PcCaseService caseService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveCases() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.anhoch.com/categories/kukjishta/products?brand=&attribute=&fromPrice=1200&toPrice=16970&inStockOnly=1&sort=priceLowToHigh&perPage=100&page=1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));

            List<WebElement> caseElements = driver.findElements(By.cssSelector(".product-card"));

            if (caseElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                List<String> knownBrands = scrapeBrands(driver);

                Part casePart = partService.findByName("Case").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Case");
                    return partService.savePart(newPart);
                });

                for (WebElement caseElement : caseElements) {
                    String name = caseElement.findElement(By.cssSelector(".product-name h6")).getText();

                    // Ignore the misplaced case
                    if (name.contains("White Shark Torpedo GCC-2304") || name.contains("ITX")) {
                        continue;
                    }

                    String imageUrl = caseElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = caseElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");

                    WebElement priceElement = caseElement.findElement(By.cssSelector(".product-card-bottom div"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
                    int price = Integer.parseInt(cleanedPriceString);

                    String brand = determineBrand(name, knownBrands);
                    String formFactor = determineFormFactor(name);

                    if (brand != null && formFactor != null) {
                        Optional<PcCase> partModel = determineAndSavePartModel(name, casePart, brand, formFactor);

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
        List<WebElement> brandElements = driver.findElements(By.cssSelector(".filter-brands .form-check label"));
        for (WebElement brandElement : brandElements) {
            brands.add(brandElement.getAttribute("textContent").trim());
        }
        return brands;
    }

    private String determineBrand(String productName, List<String> knownBrands) {
        for (String brand : knownBrands) {
            if (productName.contains(brand)) {
                return brand;
            }
        }
        return null;
    }

    private String determineFormFactor(String productName) {
        if (productName.contains("Micro")) {
            return "mATX";
        } else if (productName.contains("E-ATX")) {
            return "E-ATX";
        } else {
            return "ATX";
        }
    }

    private Optional<PcCase> determineAndSavePartModel(String productName, Part casePart, String brand, String formFactor) {
        return caseService.findByBrandAndFormFactor(brand, formFactor)
                .or(() -> {
                    PcCase newCase = new PcCase(casePart, brand, formFactor);
                    return Optional.of(caseService.save(newCase));
                });
    }
}
