package com.example.sklopi.scraping.anhoch;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.CPUCooler;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.CPUCoolerService;
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
public class AnhochAirCoolerScraperService {

    @Autowired
    private PartService partService;

    @Autowired
    private CPUCoolerService cpuCoolerService;

    @Autowired
    private ProductService productService;

    public void scrapeAndSaveAirCoolers() {
        System.setProperty("webdriver.chrome.driver", "C:\\ChromeDriver\\chromedriver.exe");

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");

        WebDriver driver = new ChromeDriver(options);

        try {
            driver.get("https://www.anhoch.com/categories/vozdushni-ladilnici/products?brand=&attribute=&fromPrice=980&toPrice=274980&inStockOnly=1&sort=priceLowToHigh&perPage=100&page=1");

            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
            wait.until(ExpectedConditions.presenceOfElementLocated(By.cssSelector(".product-card")));

            List<WebElement> coolerElements = driver.findElements(By.cssSelector(".product-card"));

            if (coolerElements.isEmpty()) {
                System.out.println("No elements found with the selector .product-card");
            } else {
                List<String> knownBrands = scrapeBrands(driver);

                Part coolerPart = partService.findByName("Air Cooler").orElseGet(() -> {
                    Part newPart = new Part();
                    newPart.setName("Air Cooler");
                    return partService.savePart(newPart);
                });

                for (WebElement coolerElement : coolerElements) {
                    String imageUrl = coolerElement.findElement(By.cssSelector(".product-image img")).getAttribute("src");
                    String productUrl = coolerElement.findElement(By.cssSelector(".product-card-middle a")).getAttribute("href");
                    String name = coolerElement.findElement(By.cssSelector(".product-name h6")).getText();

                    WebElement priceElement = coolerElement.findElement(By.cssSelector(".product-card-bottom div"));
                    String priceString = priceElement.getText().trim();

                    String cleanedPriceString = priceString.replace(" ден.", "").replace(".", "").split(",")[0];
                    int price = Integer.parseInt(cleanedPriceString);

                    String brand = determineBrand(name, knownBrands);

                    if (brand != null) {
                        Optional<CPUCooler> partModel = determineAndSavePartModel(name, coolerPart, brand);

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
        List<WebElement> brandElements = driver.findElements(By.cssSelector(".filter-brands .form-check label"));
        for (WebElement brandElement : brandElements) {
            String brand = brandElement.getAttribute("textContent").trim();
            if (!brand.equals("AMD") && !brand.equals("Intel")) { // delot za proizvoditeli pokazuva AMD/INTEL no raboti pogresno
                if (brand.equals("Other")) {
                    brand = "ARCTIC";
                }
                brands.add(brand);
            }
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

    private Optional<CPUCooler> determineAndSavePartModel(String productName, Part coolerPart, String brand) {
        return cpuCoolerService.findByNameAndCoolerType(brand, "Air")
                .or(() -> {
                    CPUCooler newCooler = new CPUCooler(brand, coolerPart, "Air");
                    return Optional.of(cpuCoolerService.save(newCooler));
                });
    }
}
