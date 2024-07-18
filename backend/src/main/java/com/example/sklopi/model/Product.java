package com.example.sklopi.model;

import jakarta.persistence.*;

@Entity
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String website;
    private double price;
    @ManyToOne
    @JoinColumn(name = "model_id")
    private PartModel model;
    // Other fields...
}