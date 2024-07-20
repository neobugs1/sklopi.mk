package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {}
