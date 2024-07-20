package com.example.sklopi.model.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import com.example.sklopi.model.Product;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CPU extends PartModel {
    private String brand;
    private String socket;
    private int coreClock; // in MHz
    private int boostClock; // in MHz
    private boolean integratedGraphics;
    private boolean includesCooler;

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public CPU(String name, Part part, String brand, String socket, int coreClock, int boostClock, boolean integratedGraphics, boolean includesCooler) {
        super(name, part);
        this.brand = brand;
        this.socket = socket;
        this.coreClock = coreClock;
        this.boostClock = boostClock;
        this.integratedGraphics = integratedGraphics;
        this.includesCooler = includesCooler;
    }
}

