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
@AllArgsConstructor
@NoArgsConstructor
public class GPU extends PartModel {
    private String brand; // NVIDIA / AMD / Intel
    private int memorySize; // in GB
    private String memoryType;
    private int coreClock; // in MHz
    private int boostClock; // in MHz
    private int tdp; // in Watts
    private int performanceScore;

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public GPU(String name, Part part, String brand, int memorySize, String memoryType, int coreClock, int boostClock, int tdp, int performanceScore) {
        super(name, part);
        this.brand = brand;
        this.memorySize = memorySize;
        this.memoryType = memoryType;
        this.coreClock = coreClock;
        this.boostClock = boostClock;
        this.tdp = tdp;
        this.performanceScore = performanceScore;
    }
}
