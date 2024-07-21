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
public class Motherboard extends PartModel {
    private String socket;
    private String formFactor; // ATX, MicroATX, etc.
    private String supportedMemory; // DDR4, DDR5, etc.

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public Motherboard(String name, Part part, String socket, String formFactor, String supportedMemory) {
        super(name, part);
        this.socket = socket;
        this.formFactor = formFactor;
        this.supportedMemory = supportedMemory;
    }
}