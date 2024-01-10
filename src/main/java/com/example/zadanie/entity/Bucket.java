package com.example.zadanie.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashMap;
import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bucket {

    private Map<Long, Integer> products = new HashMap<>();
    private double price;

    public void addProduct(Product product) {
        products.put(product.getId(), products.getOrDefault(product.getId(), 0) + 1);
        price += product.getPrice();
    }

    public void deleteProduct(Product product) {
        if(products.get(product.getId()) > 1) {
            products.put(product.getId(), products.get(product.getId()) - 1);
        } else {
            products.remove(product.getId());
        }
        price -= product.getPrice();
    }

}
