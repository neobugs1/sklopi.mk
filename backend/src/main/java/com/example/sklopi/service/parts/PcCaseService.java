package com.example.sklopi.service.parts;

import com.example.sklopi.model.parts.PCCase;
import com.example.sklopi.repository.parts.PcCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PcCaseService {
    @Autowired
    private PcCaseRepository pcCaseRepository;

    public Optional<PCCase> findByBrandAndFormFactor(String brand, String formFactor) {
        return pcCaseRepository.findByNameAndFormFactor(brand, formFactor); //brand == name vo PartModel (natklasata)
    }

    public PCCase save(PCCase psu) {
        return pcCaseRepository.save(psu);
    }
}
