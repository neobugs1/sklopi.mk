package com.example.sklopi.config;

import com.example.sklopi.scraping.CPUScraperService;
import com.example.sklopi.scraping.GPUScraperService;
import com.example.sklopi.scraping.MotherboardScraperService;
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

    @Override
    public void run(String... args) throws Exception {
        scraperService.scrapeAndSaveGPUs();
        cpuScraperService.scrapeAndSaveCPUs();
        motherboardScraperService.scrapeAndSaveMotherboards();
    }
}
