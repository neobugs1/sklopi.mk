package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.GPU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GPURepository extends JpaRepository<GPU, Long> {}