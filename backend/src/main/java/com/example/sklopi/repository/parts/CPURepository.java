package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.CPU;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface CPURepository extends JpaRepository<CPU, Long> {
    Optional<CPU> findByNameAndPart(String name, Part part);

    @Query("SELECT DISTINCT c.name FROM CPU c")
    List<String> findDistinctNames();

    @Query("SELECT DISTINCT c.brand FROM CPU c")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT c.socket FROM CPU c")
    List<String> findDistinctSockets();

    @Query("SELECT DISTINCT c.totalCores FROM CPU c")
    List<String> findDistinctCoreCounts();

    @Query("SELECT DISTINCT c.boostClock FROM CPU c")
    List<String> findDistinctPerformanceCoreBoostClocks();

    @Query("SELECT DISTINCT c.tdp FROM CPU c")
    List<String> findDistinctTDPs();

    @Query("SELECT DISTINCT c.hyperthreading FROM CPU c")
    List<String> findDistinctSMTs();

    @Query("SELECT MIN(p.price) FROM Product p JOIN CPU c ON p.partModel.id=c.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN CPU c ON p.partModel.id=c.id")
    Double findMaxPrice();
}