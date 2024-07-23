package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.model.parts.RAM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RAMRepository extends JpaRepository<RAM, Long> {
    Optional<RAM> findByNameAndPart(String name, Part part);
}