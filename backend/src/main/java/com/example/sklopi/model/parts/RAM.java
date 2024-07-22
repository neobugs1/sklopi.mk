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
public class RAM extends PartModel {
    private int amount; // broj na sticks
    private int size; // in GB
    private int frequency; // in MHz

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public RAM(String name, Part part) {
        super(name, part);
    }
    public RAM(String name, Part part, int amount, int size, int frequency) {
        super(name, part);
        this.amount = amount;
        this.size = size;
        this.frequency = frequency;
    }
}