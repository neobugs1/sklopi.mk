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
public class GPU extends PartModel {
    private int memorySize; // in MB
    private String memoryType;
    private int coreClock; // in MHz
    private int boostClock; // in MHz
    private int tdp; // in Watts

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

}
