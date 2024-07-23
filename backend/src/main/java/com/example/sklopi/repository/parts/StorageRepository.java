package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.RAM;
import com.example.sklopi.model.parts.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByNameAndPart(String name, Part part);
}
