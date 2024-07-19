package com.example.sklopi.service;

import com.example.sklopi.model.Product;
import com.example.sklopi.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id).orElse(null);
    }

    public void save(Product product) {
        productRepository.save(product);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}