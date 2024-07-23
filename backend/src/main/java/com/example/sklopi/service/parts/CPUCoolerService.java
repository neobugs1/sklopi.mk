package com.example.sklopi.service.parts;

import com.example.sklopi.model.parts.CPUCooler;
import com.example.sklopi.repository.parts.CPUCoolerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CPUCoolerService {

    @Autowired
    private CPUCoolerRepository cpuCoolerRepository;

    public CPUCooler save(CPUCooler cpuCooler) {
        return cpuCoolerRepository.save(cpuCooler);
    }

    public List<CPUCooler> findAll() {
        return cpuCoolerRepository.findAll();
    }

    public Optional<CPUCooler> findByNameAndCoolerType(String name, String coolerType) {
        return cpuCoolerRepository.findByNameAndCoolerType(name, coolerType);
    }

    public CPUCooler findById(Long id) {
        return cpuCoolerRepository.findById(id).orElse(null);
    }

    public void deleteById(Long id) {
        cpuCoolerRepository.deleteById(id);
    }
}