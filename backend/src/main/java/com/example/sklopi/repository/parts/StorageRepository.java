package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.RAM;
import com.example.sklopi.model.parts.Storage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StorageRepository extends JpaRepository<Storage, Long> {
    Optional<Storage> findByNameAndPart(String name, Part part);

    @Query("SELECT MIN(p.price) FROM Product p JOIN Storage s on p.partModel.id=s.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN Storage s on p.partModel.id=s.id")
    Double findMaxPrice();

    @Query("SELECT DISTINCT s.name FROM Storage s")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT s.formFactor FROM Storage s")
    List<String> findDistinctFormFactor();

    @Query("SELECT DISTINCT s.capacity FROM Storage s")
    List<String> findDistinctCapacities();

    @Query("SELECT DISTINCT s.type FROM Storage s")
    List<String> findDistinctTypes();
}
