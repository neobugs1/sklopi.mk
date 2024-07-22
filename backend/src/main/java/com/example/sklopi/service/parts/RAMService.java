package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.model.parts.RAM;
import com.example.sklopi.repository.parts.RAMRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RAMService {
    @Autowired
    private RAMRepository ramRepository;

    public Optional<RAM> findByNameAndPart(String name, Part part) {
        return ramRepository.findByNameAndPart(name, part);
    }

    public RAM save(RAM ram) {
        return ramRepository.save(ram);
    }
    public List<RAM> findAll() {
        return ramRepository.findAll();
    }
}
