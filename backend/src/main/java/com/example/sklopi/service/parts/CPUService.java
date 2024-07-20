package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.CPU;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.parts.CPURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CPUService {

    @Autowired
    private CPURepository cpuRepository;

    public Optional<CPU> findByNameAndPart(String name, Part part) {
        return cpuRepository.findByNameAndPart(name, part);
    }

    public CPU save(CPU cpu) {
        return cpuRepository.save(cpu);
    }

    public List<CPU> findAll() {
        return cpuRepository.findAll();
    }
}
