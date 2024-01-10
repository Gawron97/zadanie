package com.example.zadanie.service;

import com.example.zadanie.dto.ProductDTO;
import com.example.zadanie.entity.Category;
import com.example.zadanie.entity.Product;
import com.example.zadanie.repository.CategoryRepository;
import com.example.zadanie.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    public void addProduct(ProductDTO product) {
        Product newProduct = Product.of(product);
        Category category = getCategoryById(product.getCategoryId());
        newProduct.setCategory(category);
        productRepository.save(newProduct);
    }

    private Category getCategoryById(String id) {
        return categoryRepository.findById(id).orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public Product getProduct(Long id) {
        return productRepository.findById(id).get();
    }

    public void editProduct(Long id, ProductDTO product) {
        if(productRepository.findById(id).isEmpty()) {
            throw new RuntimeException();
        }
        Product productToUpdate = productRepository.findById(id).get();
        Category category = getCategoryById(product.getCategoryId());
        productToUpdate.setName(product.getName());
        productToUpdate.setWeight(product.getWeight());
        productToUpdate.setPrice(product.getPrice());
        productToUpdate.setCategory(category);
        productRepository.save(productToUpdate);
    }

    public void deleteProduct(Long id) {
        productRepository.deleteById(id);
    }
}
