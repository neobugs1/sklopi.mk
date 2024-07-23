package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.PSU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PSURepository extends JpaRepository<PSU, Long> {
    Optional<PSU> findByNameAndEfficiencyRatingAndWattage(String name, String efficiencyRating, int wattage);
}
