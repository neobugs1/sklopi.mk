package com.example.sklopi.repository.impl;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.example.sklopi.model.Product;

import java.util.List;
import java.util.Map;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findFilteredProducts(
            Class<? extends PartModel> partModelType,
            List<String> names,
            Map<String, List<String>> attributes,
            Double minPrice,
            Double maxPrice,
            String sortBy,
            Pageable pageable) {

        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Product p JOIN p.partModel pm WHERE TYPE(pm) = :partModelType");

        if (names != null && !names.isEmpty()) {
            queryBuilder.append(" AND pm.name IN :names");
        }

        for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                queryBuilder.append(" AND pm.").append(entry.getKey()).append(" IN :").append(entry.getKey());
            }
        }

        if (minPrice != null) {
            queryBuilder.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            queryBuilder.append(" AND p.price <= :maxPrice");
        }

        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            if ("efficiencyRating".equals(sortBy) && partModelType.equals(PSU.class)) {
                queryBuilder.append(" ORDER BY CASE WHEN pm.efficiencyRating = 'Gold' THEN 1 ELSE 2 END, pm.efficiencyRating ASC");
            } else {
                queryBuilder.append(" ORDER BY p.").append(sortBy);
            }
        }

        TypedQuery<Product> query = entityManager.createQuery(queryBuilder.toString(), Product.class);
        query.setParameter("partModelType", partModelType);

        if (names != null && !names.isEmpty()) {
            query.setParameter("names", names);
        }

        for (Map.Entry<String, List<String>> entry : attributes.entrySet()) {
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if (minPrice != null) {
            query.setParameter("minPrice", minPrice);
        }
        if (maxPrice != null) {
            query.setParameter("maxPrice", maxPrice);
        }

        // Apply pagination
        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Product> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, totalRows);
    }
}

