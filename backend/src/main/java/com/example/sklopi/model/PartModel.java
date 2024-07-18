package com.example.sklopi.model;

import jakarta.persistence.*;

@Entity
public class PartModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;
    // Other fields...
}