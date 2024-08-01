package com.example.sklopi.repository.custom;

import com.example.sklopi.model.PartModel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.example.sklopi.model.Product;

import java.util.List;
import java.util.Map;

public interface ProductRepositoryCustom {
    Page<Product> findFilteredProducts(
            Class<? extends PartModel> partModelType,
            List<String> names,
            Map<String, List<String>> attributes,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            Pageable pageable);
}
