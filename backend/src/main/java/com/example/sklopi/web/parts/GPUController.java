package com.example.sklopi.web.parts;

import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.parts.GPURepository;
import com.example.sklopi.service.parts.GPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/gpus")
public class GPUController {

    @Autowired
    private GPUService gpuService;

    @Autowired
    private GPURepository gpuRepository;

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getGPUs(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) List<String> memorySize,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "performanceValue") String sortBy,
            @RequestParam(required = false) Integer minMemorySize,
            @RequestParam(required = false) Integer maxMemorySize,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("brand", brand);
        attributes.put("memorySize", memorySize);

        Map<String, Object> response = gpuService.getFilteredProducts(
                GPU.class, name, attributes, minPrice, maxPrice, minMemorySize, maxMemorySize, sortBy, pageable);

        response.put("minPrice", gpuRepository.findMinPrice());
        response.put("maxPrice", gpuRepository.findMaxPrice());

        response.put("minMemorySize", gpuRepository.findMinMemorySize());
        response.put("maxMemorySize", gpuRepository.findMaxMemorySize());

        response.put("distinctName", gpuRepository.findDistinctNames());
        response.put("distinctBrand", gpuRepository.findDistinctBrands());
        response.put("distinctMemorySize", gpuRepository.findDistinctMemorySize());

        return ResponseEntity.ok(response);
    }
}
