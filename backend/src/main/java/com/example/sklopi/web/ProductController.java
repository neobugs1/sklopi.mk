package com.example.sklopi.web;

import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.*;
import com.example.sklopi.repository.ProductRepository;
import com.example.sklopi.repository.parts.MotherboardRepository;
import com.example.sklopi.repository.parts.RAMRepository;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    @Autowired
    private ProductService productService;

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private MotherboardRepository motherboardRepository;

    @Autowired
    private RAMRepository ramRepository;

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping("/motherboards")
    public ResponseEntity<Map<String, Object>> getMotherboards(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> socket,
            @RequestParam(required = false) List<String> supportedMemory,
            @RequestParam(required = false) List<String> formFactor,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("socket", socket);
        attributes.put("supportedMemory", supportedMemory);
        attributes.put("formFactor", formFactor);

        Map<String, Object> response = productService.getFilteredProducts(
                Motherboard.class, name, attributes, minPrice, maxPrice, sortBy, pageable);

        response.put("minPrice", motherboardRepository.findMinPrice());
        response.put("maxPrice", motherboardRepository.findMaxPrice());

        response.put("distinctFormFactor", motherboardRepository.findDistinctFormFactors());
        response.put("distinctName", motherboardRepository.findDistinctChipsets());
        response.put("distinctSupportedMemory", motherboardRepository.findDistinctSupportedMemory());
        response.put("distinctSocket", motherboardRepository.findDistinctSockets());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/ram")
    public ResponseEntity<Map<String, Object>> getRam(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) List<String> capacity,
            @RequestParam(required = false) List<String> speed,
            @RequestParam(required = false) List<String> amount,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("type", type);
        attributes.put("capacity", capacity);
        attributes.put("frequency", speed);
        attributes.put("amount", amount);

        Map<String, Object> response = productService.getFilteredProducts(
                RAM.class, name, attributes, minPrice, maxPrice, sortBy, pageable);

        response.put("minPrice", ramRepository.findMinPrice());
        response.put("maxPrice", ramRepository.findMaxPrice());

        response.put("distinctName", ramRepository.findDistinctTypes());
        response.put("distinctAmount", ramRepository.findDistinctAmounts());
        response.put("distinctCapacity", ramRepository.findDistinctCapacities());
        response.put("distinctSpeed", ramRepository.findDistinctSpeeds());

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}