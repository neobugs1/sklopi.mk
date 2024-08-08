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
                    new CPU("Ryzen 9 7950X3D", cpu, "AMD", "AM5", 4200, 5400, 16, "16C/32T", 2918, 19605, true, "Zen 4", 120),
                    new CPU("Ryzen 9 7950X", cpu, "AMD", "AM5", 4200, 5500, 16, "16C/32T", 2941, 19279, true, "Zen 4", 170),
                    new CPU("Ryzen 9 7900X3D", cpu, "AMD", "AM5", 4000, 5200, 12, "12C/24T", 2826, 17467, true, "Zen 4", 120),
                    new CPU("Ryzen 9 7900X", cpu, "AMD", "AM5", 4000, 5500, 12, "12C/24T", 2926, 17850, true, "Zen 4", 170),
                    new CPU("Ryzen 7 7800X3D", cpu, "AMD", "AM5", 4000, 5300, 8, "8C/16T", 2715, 15129, true, "Zen 4", 120),
                    new CPU("Ryzen 7 7700X", cpu, "AMD", "AM5", 4400, 5500, 8, "8C/16T", 2915, 15293, true, "Zen 4", 105),
                    new CPU("Ryzen 5 7600X", cpu, "AMD", "AM5", 4500, 5500, 6, "6C/12T", 2872, 12856, true, "Zen 4", 105),
                    new CPU("Ryzen 7 7700", cpu, "AMD", "AM5", 3500, 5000, 8, "8C/16T", 2866, 14870, true, "Zen 4", 65),
                    new CPU("Ryzen 5 7600", cpu, "AMD", "AM5", 3600, 4700, 6, "6C/12T", 2734, 12173, true, "Zen 4", 65),


                    new CPU("Ryzen 9 5950X", cpu, "AMD", "AM4", 3400, 4900, 16, "16C/32T", 2191, 12119, true, "Zen 3", 105),
                    new CPU("Ryzen 9 5900X", cpu, "AMD", "AM4", 3700, 4800, 12, "12C/24T", 2185, 11904, true, "Zen 3", 105),
                    new CPU("Ryzen 7 5800X3D", cpu, "AMD", "AM4", 3400, 4500, 8, "8C/16T", 1630, 10963, true, "Zen 3", 105),
                    new CPU("Ryzen 7 5800X", cpu, "AMD", "AM4", 3800, 4700, 8, "8C/16T", 2181, 10260, true, "Zen 3", 105),
                    new CPU("Ryzen 7 5700X", cpu, "AMD", "AM4", 3800, 4600, 8, "8C/16T", 2126, 9728, true, "Zen 3", 65),
                    new CPU("Ryzen 5 5600X", cpu, "AMD", "AM4", 3700, 4600, 6, "6C/12T", 2095, 8665, true, "Zen 3", 65),
                    new CPU("Ryzen 5 5600", cpu, "AMD", "AM4", 3900, 4400, 6, "6C/12T", 2035, 8568, true, "Zen 3", 65),
                    new CPU("Ryzen 5 5500", cpu, "AMD", "AM4", 3600, 4200, 6, "6C/12T", 1868, 7661, true, "Zen 3", 65),
                    new CPU("Ryzen 5 4500", cpu, "AMD", "AM4", 3600, 4100, 6, "6C/12T", 1555, 6232, true, "Zen 2", 65),
                    new CPU("Ryzen 3 4100", cpu, "AMD", "AM4", 3600, 4000, 4, "4C/8T", 1493, 4755, true, "Zen 2", 65),


                    new CPU("Ryzen 9 3950X", cpu, "AMD", "AM4", 3500, 4700, 16, "16C/32T", 1717, 10815, true, "Zen 2", 105),
                    new CPU("Ryzen 9 3900X", cpu, "AMD", "AM4", 3800, 4600, 12, "12C/24T", 1696, 9941, true, "Zen 2", 105),
                    new CPU("Ryzen 7 3800X", cpu, "AMD", "AM4", 3900, 4500, 8, "8C/16T", 1700, 8425, true, "Zen 2", 105),
                    new CPU("Ryzen 7 3700X", cpu, "AMD", "AM4", 3600, 4400, 8, "8C/16T", 1657, 8097, true, "Zen 2", 65),
                    new CPU("Ryzen 5 3600X", cpu, "AMD", "AM4", 3800, 4400, 6, "6C/12T", 1649, 7032, true, "Zen 2", 95),
                    new CPU("Ryzen 5 3600", cpu, "AMD", "AM4", 3600, 4200, 6, "6C/12T", 1599, 6881, true, "Zen 2", 65),
                    new CPU("Ryzen 5 3400G", cpu, "AMD", "AM4", 3700, 4200, 4, "4C/8T", 1129, 3623, true, "Zen+", 65),
                    new CPU("Ryzen 5 3200G", cpu, "AMD", "AM4", 3600, 4000, 4, "4C/4T", 1052, 3007, true, "Zen+", 65),
                    new CPU("Ryzen 3 3300X", cpu, "AMD", "AM4", 3800, 4300, 4, "4C/8T", 1687, 5855, true, "Zen 2", 65),
                    new CPU("Ryzen 3 3100", cpu, "AMD", "AM4", 3600, 3900, 4, "4C/8T", 1483, 5090, true, "Zen 2", 65),


                    // Intel CPUs
                    new CPU("i9 14900KS", cpu, "Intel", "LGA1700", 3200, 6000, 24, "8P + 16E", 3105, 21815, true, "14th Gen", 150),
                    new CPU("i9 14900K", cpu, "Intel", "LGA1700", 3200, 5800, 24, "8P + 16E", 3086, 20862, true, "14th Gen", 125),
                    new CPU("i7 14700K", cpu, "Intel", "LGA1700", 3400, 5700, 20, "8P + 12E", 2947, 19206, true, "14th Gen", 125),
                    new CPU("i5 14600K", cpu, "Intel", "LGA1700", 3500, 5000, 14, "6P + 8E", 2802, 15941, true, "14th Gen", 125),
                    new CPU("i5 14500", cpu, "Intel", "LGA1700", 2600, 4900, 14, "6P + 8E", 2545, 13223, true, "14th Gen", 65),
                    new CPU("i3 14100", cpu, "Intel", "LGA1700", 3500, 4400, 8, "4P + 4E", 2459, 8271, true, "14th Gen", 60),
                    new CPU("i3 14100F", cpu, "Intel", "LGA1700", 3500, 4400, 8, "4P + 4E", 2459, 8271, true, "14th Gen", 60),

                    new CPU("i9 13900KS", cpu, "Intel", "LGA1700", 3200, 6000, 24, "8P + 16E", 3105, 21815, true, "13th Gen", 150),
                    new CPU("i9 13900K", cpu, "Intel", "LGA1700", 3200, 5700, 24, "8P + 16E", 2983, 20187, true, "13th Gen", 125),
                    new CPU("i9 13900", cpu, "Intel", "LGA1700", 2000, 5700, 24, "8P + 16E", 2728, 17342, true, "13th Gen", 65),
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
