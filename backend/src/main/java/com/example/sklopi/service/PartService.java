package com.example.sklopi.service;

import com.example.sklopi.model.Part;
import com.example.sklopi.repository.PartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PartService {
    @Autowired
    private PartRepository partRepository;

    public List<Part> getAllParts() {
        return partRepository.findAll();
    }

    public Part getPartById(Long id) {
        return partRepository.findById(id).orElse(null);
    }

    public Part savePart(Part part) {
        return partRepository.save(part);
    }

    public void deletePart(Long id) {
        partRepository.deleteById(id);
    }

    public Optional<Part> findByName(String name) {
        return partRepository.findByName(name);
    }
}
