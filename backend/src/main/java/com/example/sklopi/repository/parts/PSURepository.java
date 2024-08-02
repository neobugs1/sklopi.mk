package com.example.sklopi.repository.parts;

import com.example.sklopi.model.parts.PSU;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PSURepository extends JpaRepository<PSU, Long> {
    Optional<PSU> findByNameAndEfficiencyRatingAndWattage(String name, String efficiencyRating, int wattage);

    @Query("SELECT MIN(p.price) FROM Product p JOIN PSU psu on p.partModel.id = psu.id")
    Double findMinPrice();

    @Query("SELECT MAX(p.price) FROM Product p JOIN PSU psu on p.partModel.id = psu.id")
    Double findMaxPrice();

    @Query("SELECT DISTINCT psu.name FROM PSU psu")
    List<String> findDistinctBrands();

    @Query("SELECT DISTINCT psu.wattage FROM PSU psu")
    List<String> findDistinctWattage();

    @Query("SELECT DISTINCT psu.efficiencyRating FROM PSU psu")
    List<String> findDistinctEfficiencyRatings();
}
