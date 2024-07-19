package com.example.sklopi;

import com.example.sklopi.scraping.GPUScraperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SklopiApplication implements CommandLineRunner {

    @Autowired
    private GPUScraperService scraperService;

    public static void main(String[] args) {
        SpringApplication.run(SklopiApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        scraperService.scrapeAndSaveGPUs();
    }
}
