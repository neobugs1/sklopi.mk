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

    public Product saveProduct(Product product) {
        Optional<Product> existingProduct = productRepository.findByName(product.getName());
        if (existingProduct.isPresent()) {
            Product existing = existingProduct.get();
            existing.setPrice(product.getPrice());
            existing.setImageUrl(product.getImageUrl());
            existing.setProductUrl(product.getProductUrl());
            return productRepository.save(existing);
        } else {
            return productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }
}
