package com.example.sklopi.service.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.Product;
import com.example.sklopi.model.parts.GPU;
import com.example.sklopi.model.parts.Motherboard;
import com.example.sklopi.repository.parts.MotherboardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MotherboardService {

    @Autowired
    private MotherboardRepository motherboardRepository;

    public Optional<Motherboard> findByNameAndPart(String name, Part part) {
        return motherboardRepository.findByNameAndPart(name, part);
    }

    public void save(Motherboard motherboard) {
        motherboardRepository.save(motherboard);
    }

    public List<Motherboard> findAll() {
        return motherboardRepository.findAll();
    }

    public PaginationResponse<Product> getProductsByPartModelType(Class<?> partModelType, int page) {
        int pageSize = 10; // Number of items per page
        Pageable pageable = PageRequest.of(page - 1, pageSize);
        Page<Product> productPage = motherboardRepository.findByPartModelType(partModelType, pageable);

        List<Product> products = productPage.getContent();
        int totalPages = productPage.getTotalPages();

        return new PaginationResponse<>(products, totalPages);
    }
}

