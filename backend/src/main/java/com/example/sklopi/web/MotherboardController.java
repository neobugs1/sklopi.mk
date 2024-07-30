package com.example.sklopi.web;

import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.service.parts.MotherboardService;
import com.example.sklopi.service.parts.PaginationResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/motherboards")
public class MotherboardController {
    private final MotherboardService motherboardService;

    public MotherboardController(MotherboardService motherboardService) {
        this.motherboardService = motherboardService;
    }

    @GetMapping
    public ResponseEntity<PaginationResponse<Product>> getMotherboards(@RequestParam(defaultValue = "1") int page) {
        PaginationResponse<Product> response = motherboardService.getProductsByPartModelType(Motherboard.class, page);
        return ResponseEntity.ok(response);
    }
}


