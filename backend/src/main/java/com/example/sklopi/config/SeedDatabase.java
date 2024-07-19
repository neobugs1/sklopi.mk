package com.example.sklopi.config;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.repository.PartModelRepository;
import com.example.sklopi.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class SeedDatabase implements CommandLineRunner {

    @Autowired
    private PartRepository partRepository;

    @Autowired
    private PartModelRepository partModelRepository;

    @Override
    public void run(String... args) throws Exception {
        seedPartsAndPartModels();
    }

    private void seedPartsAndPartModels() {
        // Create parts
        Part gpu = new Part();
        gpu.setName("GPU");
        partRepository.save(gpu);

        Part cpu = new Part();
        cpu.setName("CPU");
        partRepository.save(cpu);

        Part cooler = new Part();
        cooler.setName("Cooler");
        partRepository.save(cooler);

        Part psu = new Part();
        psu.setName("PSU");
        partRepository.save(psu);

        Part motherboard = new Part();
        motherboard.setName("Motherboard");
        partRepository.save(motherboard);

        Part memory = new Part();
        memory.setName("Memory");
        partRepository.save(memory);

        Part ram = new Part();
        ram.setName("RAM");
        partRepository.save(ram);

        Part casePart = new Part();
        casePart.setName("Case");
        partRepository.save(casePart);

        // Create GPU part models
        PartModel rtx3060Ti = new PartModel();
        rtx3060Ti.setName("RTX 3060 Ti");
        rtx3060Ti.setPart(gpu);
        partModelRepository.save(rtx3060Ti);

        PartModel rtx3070 = new PartModel();
        rtx3070.setName("RTX 3070");
        rtx3070.setPart(gpu);
        partModelRepository.save(rtx3070);

        PartModel gtx1650 = new PartModel();
        gtx1650.setName("GTX 1650");
        gtx1650.setPart(gpu);
        partModelRepository.save(gtx1650);

        PartModel rx7600 = new PartModel();
        rx7600.setName("RX 7600");
        rx7600.setPart(gpu);
        partModelRepository.save(rx7600);

        System.out.println("Database seeded successfully.");
    }
}
