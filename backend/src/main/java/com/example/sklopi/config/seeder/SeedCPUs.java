package com.example.sklopi.config.seeder;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.parts.CPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedCPUs {

    @Autowired
    private PartService partService;

    @Autowired
    private CPUService cpuService;

    public void seedCPUModels() {
        Optional<Part> cpuOptional = partService.findByName("CPU");
        if (cpuOptional.isPresent()) {
            Part cpu = cpuOptional.get();
            CPU[] cpus = {
                    // AMD CPUs
                    new CPU("Ryzen 9 7950X3D", cpu, "AMD", "Socket AM5", 4200, 5400, true, false),
                    new CPU("Ryzen 9 7950X", cpu, "AMD", "Socket AM5", 4200, 5500, true, false),
                    new CPU("Ryzen 9 7900X3D", cpu, "AMD", "Socket AM5", 4000, 5200, true, false),
                    new CPU("Ryzen 9 7900X", cpu, "AMD", "Socket AM5", 4000, 5500, true, false),
                    new CPU("Ryzen 7 7800X3D", cpu, "AMD", "Socket AM5", 4000, 5300, true, false),
                    new CPU("Ryzen 7 7700X", cpu, "AMD", "Socket AM5", 4400, 5500, true, false),
                    new CPU("Ryzen 5 7600X", cpu, "AMD", "Socket AM5", 4500, 5500, true, false),

                    new CPU("Ryzen 7 7700G", cpu, "AMD", "Socket AM5", 3500, 5000, true, true),
                    new CPU("Ryzen 5 7600G", cpu, "AMD", "Socket AM5", 3600, 4700, true, true),

                    // Intel CPUs
                    new CPU("Core i9-14900KS", cpu, "Intel", "Socket 1700", 6000, 6500, false, false),
                    new CPU("Core i9-14900K", cpu, "Intel", "Socket 1700", 5300, 5800, false, false),
                    new CPU("Core i7-14700K", cpu, "Intel", "Socket 1700", 5300, 5700, false, false),
                    new CPU("Core i5-14600K", cpu, "Intel", "Socket 1700", 4600, 5000, false, false),
                    new CPU("Core i5-14500", cpu, "Intel", "Socket 1700", 4600, 4900, false, false),
                    new CPU("Core i5-14400", cpu, "Intel", "Socket 1700", 4400, 4800, false, false),

                    new CPU("Core i9-13900K", cpu, "Intel", "Socket 1700", 6000, 6200, false, false),
                    new CPU("Core i7-13700K", cpu, "Intel", "Socket 1700", 5600, 5800, false, false),
                    new CPU("Core i5-13600K", cpu, "Intel", "Socket 1700", 5100, 5500, false, false),
                    new CPU("Core i5-13500", cpu, "Intel", "Socket 1700", 5000, 5400, false, false),
                    new CPU("Core i5-13400", cpu, "Intel", "Socket 1700", 4600, 5000, false, false)
            };


            for (CPU model : cpus) {
                Optional<CPU> modelOptional = cpuService.findByNameAndPart(model.getName(), cpu);
                if (modelOptional.isEmpty()) {
                    cpuService.save(model);
                }
            }
        } else {
            System.err.println("CPU part not found, models cannot be seeded.");
        }
    }
}
