package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.CPUCooler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CPUCoolerRepository extends JpaRepository<CPUCooler, Long> {
    Optional<CPUCooler> findByNameAndCoolerType(String name, String coolerType);

    @Query("SELECT MIN(p.price) FROM Product p JOIN CPUCooler cp on p.partModel.id=cp.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN CPUCooler cp on p.partModel.id=cp.id")
    Double findMaxPrice();

    @Query("SELECT DISTINCT cp.name FROM CPUCooler cp")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT cp.coolerType FROM CPUCooler cp")
    List<String> findDistinctCoolerTypes();
}