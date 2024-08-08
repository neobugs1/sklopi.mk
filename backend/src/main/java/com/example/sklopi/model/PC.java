package com.example.sklopi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PC {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "cpu_id")
    private Product cpu;

    @ManyToOne
    @JoinColumn(name = "cpucooler_id")
    private Product cpucooler;

    @ManyToOne
    @JoinColumn(name = "gpu_id")
    private Product gpu;

    @ManyToOne
    @JoinColumn(name = "motherboard_id")
    private Product motherboard;

    @ManyToMany
    @JoinTable(
            name = "pc_ram",
            joinColumns = @JoinColumn(name = "pc_id"),
            inverseJoinColumns = @JoinColumn(name = "ram_id")
    )
    private List<Product> ram = new ArrayList<>();

    @ManyToMany
    @JoinTable(
            name = "pc_storage",
            joinColumns = @JoinColumn(name = "pc_id"),
            inverseJoinColumns = @JoinColumn(name = "storage_id")
    )
    private List<Product> storage = new ArrayList<>();

    @ManyToOne
    @JoinColumn(name = "psu_id")
    private Product psu;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Product pcCase;

    // Optionally add validation to ensure max 4 RAM and 4 Storage products
    public void addRam(Product ramProduct) {
        if (this.ram.size() < 4) {
            this.ram.add(ramProduct);
        } else {
            throw new IllegalStateException("Maximum of 4 RAM sticks allowed.");
        }
    }

    public void addStorage(Product storageProduct) {
        if (this.storage.size() < 4) {
            this.storage.add(storageProduct);
        } else {
            throw new IllegalStateException("Maximum of 4 Storage devices allowed.");
        }
    }
}
