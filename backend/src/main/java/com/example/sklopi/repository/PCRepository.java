package com.example.sklopi.repository;

import com.example.sklopi.model.PC;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PCRepository extends JpaRepository<PC, Long> {
    Optional<PC> findById(Long id);
}