package com.example.sklopi.service.parts;

import com.example.sklopi.model.parts.PSU;
import com.example.sklopi.repository.parts.PSURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PSUService {
    @Autowired
    private PSURepository psuRepository;

    public Optional<PSU> findByBrandAndEfficiencyRatingAndWattage(String name, String efficiencyRating, int wattage) {
        return psuRepository.findByNameAndEfficiencyRatingAndWattage(name, efficiencyRating, wattage);
    }

    public PSU save(PSU psu) {
        return psuRepository.save(psu);
    }
}
