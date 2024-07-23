package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.model.parts.PcCase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PcCaseRepository extends JpaRepository<PcCase, Long> {
    Optional<PcCase> findByNameAndFormFactor(String brand, String formFactor);
}