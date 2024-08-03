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
    private int baseClock;
    private int boostClock;
    private int totalCores;
    private String coreConfiguration; // to support strings like "8P + 16E"
    private int singleCorePoints;
    private int multiCorePoints;
    private boolean hyperthreading;
    private String generation;
    private int tdp;

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public CPU(String name, Part part, String brand, String socket, int baseClock, int boostClock,
               int totalCores, String coreConfiguration, int singleCorePoints, int multiCorePoints, boolean hyperthreading, String generation, int tdp) {
        super(name, part);
        this.brand = brand;
        this.socket = socket;
        this.baseClock = baseClock;
        this.boostClock = boostClock;
        this.totalCores = totalCores;
        this.coreConfiguration = coreConfiguration;
        this.singleCorePoints = singleCorePoints;
        this.multiCorePoints = multiCorePoints;
        this.hyperthreading = hyperthreading;
        this.generation = generation;
        this.tdp = tdp;
    }
}

