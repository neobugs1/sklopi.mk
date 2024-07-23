package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.CPUCooler;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CPUCoolerRepository extends JpaRepository<CPUCooler, Long> {
    Optional<CPUCooler> findByNameAndCoolerType(String name, String coolerType);
}