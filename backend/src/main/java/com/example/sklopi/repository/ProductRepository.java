package com.example.sklopi.repository;

import com.example.sklopi.model.*;
import com.example.sklopi.repository.custom.ProductRepositoryCustom;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, ProductRepositoryCustom {
    Optional<Product> findByName(String name);

    Optional<Product> findByNameAndPartModel(String name, PartModel partModel);

    Optional<Product> findByProductUrl(String productUrl);

    Page<Product> findAll(Specification<Product> spec, Pageable pageable);

    @Query("SELECT MIN(p.price) FROM Product p JOIN Motherboard m on p.partModel.id=m.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN Motherboard m on p.partModel.id=m.id")
    Double findMaxPrice();


}
