package com.example.sklopi.service;


import com.example.sklopi.model.PC;
import com.example.sklopi.model.parts.*;
import com.example.sklopi.repository.PCRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PCService {
    @Autowired
    private PCRepository pcRepository;

    public List<PC> getAllPCs() {
        return pcRepository.findAll();
    }

    public PC getPCById(Long id) {
        return pcRepository.findById(id).orElse(null);
    }

    public PC savePC(PC pc) {
        // TODO:Implement compatibility validation here
        return pcRepository.save(pc);
    }

    public void deletePC(Long id) {
        pcRepository.deleteById(id);
    }

    private boolean isCompatible(PC pc) {
        CPU cpu = (CPU) pc.getCpu().getPartModel();
        Motherboard motherboard = (Motherboard) pc.getMotherboard().getPartModel();
        RAM ram = (RAM) pc.getRam().getPartModel();

        if (!cpu.getSocket().equals(motherboard.getSocket())) {
            return false;
        }

        if (!motherboard.getSupportedMemory().contains(ram.getType())) {
            return false;
        }

        return true;
    }

}
