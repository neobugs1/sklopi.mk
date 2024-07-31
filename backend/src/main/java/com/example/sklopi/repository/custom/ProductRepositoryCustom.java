package com.example.sklopi.repository.custom;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.sklopi.model.Product;

import java.util.List;

public interface ProductRepositoryCustom {
    Page<Product> findFilteredProducts(List<String> names, List<String> sockets, List<String> supportedMemories, List<String> formFactors, Double minPrice, Double maxPrice, String sortBy, Pageable pageable);
}