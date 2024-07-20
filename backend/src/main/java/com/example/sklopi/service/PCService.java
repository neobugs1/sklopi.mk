package com.example.sklopi.service;


import com.example.sklopi.model.PC;
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
}
