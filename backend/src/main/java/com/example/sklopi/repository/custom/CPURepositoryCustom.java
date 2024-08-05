package com.example.sklopi.repository.custom;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface CPURepositoryCustom {
    Page<Product> findFilteredProducts(
            Class<? extends PartModel> partModelType,
            List<String> names,
            Map<String, List<String>> attributes,
            Double minPrice,
            Double maxPrice,
            Integer minCoreCount,
            Integer maxCoreCount,
            Double minBoostClock,
            Double maxBoostClock,
            Integer minTdp,
            Integer maxTdp,
            String sortBy,
            Pageable pageable);
}