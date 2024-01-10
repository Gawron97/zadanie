package com.example.zadanie.dto;

import com.example.zadanie.entity.Bucket;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BucketDTO {

    private Map<ProductDTO, Integer> products = new HashMap<>();
    private double price;

    public static BucketDTO of(Map<ProductDTO, Integer> products, double price) {
        return BucketDTO.builder()
                .products(products)
                .price(price)
                .build();
    }

}
