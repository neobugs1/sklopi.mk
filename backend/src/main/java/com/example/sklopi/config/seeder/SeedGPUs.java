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
                    new GPU("GTX 1660 Ti", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 1000),
                    new GPU("GTX 1660 SUPER", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 1000),
                    new GPU("GeForce GTX 1080 Ti", gpu, "NVIDIA", "11GB", "GDDR5X", 1480, 1582, 250, 3014), // 30.14%

                    new GPU("RTX 2060", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 2365),
                    new GPU("GeForce RTX 2060 SUPER", gpu, "NVIDIA", "8GB", "GDDR6", 1470, 1650, 175, 2657), // 26.57%
                    new GPU("GeForce RTX 2070", gpu, "NVIDIA", "8GB", "GDDR6", 1410, 1620, 175, 2692), // 26.92%
                    new GPU("GeForce RTX 2070 SUPER", gpu, "NVIDIA", "8GB", "GDDR6", 1605, 1770, 215, 3046), // 30.46%
                    new GPU("GeForce GTX 1080 Ti", gpu, "NVIDIA", "11GB", "GDDR5X", 1480, 1582, 250, 3014), // 30.14%
                    new GPU("GeForce RTX 2080", gpu, "NVIDIA", "8GB", "GDDR6", 1515, 1710, 215, 3276), // 32.76%
                    new GPU("GeForce RTX 2080 SUPER", gpu, "NVIDIA", "8GB", "GDDR6", 1650, 1815, 250, 3435), // 34.35%

                    new GPU("RTX 3050", gpu, "NVIDIA", "8GB", "GDDR6", 1552, 1777, 130, 1000),
                    new GPU("RTX 3060", gpu, "NVIDIA", "12GB", "GDDR6", 1320, 1777, 170, 2747),
                    new GPU("GeForce RTX 3060 Ti", gpu, "NVIDIA", "8GB", "GDDR6", 1410, 1665, 200, 3519), // 35.19%
                    new GPU("GeForce RTX 3070", gpu, "NVIDIA", "8GB", "GDDR6", 1500, 1725, 220, 4121), // 41.21%
                    new GPU("GeForce RTX 3070 Ti", gpu, "NVIDIA", "8GB", "GDDR6X", 1575, 1770, 290, 4409), // 44.09%
                    new GPU("GeForce RTX 3080", gpu, "NVIDIA", "10GB", "GDDR6X", 1440, 1710, 320, 5356), // 53.56%
                    new GPU("GeForce RTX 3080 Ti", gpu, "NVIDIA", "12GB", "GDDR6X", 1365, 1665, 350, 5999), // 59.99%
                    new GPU("GeForce RTX 3090", gpu, "NVIDIA", "24GB", "GDDR6X", 1395, 1695, 350, 6112), // 61.12%
                    new GPU("GeForce RTX 3090 Ti", gpu, "NVIDIA", "24GB", "GDDR6X", 1560, 1860, 450, 7090), // 70.90%

                    new GPU("RTX 4060", gpu, "NVIDIA", "8GB", "GDDR6", 1830, 2460, 115, 3255),
                    new GPU("RTX 4060 Ti", gpu, "NVIDIA", "8GB", "GDDR6", 2310, 2535, 160, 3938),
                    new GPU("RTX 4070", gpu, "NVIDIA", "12GB", "GDDR6X", 1920, 2475, 200, 5033),
                    new GPU("RTX 4070 SUPER", gpu, "NVIDIA", "12GB", "GDDR6X", 1980, 2550, 220, 5838),
                    new GPU("RTX 4070 Ti", gpu, "NVIDIA", "12GB", "GDDR6X", 2310, 2610, 285, 6266),
                    new GPU("RTX 4070 Ti SUPER", gpu, "NVIDIA", "12GB", "GDDR6X", 2330, 2630, 300, 6845),
                    new GPU("RTX 4080", gpu, "NVIDIA", "16GB", "GDDR6X", 2205, 2505, 320, 7960),
                    new GPU("RTX 4080 SUPER", gpu, "NVIDIA", "16GB", "GDDR6X", 2235, 2535, 350, 8079),
                    new GPU("RTX 4090", gpu, "NVIDIA", "24GB", "GDDR6X", 2235, 2520, 450, 10000),

                    new GPU("RX 6500 XT", gpu, "AMD", "4GB", "GDDR6", 2610, 2815, 107, 4500),
                    new GPU("RX 6600", gpu, "AMD", "8GB", "GDDR6", 2044, 2491, 132, 2571),
                    new GPU("RX 6600 XT", gpu, "AMD", "8GB", "GDDR6", 1968, 2589, 160, 2955),
                    new GPU("Radeon RX 6650 XT", gpu, "AMD", "8GB", "GDDR6", 2075, 2575, 180, 3204),
                    new GPU("RX 6700 XT", gpu, "AMD", "12GB", "GDDR6", 2321, 2581, 230, 3643),
                    new GPU("Radeon RX 6750 XT", gpu, "AMD", "12GB", "GDDR6", 2425, 2595, 235, 3908),
                    new GPU("RX 6800", gpu, "AMD", "16GB", "GDDR6", 1700, 2105, 250, 4542),
                    new GPU("RX 6800 XT", gpu, "AMD", "16GB", "GDDR6", 1825, 2250, 300, 5159),
                    new GPU("RX 6900 XT", gpu, "AMD", "16GB", "GDDR6", 1825, 2250, 300, 5559),

                    new GPU("RX 7600", gpu, "AMD", "8GB", "GDDR6", 2250, 2655, 165, 3240),
                    new GPU("RX 7600 XT", gpu, "AMD", "8GB", "GDDR6", 2250, 2655, 165, 3343),
                    new GPU("RX 7700 XT", gpu, "AMD", "12GB", "GDDR6", 2321, 2584, 230, 4456),
                    new GPU("RX 7800 XT", gpu, "AMD", "16GB", "GDDR6", 2124, 2430, 285, 5409),
                    new GPU("RX 7900 GRE", gpu, "AMD", "20GB", "GDDR6", 2000, 2400, 300, 5950),
                    new GPU("RX 7900 XT", gpu, "AMD", "20GB", "GDDR6", 1900, 2400, 300, 7030),
                    new GPU("RX 7900 XTX", gpu, "AMD", "24GB", "GDDR6", 1850, 2499, 355, 8156)

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
