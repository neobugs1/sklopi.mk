package com.example.sklopi.config.seeder;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.RAM;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.parts.RAMService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedRAMs {

    @Autowired
    private PartService partService;

    @Autowired
    private RAMService ramService;

    public void seedRAMModels() {
        Optional<Part> ramOptional = partService.findByName("RAM");
        if (ramOptional.isPresent()) {
            Part ram = ramOptional.get();
            RAM[] rams = {
                    new RAM("DDR4", ram),
                    new RAM("DDR5", ram)
            };

            for (RAM model : rams) {
                Optional<RAM> modelOptional = ramService.findByNameAndPart(model.getName(), ram);
                if (modelOptional.isEmpty()) {
                    ramService.save(model);
                }
            }
        } else {
            System.err.println("RAM part not found, models cannot be seeded.");
        }
    }
}
