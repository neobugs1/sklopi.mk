package com.example.sklopi.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
    @JoinColumn(name = "gpu_id")
    private Product gpu;

    @ManyToOne
    @JoinColumn(name = "motherboard_id")
    private Product motherboard;

    @ManyToOne
    @JoinColumn(name = "ram_id")
    private Product ram;

    @ManyToOne
    @JoinColumn(name = "storage_id")
    private Product storage;

    @ManyToOne
    @JoinColumn(name = "case_id")
    private Product pcCase;

}
