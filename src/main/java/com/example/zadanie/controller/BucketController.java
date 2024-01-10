package com.example.zadanie.controller;

import com.example.zadanie.service.BucketService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequestMapping("/bucket")
@RequiredArgsConstructor
public class BucketController {

    private final BucketService bucketService;

    @PostMapping("/add/{id}")
    public String addProductToBucket(@PathVariable Long id, HttpSession session, Principal principal) {

        String bucketJson = (String) session.getAttribute("bucket_" + principal.getName());
        String bucketToSession = bucketService.addProductToBucket(bucketJson, id);
        session.setAttribute("bucket_" + principal.getName(), bucketToSession);

        return "redirect:/products/all";

    }

    @PostMapping("update/{id}")
    public String updateProductInBucket(@PathVariable Long id, HttpSession session, Principal principal) {

        String bucketJson = (String) session.getAttribute("bucket_" + principal.getName());
        String bucketToSession = bucketService.addProductToBucket(bucketJson, id);
        session.setAttribute("bucket_" + principal.getName(), bucketToSession);

        return "redirect:/bucket";

    }

    @PostMapping("remove/{id}")
    public String deleteProductInBucket(@PathVariable Long id, HttpSession session, Principal principal) {

        String bucketJson = (String) session.getAttribute("bucket_" + principal.getName());
        String bucketToSession = bucketService.deleteProductFromBucket(bucketJson, id);
        session.setAttribute("bucket_" + principal.getName(), bucketToSession);

        return "redirect:/bucket";

    }

    @GetMapping
    public String getBucket(Model model, HttpSession session, Principal principal) {
        String bucketJson = (String) session.getAttribute("bucket_" + principal.getName());
        model.addAttribute("bucket", bucketService.getBucket(bucketJson));
        return "bucket";
    }

}
