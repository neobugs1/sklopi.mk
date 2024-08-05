package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.ProductRepository;
import com.example.sklopi.repository.parts.CPURepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class CPUService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CPURepository cpuRepository;

    public Optional<CPU> findByNameAndPart(String name, Part part) {
        return cpuRepository.findByNameAndPart(name, part);
    }

    public CPU save(CPU cpu) {
        return cpuRepository.save(cpu);
    }

    public List<CPU> findAll() {
        return cpuRepository.findAll();
    }

    public Map<String, Object> getFilteredProducts(
            Class<CPU> cpuClass, List<String> names, Map<String, List<String>> attributes, Double minPrice, Double maxPrice,
            Integer minCoreCount, Integer maxCoreCount, Double minBoostClock, Double maxBoostClock, Integer minTdp, Integer maxTdp,
            String sortBy, Pageable pageable) {

        Page<Product> products = cpuRepository.findFilteredProducts(
                cpuClass, names, attributes, minPrice, maxPrice, minCoreCount, maxCoreCount, minBoostClock, maxBoostClock, minTdp, maxTdp, sortBy, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products.getContent());
        response.put("totalElements", products.getTotalElements());
        response.put("totalPages", products.getTotalPages());
        response.put("size", products.getSize());

        return response;
    }


}
