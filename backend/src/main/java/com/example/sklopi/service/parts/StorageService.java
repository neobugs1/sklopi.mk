package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.parts.RAM;
import com.example.sklopi.model.parts.Storage;
import com.example.sklopi.repository.parts.RAMRepository;
import com.example.sklopi.repository.parts.StorageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StorageService {
    @Autowired
    private StorageRepository storageRepository;

    public Optional<Storage> findByNameAndPart(String name, Part part) {
        return storageRepository.findByNameAndPart(name, part);
    }

    public Storage save(Storage storage) {
        return storageRepository.save(storage);
    }
    public List<Storage> findAll() {
        return storageRepository.findAll();
    }
}
