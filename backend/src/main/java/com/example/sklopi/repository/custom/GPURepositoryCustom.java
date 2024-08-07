package com.example.sklopi.repository.custom;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface GPURepositoryCustom {
    Page<Product> findFilteredProducts(
            Class<? extends PartModel> partModelType,
            List<String> names,
            Map<String, List<String>> attributes,
            Double minPrice,
            Double maxPrice,
            Integer minVram,
            Integer maxVram,
            String sortBy,
            Pageable pageable);
}
