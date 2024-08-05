package com.example.sklopi.web.parts;

import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.repository.parts.CPURepository;
import com.example.sklopi.service.ProductService;
import com.example.sklopi.service.parts.CPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/cpus")
public class CPUController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CPURepository cpuRepository;

    @Autowired
    private CPUService cpuService;

    @GetMapping("/products")
    public ResponseEntity<Map<String, Object>> getCPUs(
            @RequestParam(required = false) List<String> name,
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) List<String> socket,
            @RequestParam(required = false) List<String> totalCores,
            @RequestParam(required = false) List<String> boostClock,
            @RequestParam(required = false) List<String> tdp,
            @RequestParam(required = false) List<String> hyperthreading,
            @RequestParam(required = false) Double minPrice,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false, defaultValue = "price") String sortBy,
            @RequestParam(required = false) Integer minTotalCores,
            @RequestParam(required = false) Integer maxTotalCores,
            @RequestParam(required = false) Double minBoostClock,
            @RequestParam(required = false) Double maxBoostClock,
            @RequestParam(required = false) Integer minTdp,
            @RequestParam(required = false) Integer maxTdp,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Pageable pageable = PageRequest.of(page, size);
        Map<String, List<String>> attributes = new HashMap<>();
        attributes.put("brand", brand);
        attributes.put("socket", socket);
        attributes.put("totalCores", totalCores);
        attributes.put("boostClock", boostClock);
        attributes.put("tdp", tdp);
        attributes.put("hyperthreading", hyperthreading);

        Map<String, Object> response = cpuService.getFilteredProducts(
                CPU.class, name, attributes, minPrice, maxPrice, minTotalCores, maxTotalCores, minBoostClock, maxBoostClock, minTdp, maxTdp, sortBy, pageable);

        response.put("minPrice", cpuRepository.findMinPrice());
        response.put("maxPrice", cpuRepository.findMaxPrice());

        response.put("minTotalCores", cpuRepository.findMinTotalCores());
        response.put("maxTotalCores", cpuRepository.findMaxTotalCores());

        response.put("minBoostClock", cpuRepository.findMinBoostClock());
        response.put("maxBoostClock", cpuRepository.findMaxBoostClock());

        response.put("minTdp", cpuRepository.findMinTdp());
        response.put("maxTdp", cpuRepository.findMaxTdp());

        response.put("distinctName", cpuRepository.findDistinctNames());
        response.put("distinctBrand", cpuRepository.findDistinctBrands());
        response.put("distinctSocket", cpuRepository.findDistinctSockets());
        response.put("distinctTotalCores", cpuRepository.findDistinctCoreCounts());
        response.put("distinctBoostClock", cpuRepository.findDistinctPerformanceCoreBoostClocks());
        response.put("distinctTdp", cpuRepository.findDistinctTDPs());
        response.put("distinctHyperthreading", cpuRepository.findDistinctSMTs());

        return ResponseEntity.ok(response);
    }


}
