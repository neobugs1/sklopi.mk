package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.Motherboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long>, PagingAndSortingRepository<Motherboard, Long> {
    Optional<Motherboard> findByNameAndPart(String name, Part part);

    @Query("SELECT p FROM Product p WHERE TYPE(p.partModel) = :partModelType")
    Page<Product> findByPartModelType(@Param("partModelType") Class<?> partModelType, Pageable pageable);
}
