package com.example.sklopi.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String imageUrl;

    @Column(length = 1024)
    private String productUrl; //potrebno e pogolema dolzina bidejki setec koristi kirilica vo URI

    private int price;
    private LocalDate lastUpdated;
    private boolean inStock;

    @ManyToOne
    @JoinColumn(name = "part_id")
    private Part part;

    @ManyToOne
    @JoinColumn(name = "part_model_id")
    @JsonIgnoreProperties({"products"})
    private PartModel partModel;

    @PrePersist
    @PreUpdate
    public void setLastUpdated() {
        this.lastUpdated = LocalDate.now();
    }

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<PriceHistory> priceHistory = new ArrayList<>(); // Initialize here

    public void addPriceHistory(int price) {
        if (priceHistory == null) {
            priceHistory = new ArrayList<>();
        }
        PriceHistory history = new PriceHistory();
        history.setPrice(price);
        history.setDate(LocalDate.now());
        history.setProduct(this);
        priceHistory.add(history);

        System.out.println("Adding price history: " + price + " on " + LocalDate.now());
    }
}
