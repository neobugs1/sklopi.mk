package com.example.sklopi.config;

import com.example.sklopi.scraping.anhoch.*;
import com.example.sklopi.scraping.setec.SetecCPUScraperService;
import com.example.sklopi.scraping.setec.SetecGPUScraperService;
import com.example.sklopi.scraping.setec.SetecMotherboardScraperService;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ScrapingRunner implements CommandLineRunner {

    @Autowired
    private AnhochGPUScraperService scraperService;
    @Autowired
    private AnhochCPUScraperService anhochCpuScraperService;
    @Autowired
    private AnhochMotherboardScraperService anhochMotherboardScraperService;
    @Autowired
    private AnhochRAMScraperService anhochRamScraperService;
    @Autowired
    private AnhochSSDScraperService anhochSsdScraperService;
    @Autowired
    private AnhochHDDScraperService anhochHddScraperService;
    @Autowired
    private AnhochAirCoolerScraperService anhochAirCoolerScraperService;
    @Autowired
    private AnhochLiquidCoolerScraperService anhochLiquidCoolerScraperService;
    @Autowired
    private AnhochPSUScraperService anhochPsuScraperService;
    @Autowired
    private AnhochPcCaseScraperService anhochPcCaseScraperService;

    @Autowired
    private ProductService productService;
    @Autowired
    private SetecGPUScraperService setecGPUScraperService;
    @Autowired
    private SetecCPUScraperService setecCPUScraperService;
    @Autowired
    private SetecMotherboardScraperService setecMotherboardScraperService;

    @Override
    public void run(String... args) throws Exception {
        productService.setAllProductsOutOfStock();

//        setecGPUScraperService.scrapeAndSaveGPUs();
//        anhochCpuScraperService.scrapeAndSaveCPUs();
        setecCPUScraperService.scrapeAndSaveCPUs();
//        setecMotherboardScraperService.scrapeAndSaveMotherboards();
    }
}
