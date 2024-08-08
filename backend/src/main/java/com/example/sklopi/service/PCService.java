package com.example.sklopi.service;


import com.example.sklopi.model.PC;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.*;
import com.example.sklopi.repository.PCRepository;
import com.example.sklopi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PCService {
    private final PCRepository pcRepository;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    public PCService(PCRepository pcRepository) {
        this.pcRepository = pcRepository;
    }

    public PC createPC(PC pc) {
        return pcRepository.save(pc);
    }

    public Optional<PC> getPCById(Long id) {
        return pcRepository.findById(id);
    }

    public PC updatePC(PC pc) {
        return pcRepository.save(pc);
    }

    public void deletePC(Long id) {
        pcRepository.deleteById(id);
    }

    public PC addComponent(PC pc, Product product) {
        if (product.getPartModel() instanceof CPU) {
            pc.setCpu(product);
        } else if (product.getPartModel() instanceof GPU) {
            pc.setGpu(product);
        } else if (product.getPartModel() instanceof RAM) {
            // Handle RAM as a list
            pc.addRam(product);
        } else if (product.getPartModel() instanceof Storage) {
            // Handle Storage as a list
            pc.addStorage(product);
        } else if (product.getPartModel() instanceof Motherboard) {
            pc.setMotherboard(product);
        } else if (product.getPartModel() instanceof PSU) {
            pc.setPsu(product);
        } else if (product.getPartModel() instanceof PCCase) {
            pc.setPcCase(product);
        } else if (product.getPartModel() instanceof CPUCooler) {
            pc.setCpucooler(product);
        }

        return pcRepository.save(pc);
    }
}
