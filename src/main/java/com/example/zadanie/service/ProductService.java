package com.example.zadanie.service;

import com.example.zadanie.entity.Product;
import com.example.zadanie.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(Product product) {
        productRepository.save(product);
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    public void editProduct(Long id, Product product) {
        if(productRepository.findById(id).isEmpty()) {
            throw new RuntimeException();
        }
        Product productToUpdate = productRepository.findById(id).get();
        productToUpdate.setName(product.getName());
        productToUpdate.setWeight(product.getWeight());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCategory(product.getCategory());
        productRepository.save(productToUpdate);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
