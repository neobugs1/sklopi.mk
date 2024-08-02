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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PCCase extends PartModel {
    private String formFactor; // ATX, MicroATX, etc.

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;

    public PCCase(Part part, String name, String formFactor) {
        super(name, part);
        this.formFactor = formFactor;
    }
}