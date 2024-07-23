package com.example.sklopi.config;

import com.example.sklopi.scraping.*;
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

    @Override
    public void run(String... args) throws Exception {
//        scraperService.scrapeAndSaveGPUs();
//        cpuScraperService.scrapeAndSaveCPUs();
//        motherboardScraperService.scrapeAndSaveMotherboards();
//        ramScraperService.scrapeAndSaveRAMs();
//        ssdScraperService.scrapeAndSaveSSDs();
//        hddScraperService.scrapeAndSaveHDDs();
        airCoolerScraperService.scrapeAndSaveAirCoolers();
        liquidCoolerScraperService.scrapeAndSaveLiquidCoolers();
    }
}
