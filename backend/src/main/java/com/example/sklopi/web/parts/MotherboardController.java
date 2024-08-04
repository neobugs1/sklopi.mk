package com.example.sklopi.web.parts;

import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.service.parts.MotherboardService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/motherboards")
public class MotherboardController {
    private final MotherboardService motherboardService;

    public MotherboardController(MotherboardService motherboardService) {
        this.motherboardService = motherboardService;
    }

    @GetMapping
    public Page<Motherboard> getFilteredMotherboards(
            @RequestParam(required = false) String memoryType,
            @RequestParam(required = false) String socket,
            Pageable pageable
    ) {
        return motherboardService.getFilteredMotherboards(memoryType, socket, pageable);
    }

}


