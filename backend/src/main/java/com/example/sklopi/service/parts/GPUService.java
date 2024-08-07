package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.parts.GPURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class GPUService {

    @Autowired
    private GPURepository gpuRepository;

    public Optional<GPU> findByNameAndPart(String name, Part part) {
        return gpuRepository.findByNameAndPart(name, part);
    }

    public Optional<GPU> findByNamePartAndMemorySize(String name, Part part, int memorySize) {
        return gpuRepository.findByNameAndPartAndMemorySize(name, part, memorySize);
    }


    public GPU save(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public List<GPU> findAll() {
        return gpuRepository.findAll();
    }

    public Map<String, Object> getFilteredProducts(
            Class<? extends PartModel> partModelType,
            List<String> names,
            Map<String, List<String>> attributes,
            Double minPrice,
            Double maxPrice,
            Integer minVram,
            Integer maxVram,
            String sortBy,
            Pageable pageable) {

        Page<Product> pageProducts = gpuRepository.findFilteredProducts(
                partModelType, names, attributes, minPrice, maxPrice, minVram, maxVram, sortBy, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", pageProducts.getContent());
        response.put("currentPage", pageProducts.getNumber());
        response.put("totalItems", pageProducts.getTotalElements());
        response.put("totalPages", pageProducts.getTotalPages());

        return response;
    }
}


