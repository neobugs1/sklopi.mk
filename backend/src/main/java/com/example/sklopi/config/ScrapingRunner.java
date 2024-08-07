package com.example.sklopi.config;

import com.example.sklopi.scraping.anhoch.*;
import com.example.sklopi.scraping.setec.*;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(2)
public class ScrapingRunner implements CommandLineRunner {

    @Autowired
    private AnhochGPUScraperService anhochGPUScraperService;
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
    @Autowired
    private SetecRAMScraperService setecRAMScraperService;
    @Autowired
    private SetecHDDScraperService setecHDDScraperService;
    @Autowired
    private SetecSSDScraperService setecSSDScraperService;
    @Autowired
    private SetecM2SSDScraperService setecM2SSDScraperService;
    @Autowired
    private SetecCPUCoolerScraperService setecCPUCoolerScraperService;
    @Autowired
    private SetecPcCaseScraperService setecPcCaseScraperService;
    @Autowired
    private SetecPSUScraperService setecPSUScraperService;

    @Override
    public void run(String... args) throws Exception {
//        productService.setAllProductsOutOfStock();
//
//        anhochGPUScraperService.scrapeAndSaveGPUs();
//        anhochCpuScraperService.scrapeAndSaveCPUs();
//        anhochMotherboardScraperService.scrapeAndSaveMotherboards();
//        anhochRamScraperService.scrapeAndSaveRAMs();
//        anhochSsdScraperService.scrapeAndSaveSSDs();
//        anhochHddScraperService.scrapeAndSaveHDDs();
//        anhochAirCoolerScraperService.scrapeAndSaveAirCoolers();
//        anhochLiquidCoolerScraperService.scrapeAndSaveLiquidCoolers();
//        anhochPcCaseScraperService.scrapeAndSaveCases();
//        anhochPsuScraperService.scrapeAndSavePSUs();

        setecGPUScraperService.scrapeAndSaveGPUs();
//        setecCPUScraperService.scrapeAndSaveCPUs();
//        setecMotherboardScraperService.scrapeAndSaveMotherboards();
//        setecRAMScraperService.scrapeAndSaveRAMs();
//        setecHDDScraperService.scrapeAndSaveHDDs();
//        setecSSDScraperService.scrapeAndSave25InchSSDs();
//        setecM2SSDScraperService.scrapeAndSaveM2SSDs();
//        setecCPUCoolerScraperService.scrapeAndSaveCPUCoolers();
//        setecPcCaseScraperService.scrapeAndSavePcCases();
//        setecPSUScraperService.scrapeAndSavePSUs();
    }
}
