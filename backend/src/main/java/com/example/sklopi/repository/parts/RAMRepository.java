package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.model.parts.RAM;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RAMRepository extends JpaRepository<RAM, Long> {
    Optional<RAM> findByNameAndPart(String name, Part part);

    @Query("SELECT MIN(p.price) FROM Product p JOIN RAM r on p.partModel.id=r.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN RAM r on p.partModel.id=r.id")
    Double findMaxPrice();


    @Query("SELECT DISTINCT r.name FROM RAM r")
    List<String> findDistinctTypes();

    @Query("SELECT DISTINCT r.amount FROM RAM r")
    List<String> findDistinctAmounts();

    @Query("SELECT DISTINCT r.capacity FROM RAM r")
    List<String> findDistinctCapacities();

    @Query("SELECT DISTINCT r.frequency FROM RAM r")
    List<String> findDistinctSpeeds();
}