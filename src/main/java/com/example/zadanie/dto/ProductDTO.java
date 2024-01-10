package com.example.zadanie.dto;

import com.example.zadanie.entity.Product;
import jakarta.annotation.Nonnull;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductDTO {

    private Long id;
    @NotBlank
    private String name;
    @Max(value = 100)
    private double weight;
    @Max(value = 10000)
    private double price;
    @Nonnull
    private String categoryId;
    private String categoryName;

    public static ProductDTO of(Product product) {
        return ProductDTO.builder()
                .id(product.getId())
                .name(product.getName())
                .weight(product.getWeight())
                .price(product.getPrice())
                .categoryId(product.getCategory().getId())
                .categoryName(product.getCategory().getName())
                .build();
    }

}
