package com.example.sklopi.model.parts;

import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
public class RAM extends PartModel {
    private String type; // DDR4, DDR5, etc.
    private int size; // in GB

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;
}