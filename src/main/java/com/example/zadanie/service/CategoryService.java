package com.example.zadanie.service;

import com.example.zadanie.entity.Category;
import com.example.zadanie.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public List<Category> getCategories() {
        return categoryRepository.findAll();
    }

    public Category getCategory(String id) {
        return categoryRepository.findById(id).get();
    }

    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void updateCategory(String id, Category category) {
        Category categoryToUpdate = categoryRepository.findById(id).orElseThrow(RuntimeException::new);
        categoryToUpdate.setName(category.getName());
        categoryRepository.save(categoryToUpdate);
    }

    public void deleteCategory(String id) {
        categoryRepository.deleteById(id);
    }

}
