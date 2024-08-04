package com.example.sklopi.web;

import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.*;
import com.example.sklopi.repository.ProductRepository;
import com.example.sklopi.repository.parts.*;
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

    @Autowired
    private CPUCoolerRepository cpuCoolerRepository;

    @Autowired
    private PcCaseRepository pcCaseRepository;

    @Autowired
    private StorageRepository storageRepository;

    @Autowired
    private PSURepository psuRepository;

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

    @GetMapping("/cases")
    public ResponseEntity<Map<String, Object>> getCases(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> formFactor,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("formFactor", formFactor);

        Map<String, Object> response = productService.getFilteredProducts(
                PCCase.class, name, attributes, minPrice, maxPrice, sortBy, pageable);

        response.put("minPrice", pcCaseRepository.findMinPrice());
        response.put("maxPrice", pcCaseRepository.findMaxPrice());

        response.put("distinctName", pcCaseRepository.findDistinctBrands());
        response.put("distinctFormFactor", pcCaseRepository.findDistinctFormFactors());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/cpu-coolers")
    public ResponseEntity<Map<String, Object>> getCpuCoolers(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> coolerType,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("coolerType", coolerType);

        Map<String, Object> response = productService.getFilteredProducts(
                CPUCooler.class, name, attributes, minPrice, maxPrice, sortBy, pageable);

        response.put("minPrice", cpuCoolerRepository.findMinPrice());
        response.put("maxPrice", cpuCoolerRepository.findMaxPrice());

        response.put("distinctName", cpuCoolerRepository.findDistinctBrands());
        response.put("distinctCoolerType", cpuCoolerRepository.findDistinctCoolerTypes());

        return ResponseEntity.ok(response);
    }

    @GetMapping("/storage")
    public ResponseEntity<Map<String, Object>> getStorage(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> formFactor,
            @RequestParam(required = false) List<String> capacity,
            @RequestParam(required = false) List<String> type,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("formFactor", formFactor);
        attributes.put("capacity", capacity);
        attributes.put("type", type);

        Map<String, Object> response = productService.getFilteredProducts(
                Storage.class, name, attributes, minPrice, maxPrice, sortBy, pageable);

        response.put("minPrice", storageRepository.findMinPrice());
        response.put("maxPrice", storageRepository.findMaxPrice());

        response.put("distinctName", storageRepository.findDistinctBrands());
        response.put("distinctCapacity", storageRepository.findDistinctCapacities());
        response.put("distinctFormFactor", storageRepository.findDistinctFormFactor());
        response.put("distinctType", storageRepository.findDistinctTypes());

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}