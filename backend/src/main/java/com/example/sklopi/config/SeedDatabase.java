package com.example.sklopi.config;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.service.PartModelService;
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
    private PartModelService partModelService;

    @Override
    public void run(String... args) throws Exception {
        seedParts();
        seedGPUModels();
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

    private void seedGPUModels() {
        Optional<Part> gpuOptional = partService.findByName("GPU");
        if (gpuOptional.isPresent()) {
            Part gpu = gpuOptional.get();
            String[] gpuModels = {
                    "RTX 3050", "RTX 3060", "RTX 3090 Ti",
                    "RTX 4060", "RTX 4060 Ti", "RTX 4070",
                    "RTX 4070 SUPER", "RTX 4070 Ti", "RTX 4070 Ti SUPER",
                    "RTX 4080", "RTX 4080 SUPER", "RTX 4090",
                    "RX 6500 XT", "RX 6600", "RX 6600 XT",
                    "RX 6700 XT", "RX 6800", "RX 6800 XT",
                    "RX 6900 XT", "RX 7600 XT", "RX 7700 XT",
                    "RX 7800 XT", "RX 7900 GRE", "RX 7900 XT",
                    "RX 7900 XTX"
            };

            for (String modelName : gpuModels) {
                Optional<PartModel> modelOptional = partModelService.findByNameAndPart(modelName, gpu);
                if (modelOptional.isEmpty()) {
                    PartModel model = new PartModel();
                    model.setName(modelName);
                    model.setPart(gpu);
                    partModelService.savePartModel(model);
                }
            }
        } else {
            System.err.println("GPU part not found, models cannot be seeded.");
        }
    }
}
