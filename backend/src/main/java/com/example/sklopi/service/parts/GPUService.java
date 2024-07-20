package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.parts.GPURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class GPUService {

    @Autowired
    private GPURepository gpuRepository;

    public Optional<GPU> findByNameAndPart(String name, Part part) {
        return gpuRepository.findByNameAndPart(name, part);
    }

    public GPU save(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public List<GPU> findAll() {
        return gpuRepository.findAll();
    }
}


