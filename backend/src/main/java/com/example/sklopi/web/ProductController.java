package com.example.sklopi.web;

import com.example.sklopi.model.Product;
import com.example.sklopi.repository.ProductRepository;
import com.example.sklopi.repository.parts.MotherboardRepository;
import com.example.sklopi.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
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

//    @GetMapping
//    public List<Product> getAllProducts() {
//        return productService.getAllProducts();
//    }

    @GetMapping("/{id}")
    public Product getProductById(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    //    @PostMapping
//    public Product createProduct(@RequestBody Product product) {
//        return productService.save(product);
//    }
//
//    @PutMapping("/{id}")
//    public Product updateProduct(@PathVariable Long id, @RequestBody Product product) {
//        product.setId(id);
//        return productService.save(product);
////    }
//    @GetMapping
//    public Page<Product> getProducts(
//            @RequestParam(required = false) String name,
//            @RequestParam(required = false) Integer price,
//            @RequestParam(required = false) Long partModelId,
//            @RequestParam(required = false) Double minPrice,
//            @RequestParam(required = false) Double maxPrice,
//            @RequestParam(defaultValue = "0") int page,
//            @RequestParam(defaultValue = "10") int size,
//            @RequestParam(defaultValue = "name") String sortBy,
//            @RequestParam(defaultValue = "asc") String direction
//    ) {
//        return productService.getProducts(name, price, partModelId, page, size, minPrice, maxPrice, sortBy, direction);
//    }

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

        Page<Product> products = productRepository.findFilteredProducts(name, socket, supportedMemory, formFactor, minPrice, maxPrice, sortBy, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products.getContent());
        response.put("totalElements", products.getTotalElements());
        response.put("totalPages", products.getTotalPages());
        response.put("size", products.getSize());
        response.put("minPrice", productRepository.findMinPrice());
        response.put("maxPrice", productRepository.findMaxPrice());
        response.put("distinctFormFactors", motherboardRepository.findDistinctFormFactors());
        response.put("distinctSupportedMemory", motherboardRepository.findDistinctSupportedMemory());
        response.put("distinctSockets", motherboardRepository.findDistinctSockets());

        return ResponseEntity.ok(response);
    }


    @DeleteMapping("/{id}")
    public void deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
    }
}