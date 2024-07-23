package com.example.sklopi.model.parts;

import com.example.sklopi.model.Part;
import com.example.sklopi.model.PartModel;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class CPUCooler extends PartModel {
    private String coolerType;

    public CPUCooler(String name, Part part, String coolerType) {
        super(name, part);
        this.coolerType = coolerType;
    }
}