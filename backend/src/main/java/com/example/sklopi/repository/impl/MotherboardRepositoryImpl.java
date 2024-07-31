package com.example.sklopi.repository.impl;

import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.repository.custom.MotherboardRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;


import java.util.List;

@Repository
public class MotherboardRepositoryImpl implements MotherboardRepositoryCustom {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Page<Motherboard> findFilteredMotherboards(String socket, String formFactor, Pageable pageable) {
        StringBuilder queryBuilder = new StringBuilder("SELECT m FROM Motherboard m WHERE 1=1");

        if (socket != null && !socket.isEmpty()) {
            queryBuilder.append(" AND m.socket = :socket");
        }
        if (formFactor != null && !formFactor.isEmpty()) {
            queryBuilder.append(" AND m.formFactor = :formFactor");
        }

        TypedQuery<Motherboard> query = entityManager.createQuery(queryBuilder.toString(), Motherboard.class);

        if (socket != null && !socket.isEmpty()) {
            query.setParameter("socket", socket);
        }
        if (formFactor != null && !formFactor.isEmpty()) {
            query.setParameter("formFactor", formFactor);
        }

        int totalRows = query.getResultList().size();
        query.setFirstResult((int) pageable.getOffset());
        query.setMaxResults(pageable.getPageSize());

        List<Motherboard> resultList = query.getResultList();
        return new PageImpl<>(resultList, pageable, totalRows);
    }
}
