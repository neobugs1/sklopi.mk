package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.CPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CPURepository extends JpaRepository<CPU, Long> {
    Optional<CPU> findByNameAndPart(String name, Part part);
}