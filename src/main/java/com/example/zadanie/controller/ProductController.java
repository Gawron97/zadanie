package com.example.zadanie.controller;

import com.example.zadanie.dto.ProductDTO;
import com.example.zadanie.entity.Product;
import com.example.zadanie.service.CategoryService;
import com.example.zadanie.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/products")
@RequiredArgsConstructor
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    @GetMapping("/all")
    public String showProducts(Model model) {
        model.addAttribute("products", productService.getProducts());
        return "products_panel";
    }

    @GetMapping("/add")
    public String getAddProductForm(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("newProduct", new ProductDTO());
        model.addAttribute("error", error);
        model.addAttribute("categories", categoryService.getCategories());
        return "add_product";
    }

    @PostMapping("/add")
    public String addProduct(@ModelAttribute("newProduct") @Valid ProductDTO product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "add_product";
        }
        try {
            productService.addProduct(product);
        } catch (RuntimeException e) {
            return "redirect:/products/add?error=Wystapil blad podczas dodawania produktu, sprobuj ponownie";
        }
        return "redirect:/products/all";
    }

    @GetMapping("/details/{id}")
    public String getProductDetails(Model model, @PathVariable Long id) {
        model.addAttribute("product", productService.getProduct(id));
        return "detail_product";
    }

    @GetMapping("/edit/{id}")
    public String getProductEdit(Model model, @PathVariable Long id, @RequestParam(required = false) String error) {
        model.addAttribute("product", ProductDTO.of(productService.getProduct(id)));
        model.addAttribute("error", error);
        model.addAttribute("categories", categoryService.getCategories());
        return "edit_product";
    }

    @PostMapping("/edit/{id}")
    public String editProduct(@PathVariable Long id, @ModelAttribute("product") @Valid ProductDTO product, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "edit_product";
        }
        try {
            productService.editProduct(id, product);
        } catch (RuntimeException e) {
            return "redirect:/products/edit/{id}?error=Wystapil blad podczas edycji produktu";
        }
        return "redirect:/products/all";
    }

    @PostMapping("/delete/{id}")
    public String deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return "redirect:/products/all";
    }

}

