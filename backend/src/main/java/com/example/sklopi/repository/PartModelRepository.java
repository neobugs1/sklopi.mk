package com.example.sklopi.repository;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PartModelRepository extends JpaRepository<PartModel, Long> {
    Optional<PartModel> findByNameAndPart(String name, Part part);
    List<PartModel> findByPart(Part part);
}
