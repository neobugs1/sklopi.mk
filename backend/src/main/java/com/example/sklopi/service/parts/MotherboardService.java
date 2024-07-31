package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.repository.ProductRepository;
import com.example.sklopi.repository.parts.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class MotherboardService {

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private ProductRepository productRepository;

    public Optional<Motherboard> findByNameAndPart(String name, Part part) {
        return motherboardRepository.findByNameAndPart(name, part);
    }

    public void save(Motherboard motherboard) {
        motherboardRepository.save(motherboard);
    }

    public List<Motherboard> findAll() {
        return motherboardRepository.findAll();
    }

    public Page<Motherboard> getFilteredMotherboards(String memoryType, String socket, Pageable pageable) {
        return motherboardRepository.findFilteredMotherboards(memoryType, socket, pageable);
    }
}

