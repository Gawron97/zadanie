package com.example.zadanie.entity;

import com.example.zadanie.dto.ProductDTO;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @NotBlank
    private String name;
    @Max(value = 100)
    private double weight;
    @Max(value = 10000)
    private double price;
    @ManyToOne(fetch = FetchType.EAGER)
    private Category category;

    public static Product of(ProductDTO product) {
        return Product.builder()
                .name(product.getName())
                .weight(product.getWeight())
                .price(product.getPrice())
                .build();
    }
}
