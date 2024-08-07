package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.custom.GPURepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface GPURepository extends JpaRepository<GPU, Long>, GPURepositoryCustom {
    Optional<GPU> findByNameAndPart(String name, Part part);

    @Query("SELECT MIN(p.price) FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    Double findMaxPrice();

    @Query("SELECT MIN(pm.memorySize) FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    Integer findMinMemorySize();

    @Query("SELECT MAX(pm.memorySize) FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    Integer findMaxMemorySize();

    @Query("SELECT DISTINCT pm.name FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    List<String> findDistinctNames();

    @Query("SELECT DISTINCT pm.brand FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT pm.memorySize FROM Product p JOIN GPU pm ON p.partModel.id=pm.id")
    List<String> findDistinctMemorySize();
}