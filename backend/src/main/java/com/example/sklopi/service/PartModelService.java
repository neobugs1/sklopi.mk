package com.example.sklopi.service;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.repository.PartModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PartModelService {
    @Autowired
    private PartModelRepository partModelRepository;

    public List<PartModel> getAllPartModels() {
        return partModelRepository.findAll();
    }

    public PartModel getPartModelById(Long id) {
        return partModelRepository.findById(id).orElse(null);
    }

    public PartModel savePartModel(PartModel partModel) {
        return partModelRepository.save(partModel);
    }

    public void deletePartModel(Long id) {
        partModelRepository.deleteById(id);
    }
}
