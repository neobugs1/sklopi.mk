package com.example.sklopi.service.parts;

import com.example.sklopi.model.parts.PcCase;
import com.example.sklopi.repository.parts.PcCaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PcCaseService {
    @Autowired
    private PcCaseRepository pcCaseRepository;

    public Optional<PcCase> findByBrandAndFormFactor(String brand, String formFactor) {
        return pcCaseRepository.findByNameAndFormFactor(brand, formFactor); //brand == name vo PartModel (natklasata)
    }

    public PcCase save(PcCase psu) {
        return pcCaseRepository.save(psu);
    }
}
