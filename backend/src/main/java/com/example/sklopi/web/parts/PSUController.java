package com.example.sklopi.web.parts;

import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.repository.parts.PSURepository;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/psus")
public class PSUController {
    @Autowired
    private PSURepository psuRepository;

    @Autowired
    private ProductService productService;

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getPSUs(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> wattage,
            @RequestParam(required = false) List<String> efficiencyRating,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "efficiencyRating") String sortBy,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("wattage", wattage);
        attributes.put("efficiencyRating", efficiencyRating);

        Map<String, Object> response = productService.getFilteredProducts(
                PSU.class, name, attributes, minPrice, maxPrice, sortBy, pageable);

        response.put("minPrice", psuRepository.findMinPrice());
        response.put("maxPrice", psuRepository.findMaxPrice());

        response.put("distinctName", psuRepository.findDistinctBrands());
        response.put("distinctWattage", psuRepository.findDistinctWattage());
        response.put("distinctEfficiencyRating", psuRepository.findDistinctEfficiencyRatings());

        return ResponseEntity.ok(response);
    }
}
