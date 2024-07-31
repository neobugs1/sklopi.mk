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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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

    public Page<Product> getProducts(String name, Integer price, Long partModelId, int page, int size, String sortBy, String direction) {
        Pageable pageable = PageRequest.of(page, size, Sort.Direction.fromString(direction), sortBy);
        return productRepository.findAll((root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (name != null) {
                predicates.add(criteriaBuilder.like(root.get("name"), "%" + name + "%"));
            }
            if (price != null) {
                predicates.add(criteriaBuilder.equal(root.get("price"), price));
            }
            if (partModelId != null) {
                predicates.add(criteriaBuilder.equal(root.join("partModel").get("id"), partModelId));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        }, pageable);
    }

//    public Page<Product> getFilteredProducts(String name, String socket, String supportedMemory, String formFactor, Pageable pageable) {
//        return productRepository.findFilteredProducts(name, socket, supportedMemory, formFactor, pageable);
//    }
}

