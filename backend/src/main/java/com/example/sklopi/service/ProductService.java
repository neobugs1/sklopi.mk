package com.example.sklopi.service;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import com.example.sklopi.repository.ProductRepository;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Product saveProduct(Product product) {
        Optional<Product> existingProductOptional = productRepository.findByProductUrl(product.getProductUrl());
        if (existingProductOptional.isPresent()) {
            Product existingProduct = existingProductOptional.get();
            existingProduct.setName(product.getName());
            existingProduct.setImageUrl(product.getImageUrl());
            existingProduct.setPrice(product.getPrice());
            existingProduct.addPriceHistory(product.getPrice());
            existingProduct.setPartModel(product.getPartModel());
            existingProduct.setInStock(true);
            return productRepository.save(existingProduct);
        } else {
            product.addPriceHistory(product.getPrice());
            product.setInStock(true);
            return productRepository.save(product);
        }
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }

    public Optional<Product> findByName(String name) {
        return productRepository.findByName(name);
    }

    // figuring out product stock
    public void setAllProductsOutOfStock() {
        List<Product> allProducts = productRepository.findAll();
        for (Product product : allProducts) {
            if (product.isInStock()) {
                product.setInStock(false);
                productRepository.save(product);
            }
        }
    }

    public Map<String, Object> getFilteredProducts(
            Class<? extends PartModel> partModelType,
            List<String> name,
            Map<String, List<String>> attributes,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            Pageable pageable) {

        Page<Product> products = productRepository.findFilteredProducts(
                partModelType, name, attributes, minPrice, maxPrice, sortBy, pageable);

        Map<String, Object> response = new HashMap<>();
        response.put("products", products.getContent());
        response.put("totalElements", products.getTotalElements());
        response.put("totalPages", products.getTotalPages());
        response.put("size", products.getSize());

        return response;
    }
}

