package com.example.sklopi.config.seeder;

import com.example.sklopi.model.Part;
import com.example.sklopi.service.PartService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Order(1)
@Component
public class SeedDatabase implements CommandLineRunner {

    @Autowired
    private PartService partService;

    @Autowired
    private SeedGPUs seedGPUs;

    @Autowired
    private SeedCPUs seedCPUs;
    @Autowired
    private SeedMotherboards seedMotherboards;
    @Autowired
    private SeedRAMs seedRAMs;

    @Override
    public void run(String... args) throws Exception {
        seedParts();
        seedGPUs.seedGPUModels();
        seedCPUs.seedCPUModels();
        seedMotherboards.seedMotherboardModels();
        seedRAMs.seedRAMModels();
    }

    private void seedParts() {
        List<String> partNames = Arrays.asList(
                "CPU", "CPU Cooler", "Motherboard", "RAM", "Storage",
                "PSU", "Case", "GPU"
        );

        for (String partName : partNames) {
            Optional<Part> partOptional = partService.findByName(partName);
            if (partOptional.isEmpty()) {
                Part part = new Part();
                part.setName(partName);
                partService.savePart(part);
            }
        }
    }
}
