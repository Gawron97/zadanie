package com.example.zadanie.controller;

import com.example.zadanie.entity.Category;
import com.example.zadanie.service.CategoryService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    @GetMapping("/all")
    public String getAllCategories(Model model) {
        model.addAttribute("categories", categoryService.getCategories());
        return "categories_panel";
    }

    @GetMapping("/add")
    public String getAddCategoryForm(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("newCategory", new Category());
        model.addAttribute("error", error);
        return "add_category";
    }

    @PostMapping("/add")
    public String addCategory(@ModelAttribute("newCategory") @Valid Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "add_category";
        }
        try {
            categoryService.addCategory(category);
        } catch (RuntimeException e) {
            return "redirect:/categories/add?error=Wystapil blad podczas dodawania kategorii";
        }
        return "redirect:/categories/all";
    }

    @GetMapping("/details/{id}")
    public String getCategoryDetails(Model model, @PathVariable String id) {
        model.addAttribute("category", categoryService.getCategory(id));
        return "detail_category";
    }

    @GetMapping("/edit/{id}")
    public String getProductEdit(Model model, @PathVariable String id, @RequestParam(required = false) String error) {
        model.addAttribute("category", categoryService.getCategory(id));
        model.addAttribute("error", error);
        return "edit_category";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable String id, @ModelAttribute("category") @Valid Category category, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "edit_category";
        }
        try {
            categoryService.updateCategory(id, category);
        } catch (RuntimeException e) {
            return "redirect:/categories/edit/{id}?error=Wystapil blad podczas edycji kategorii";
        }
        return "redirect:/categories/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable String id) {
        categoryService.deleteCategory(id);
        return "redirect:/categories/all";
    }

}
