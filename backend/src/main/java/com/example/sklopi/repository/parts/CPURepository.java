package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.CPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CPURepository extends JpaRepository<CPU, Long> {}