package com.example.sklopi.service.parts;

import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.repository.parts.GPURepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GPUService {
    @Autowired
    private GPURepository gpuRepository;

    public List<GPU> getAllGPUs() {
        return gpuRepository.findAll();
    }

    public GPU getGPUById(Long id) {
        return gpuRepository.findById(id).orElse(null);
    }

    public GPU saveGPU(GPU gpu) {
        return gpuRepository.save(gpu);
    }

    public void deleteGPU(Long id) {
        gpuRepository.deleteById(id);
    }
}

