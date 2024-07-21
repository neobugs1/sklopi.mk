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
                    new CPU("Ryzen 9 7950X3D", cpu, "AMD", "AM5", 4200, 5400, true, false),
                    new CPU("Ryzen 9 7950X", cpu, "AMD", "AM5", 4200, 5500, true, false),
                    new CPU("Ryzen 9 7900X3D", cpu, "AMD", "AM5", 4000, 5200, true, false),
                    new CPU("Ryzen 9 7900X", cpu, "AMD", "AM5", 4000, 5500, true, false),
                    new CPU("Ryzen 7 7800X3D", cpu, "AMD", "AM5", 4000, 5300, true, false),
                    new CPU("Ryzen 7 7700X", cpu, "AMD", "AM5", 4400, 5500, true, false),
                    new CPU("Ryzen 5 7600X", cpu, "AMD", "AM5", 4500, 5500, true, false),
                    new CPU("Ryzen 7 7700G", cpu, "AMD", "AM5", 3500, 5000, true, true),
                    new CPU("Ryzen 5 7600G", cpu, "AMD", "AM5", 3600, 4700, true, true),

                    new CPU("Ryzen 9 5950X", cpu, "AMD", "AM4", 3400, 4900, true, false),
                    new CPU("Ryzen 9 5900X", cpu, "AMD", "AM4", 3700, 4800, true, false),
                    new CPU("Ryzen 7 5800X3D", cpu, "AMD", "AM4", 3400, 4500, true, false),
                    new CPU("Ryzen 7 5800X", cpu, "AMD", "AM4", 3800, 4700, true, false),
                    new CPU("Ryzen 7 5700G", cpu, "AMD", "AM4", 3800, 4600, true, true),
                    new CPU("Ryzen 5 5600X", cpu, "AMD", "AM4", 3700, 4600, true, false),
                    new CPU("Ryzen 5 5600G", cpu, "AMD", "AM4", 3900, 4400, true, true),
                    new CPU("Ryzen 5 5500", cpu, "AMD", "AM4", 3600, 4200, true, false),
                    new CPU("Ryzen 5 4500", cpu, "AMD", "AM4", 3600, 4100, true, false),
                    new CPU("Ryzen 3 4100", cpu, "AMD", "AM4", 3600, 4000, true, false),

                    new CPU("Ryzen 9 3950X", cpu, "AMD", "AM4", 3500, 4700, true, false),
                    new CPU("Ryzen 9 3900X", cpu, "AMD", "AM4", 3800, 4600, true, false),
                    new CPU("Ryzen 9 3900", cpu, "AMD", "AM4", 3200, 4400, true, false),
                    new CPU("Ryzen 7 3800X", cpu, "AMD", "AM4", 3900, 4500, true, false),
                    new CPU("Ryzen 7 3700X", cpu, "AMD", "AM4", 3600, 4400, true, false),
                    new CPU("Ryzen 7 3700", cpu, "AMD", "AM4", 3200, 4200, true, false),
                    new CPU("Ryzen 5 3600X", cpu, "AMD", "AM4", 3800, 4400, true, false),
                    new CPU("Ryzen 5 3600", cpu, "AMD", "AM4", 3600, 4200, true, false),
                    new CPU("Ryzen 5 3400G", cpu, "AMD", "AM4", 3700, 4200, true, true),
                    new CPU("Ryzen 5 3200G", cpu, "AMD", "AM4", 3600, 4000, true, true),
                    new CPU("Ryzen 3 3300X", cpu, "AMD", "AM4", 3800, 4300, true, false),
                    new CPU("Ryzen 3 3100", cpu, "AMD", "AM4", 3600, 3900, true, false),

                    new CPU("Ryzen 7 2700X", cpu, "AMD", "AM4", 3700, 4300, true, false),
                    new CPU("Ryzen 7 2700", cpu, "AMD", "AM4", 3200, 4100, true, false),
                    new CPU("Ryzen 5 2600X", cpu, "AMD", "AM4", 3600, 4250, true, false),
                    new CPU("Ryzen 5 2600", cpu, "AMD", "AM4", 3400, 3900, true, false),
                    new CPU("Ryzen 5 2400G", cpu, "AMD", "AM4", 3600, 3900, true, true),
                    new CPU("Ryzen 3 2200G", cpu, "AMD", "AM4", 3500, 3700, true, true),

                    new CPU("Ryzen 7 1800X", cpu, "AMD", "AM4", 3600, 4000, true, false),
                    new CPU("Ryzen 7 1700X", cpu, "AMD", "AM4", 3400, 3800, true, false),
                    new CPU("Ryzen 7 1700", cpu, "AMD", "AM4", 3000, 3700, true, false),
                    new CPU("Ryzen 5 1600X", cpu, "AMD", "AM4", 3600, 4000, true, false),
                    new CPU("Ryzen 5 1600", cpu, "AMD", "AM4", 3200, 3600, true, false),
                    new CPU("Ryzen 5 1500X", cpu, "AMD", "AM4", 3500, 3700, true, false),
                    new CPU("Ryzen 3 1300X", cpu, "AMD", "AM4", 3500, 3700, true, false),
                    new CPU("Ryzen 3 1200", cpu, "AMD", "AM4", 3100, 3400, true, false),


                    // Intel CPUs
                    new CPU("Core i9-14900KS", cpu, "Intel", "LGA1700", 6000, 6500, false, false),
                    new CPU("Core i9-14900K", cpu, "Intel", "LGA1700", 5300, 5800, false, false),
                    new CPU("Core i7-14700K", cpu, "Intel", "LGA1700", 5300, 5700, false, false),
                    new CPU("Core i5-14600K", cpu, "Intel", "LGA1700", 4600, 5000, false, false),
                    new CPU("Core i5-14500", cpu, "Intel", "LGA1700", 4600, 4900, false, false),
                    new CPU("Core i5-14400", cpu, "Intel", "LGA1700", 4400, 4800, false, false),

                    new CPU("Core i9-13900K", cpu, "Intel", "LGA1700", 6000, 6200, false, false),
                    new CPU("Core i7-13700K", cpu, "Intel", "LGA1700", 5600, 5800, false, false),
                    new CPU("Core i5-13600K", cpu, "Intel", "LGA1700", 5100, 5500, false, false),
                    new CPU("Core i5-13500", cpu, "Intel", "LGA1700", 5000, 5400, false, false),
                    new CPU("Core i5-13400", cpu, "Intel", "LGA1700", 4600, 5000, false, false)
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
