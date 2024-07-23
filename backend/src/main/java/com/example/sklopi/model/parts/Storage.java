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
public class Storage extends PartModel {
    private String formFactor;
    private int capacity;
    private String type; // Added type field to differentiate between SSDs and HDDs

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public Storage(String name,  Part part, String formFactor, int capacity, String type) {
        super(name, part);
        this.formFactor = formFactor;
        this.capacity = capacity;
        this.type = type;
    }
}