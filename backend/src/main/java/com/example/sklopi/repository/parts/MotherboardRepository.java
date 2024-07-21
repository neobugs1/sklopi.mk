package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long> {
    Optional<Motherboard> findByNameAndPart(String name, Part part);
}
