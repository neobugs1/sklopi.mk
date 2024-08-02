package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.PCCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PcCaseRepository extends JpaRepository<PCCase, Long> {
    Optional<PCCase> findByNameAndFormFactor(String brand, String formFactor);

    @Query("SELECT pc FROM PCCase pc")
    List<PCCase> findAllPcCases();

    @Query("SELECT MIN(p.price) FROM Product p JOIN PCCase pc on p.partModel.id=pc.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN PCCase pc on p.partModel.id=pc.id")
    Double findMaxPrice();

    @Query("SELECT DISTINCT m.name FROM PCCase m")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT m.formFactor FROM PCCase m")
    List<String> findDistinctFormFactors();
}