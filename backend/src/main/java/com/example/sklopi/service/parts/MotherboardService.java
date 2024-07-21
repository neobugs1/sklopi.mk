package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.repository.parts.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotherboardService {

    @Autowired
    private MotherboardRepository motherboardRepository;

    public Optional<Motherboard> findByNameAndPart(String name, Part part) {
        return motherboardRepository.findByNameAndPart(name, part);
    }

    public void save(Motherboard motherboard) {
        motherboardRepository.save(motherboard);
    }
    public List<Motherboard> findAll() {
        return motherboardRepository.findAll();
    }
}
