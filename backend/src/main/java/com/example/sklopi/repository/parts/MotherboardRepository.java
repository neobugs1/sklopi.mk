package com.example.sklopi.repository.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.repository.custom.MotherboardRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MotherboardRepository extends JpaRepository<Motherboard, Long>, MotherboardRepositoryCustom {
    Optional<Motherboard> findByNameAndPart(String name, Part part);


    @Query("SELECT DISTINCT m.formFactor FROM Motherboard m")
    List<String> findDistinctFormFactors();

    @Query("SELECT DISTINCT m.name FROM Motherboard m")
    List<String> findDistinctNames();

    @Query("SELECT DISTINCT m.supportedMemory FROM Motherboard m")
    List<String> findDistinctSupportedMemory();

    @Query("SELECT DISTINCT m.socket FROM Motherboard m")
    List<String> findDistinctSockets();
}
