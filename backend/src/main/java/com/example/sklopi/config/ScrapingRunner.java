package com.example.sklopi.config;

import com.example.sklopi.scraping.*;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ScrapingRunner implements CommandLineRunner {

    @Autowired
    private GPUScraperService scraperService;
    @Autowired
    private CPUScraperService cpuScraperService;
    @Autowired
    private MotherboardScraperService motherboardScraperService;
    @Autowired
    private RAMScraperService ramScraperService;
    @Autowired
    private SSDScraperService ssdScraperService;
    @Autowired
    private HDDScraperService hddScraperService;
    @Autowired
    private AirCoolerScraperService airCoolerScraperService;
    @Autowired
    private LiquidCoolerScraperService liquidCoolerScraperService;
    @Autowired
    private PSUScraperService psuScraperService;
    @Autowired
    private PcCaseScraperService pcCaseScraperService;

    @Autowired
    private ProductService productService;

    @Override
    public void run(String... args) throws Exception {
        productService.setAllProductsOutOfStock();

        scraperService.scrapeAndSaveGPUs();
        cpuScraperService.scrapeAndSaveCPUs();
        motherboardScraperService.scrapeAndSaveMotherboards();
        ramScraperService.scrapeAndSaveRAMs();
        ssdScraperService.scrapeAndSaveSSDs();
        hddScraperService.scrapeAndSaveHDDs();
        airCoolerScraperService.scrapeAndSaveAirCoolers();
        liquidCoolerScraperService.scrapeAndSaveLiquidCoolers();
        psuScraperService.scrapeAndSavePSUs();
        pcCaseScraperService.scrapeAndSaveCases();
    }
}
