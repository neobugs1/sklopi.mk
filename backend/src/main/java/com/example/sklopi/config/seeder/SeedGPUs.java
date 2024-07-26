package com.example.sklopi.config.seeder;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.service.PartService;
import com.example.sklopi.service.parts.GPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class SeedGPUs {

    @Autowired
    private PartService partService;

    @Autowired
    private GPUService gpuService;

    public void seedGPUModels() {
        Optional<Part> gpuOptional = partService.findByName("GPU");
        if (gpuOptional.isPresent()) {
            Part gpu = gpuOptional.get();
            GPU[] gpus = {
                    new GPU("GTX 1660 Ti", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 5000),
                    new GPU("GTX 1660 SUPER", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 5000),
                    new GPU("RTX 2060", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 5000),
                    new GPU("RTX 3050", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 5000),
                    new GPU("RTX 3060", gpu, "NVIDIA", "12GB", "GDDR6", 1320, 1777, 170, 7000),
                    new GPU("RTX 3090 Ti", gpu, "NVIDIA", "24GB", "GDDR6X", 1560, 1860, 450, 13000),
                    new GPU("RTX 4060", gpu, "NVIDIA", "8GB", "GDDR6", 1830, 2460, 115, 8500),
                    new GPU("RTX 4060 Ti", gpu, "NVIDIA", "8GB", "GDDR6", 2310, 2535, 160, 9500),
                    new GPU("RTX 4070", gpu, "NVIDIA", "12GB", "GDDR6X", 1920, 2475, 200, 11000),
                    new GPU("RTX 4070 SUPER", gpu, "NVIDIA", "12GB", "GDDR6X", 1980, 2550, 220, 11500),
                    new GPU("RTX 4070 Ti", gpu, "NVIDIA", "12GB", "GDDR6X", 2310, 2610, 285, 12000),
                    new GPU("RTX 4070 Ti SUPER", gpu, "NVIDIA", "12GB", "GDDR6X", 2330, 2630, 300, 12500),
                    new GPU("RTX 4080", gpu, "NVIDIA", "16GB", "GDDR6X", 2205, 2505, 320, 14000),
                    new GPU("RTX 4080 SUPER", gpu, "NVIDIA", "16GB", "GDDR6X", 2235, 2535, 350, 14500),
                    new GPU("RTX 4090", gpu, "NVIDIA", "24GB", "GDDR6X", 2235, 2520, 450, 16000),
                    new GPU("RX 6500 XT", gpu, "AMD", "4GB", "GDDR6", 2610, 2815, 107, 4500),
                    new GPU("RX 6600", gpu, "AMD", "8GB", "GDDR6", 2044, 2491, 132, 6000),
                    new GPU("RX 6600 XT", gpu, "AMD", "8GB", "GDDR6", 1968, 2589, 160, 6500),
                    new GPU("RX 6700 XT", gpu, "AMD", "12GB", "GDDR6", 2321, 2581, 230, 8000),
                    new GPU("RX 6800", gpu, "AMD", "16GB", "GDDR6", 1700, 2105, 250, 9500),
                    new GPU("RX 6800 XT", gpu, "AMD", "16GB", "GDDR6", 1825, 2250, 300, 10500),
                    new GPU("RX 6900 XT", gpu, "AMD", "16GB", "GDDR6", 1825, 2250, 300, 11500),
                    new GPU("RX 7600", gpu, "AMD", "8GB", "GDDR6", 2250, 2655, 165, 6000),
                    new GPU("RX 7600 XT", gpu, "AMD", "8GB", "GDDR6", 2250, 2655, 165, 7000),
                    new GPU("RX 7700 XT", gpu, "AMD", "12GB", "GDDR6", 2321, 2584, 230, 8500),
                    new GPU("RX 7800 XT", gpu, "AMD", "16GB", "GDDR6", 2124, 2430, 285, 10000),
                    new GPU("RX 7900 GRE", gpu, "AMD", "20GB", "GDDR6", 2000, 2400, 300, 10500),
                    new GPU("RX 7900 XT", gpu, "AMD", "20GB", "GDDR6", 1900, 2400, 300, 12500),
                    new GPU("RX 7900 XTX", gpu, "AMD", "24GB", "GDDR6", 1850, 2499, 355, 14000)
            };

            for (GPU model : gpus) {
                Optional<GPU> modelOptional = gpuService.findByNameAndPart(model.getName(), gpu);
                if (modelOptional.isEmpty()) {
                    gpuService.save(model);
                }
            }
        } else {
            System.err.println("GPU part not found, models cannot be seeded.");
        }
    }
}
