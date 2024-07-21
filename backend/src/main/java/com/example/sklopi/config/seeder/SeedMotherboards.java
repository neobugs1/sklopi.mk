package com.example.sklopi.config.seeder;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.parts.MotherboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedMotherboards {

    @Autowired
    private PartService partService;

    @Autowired
    private MotherboardService motherboardService;

    public void seedMotherboardModels() {
        Optional<Part> motherboardOptional = partService.findByName("Motherboard");
        if (motherboardOptional.isPresent()) {
            Part motherboard = motherboardOptional.get();
            Motherboard[] motherboards = {
                    // AM5 Motherboards
                    new Motherboard("B650", motherboard,"AM5", "ATX", "DDR5"),
                    new Motherboard("B650E",motherboard, "AM5", "ATX", "DDR5"),
                    new Motherboard("X670", motherboard,"AM5", "ATX", "DDR5"),
                    new Motherboard("X670E",motherboard, "AM5", "ATX", "DDR5"),

                    // LGA1700 Motherboards
                    new Motherboard("B760", motherboard,"LGA1700", "ATX", "DDR5"),
                    new Motherboard("Z790", motherboard,"LGA1700", "ATX", "DDR5"),

                    // AM4 Motherboards
                    new Motherboard("B450", motherboard,"AM4", "ATX", "DDR4"),
                    new Motherboard("B550", motherboard,"AM4", "ATX", "DDR4"),
                    new Motherboard("X570", motherboard,"AM4", "ATX", "DDR4"),

                    // LGA1200 Motherboards
                    new Motherboard("B460", motherboard,"LGA1200", "ATX", "DDR4"),
                    new Motherboard("Z490", motherboard,"LGA1200", "ATX", "DDR4"),
                    new Motherboard("Z590", motherboard,"LGA1200", "ATX", "DDR4"),
            };

            for (Motherboard model : motherboards) {
                Optional<Motherboard> modelOptional = motherboardService.findByNameAndPart(model.getName(), motherboard);
                if (modelOptional.isEmpty()) {
                    motherboardService.save(model);
                }
            }
        } else {
            System.err.println("Motherboard part not found, models cannot be seeded.");
        }
    }
}
