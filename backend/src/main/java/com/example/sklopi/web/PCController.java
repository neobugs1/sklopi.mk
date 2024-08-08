package com.example.sklopi.web;

import com.example.sklopi.model.PC;
import com.example.sklopi.model.Product;
import com.example.sklopi.service.PCService;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/pcs")
public class PCController {

    private final PCService pcService;
    private final ProductService productService;

    @Autowired
    public PCController(PCService pcService, ProductService productService) {
        this.pcService = pcService;
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<PC> createPC(@RequestBody PC pc) {
        PC createdPC = pcService.createPC(pc);
        return ResponseEntity.ok(createdPC);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PC> getPCById(@PathVariable Long id) {
        Optional<PC> pc = pcService.getPCById(id);
        return pc.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<PC> updatePC(@PathVariable Long id, @RequestBody PC updatedPC) {
        Optional<PC> existingPC = pcService.getPCById(id);
        if (existingPC.isPresent()) {
            updatedPC.setId(id);
            PC savedPC = pcService.updatePC(updatedPC);
            return ResponseEntity.ok(savedPC);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/add-component")
    public ResponseEntity<PC> addComponentToPC(@PathVariable Long id, @RequestBody Map<String, Long> request) {
        Long productId = request.get("productId");
        Optional<PC> optionalPC = pcService.getPCById(id);

        if (optionalPC.isPresent()) {
            PC pc = optionalPC.get();
            Product product = productService.getProductById(productId);

            if (product != null) {
                pc = pcService.addComponent(pc, product);
                return ResponseEntity.ok(pc);
            } else {
                return ResponseEntity.notFound().build();
            }
        } else {
            return ResponseEntity.notFound().build();
        }
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePC(@PathVariable Long id) {
        pcService.deletePC(id);
        return ResponseEntity.noContent().build();
    }
}