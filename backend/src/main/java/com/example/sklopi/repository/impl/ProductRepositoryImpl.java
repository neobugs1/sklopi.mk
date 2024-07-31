package com.example.sklopi.repository.impl;

import com.example.sklopi.repository.custom.ProductRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import com.example.sklopi.model.Product;

import java.util.List;

public class ProductRepositoryImpl implements ProductRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Product> findFilteredProducts(List<String> names, List<String> sockets, List<String> supportedMemories, List<String> formFactors, Double minPrice, Double maxPrice, String sortBy, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT p FROM Product p JOIN p.partModel pm WHERE TYPE(pm) = Motherboard");

        if (names != null && !names.isEmpty()) {
            queryBuilder.append(" AND pm.name IN :names");
        }
        if (sockets != null && !sockets.isEmpty()) {
            queryBuilder.append(" AND pm.socket IN :sockets");
        }
        if (supportedMemories != null && !supportedMemories.isEmpty()) {
            queryBuilder.append(" AND pm.supportedMemory IN :supportedMemories");
        }
        if (formFactors != null && !formFactors.isEmpty()) {
            queryBuilder.append(" AND pm.formFactor IN :formFactors");
        }
        if (minPrice != null) {
            queryBuilder.append(" AND p.price >= :minPrice");
        }
        if (maxPrice != null) {
            queryBuilder.append(" AND p.price <= :maxPrice");
        }

        // Apply sorting
        if (sortBy != null && !sortBy.isEmpty()) {
            queryBuilder.append(" ORDER BY p.").append(sortBy);
        }

        TypedQuery<Product> query = entityManager.createQuery(queryBuilder.toString(), Product.class);

        if (names != null && !names.isEmpty()) {
            query.setParameter("names", names);
        }
        if (sockets != null && !sockets.isEmpty()) {
            query.setParameter("sockets", sockets);
        }
        if (supportedMemories != null && !supportedMemories.isEmpty()) {
            query.setParameter("supportedMemories", supportedMemories);
        }
        if (formFactors != null && !formFactors.isEmpty()) {
            query.setParameter("formFactors", formFactors);
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
