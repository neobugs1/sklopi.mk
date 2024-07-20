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
public class Case extends PartModel {
    private String formFactor; // ATX, MicroATX, etc.

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

}
