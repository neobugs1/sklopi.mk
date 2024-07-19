package com.example.sklopi.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;
    private String productUrl;
    private int price;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @ManyToOne
    @JoinColumn(name = "part_model_id")
    private PartModel partModel;

    private LocalDateTime lastUpdated;

    @PrePersist
    @PreUpdate
    public void setLastUpdated() {
        this.lastUpdated = LocalDateTime.now();
    }
}