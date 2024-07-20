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
public class Storage extends PartModel {
    private String type; // HDD, SSD, M.2-SATA, M.2-NvME
    private int capacity; // in GB

    @OneToMany(mappedBy = "partModel")
    private List<Product> products;
}