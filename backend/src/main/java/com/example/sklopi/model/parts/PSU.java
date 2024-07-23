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
public class PSU extends PartModel {
    private String efficiencyRating;
    private int wattage;

    public PSU(Part part, String brand, String efficiencyRating, int wattage) {
        super(brand, part);
        this.efficiencyRating = efficiencyRating;
        this.wattage = wattage;
    }
}