package com.example.sklopi.repository;

import com.example.sklopi.model.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Optional<Product> findByName(String name);
    Optional<Product> findByNameAndPartModel(String name, PartModel partModel);
    Optional<Product> findByProductUrl(String productUrl);
}
