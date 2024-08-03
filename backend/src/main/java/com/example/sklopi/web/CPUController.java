package com.example.sklopi.web;

import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.service.parts.CPUService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cpus")
public class CPUController {

    @Autowired
    private CPUService cpuService;

//    @GetMapping
//    public Page<CPU> getCPUs(
//            @RequestParam(required = false) String sortBy,
//            @RequestParam(required = false, defaultValue = "asc") String sortOrder,
//            Pageable pageable) {
//        return cpuService.getCPUs(sortBy, sortOrder, pageable);
//    }
//
//    @GetMapping("/{id}")
//    public CPU getCPUById(@PathVariable Long id) {
//        return cpuService.getCPUById(id);
//    }
//
//    @GetMapping("/search")
//    public List<CPU> searchCPUs(@RequestParam String query) {
//        return cpuService.searchCPUs(query);
//    }
}
