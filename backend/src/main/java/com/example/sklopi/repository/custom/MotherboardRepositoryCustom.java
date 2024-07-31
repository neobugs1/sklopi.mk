package com.example.sklopi.repository.custom;

import com.example.sklopi.model.parts.Motherboard;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MotherboardRepositoryCustom {
    Page<Motherboard> findFilteredMotherboards(String socket, String formFactor, Pageable pageable);
}
