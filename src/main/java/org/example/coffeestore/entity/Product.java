package org.example.coffeestore.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String category; // Coffee, Drink, Dessert
    private String size; // S, M, L
    private BigDecimal price;
    private BigDecimal cost;
    private String description;
}