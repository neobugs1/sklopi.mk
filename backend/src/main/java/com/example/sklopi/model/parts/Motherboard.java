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
public class Motherboard extends PartModel {
    private String socket;
    private String chipset;
    private String formFactor; // ATX, MicroATX, etc.
    private String supportedMemory; // DDR4, DDR5, etc.

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

}