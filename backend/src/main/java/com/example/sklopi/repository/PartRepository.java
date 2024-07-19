package com.example.sklopi.repository;

import com.example.sklopi.model.Part;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PartRepository extends JpaRepository<Part, Long> {
    Optional<Part> findByName(String name);
}
