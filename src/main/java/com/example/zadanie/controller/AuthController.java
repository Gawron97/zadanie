package com.example.zadanie.controller;

import com.example.zadanie.dto.RegisterDTO;
import com.example.zadanie.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequiredArgsConstructor
public class AuthController {

    private final UserService userService;

//    @GetMapping("/login")
//    public String login(@RequestParam(required = false) String error, Model model) {
//        model.addAttribute("error", error);
//        return "login";
//    }

    @GetMapping("/register")
    public String getRegisterForm(Model model, @RequestParam(required = false) String error) {
        model.addAttribute("registrationData", new RegisterDTO());
        model.addAttribute("error", error);
        return "register";
    }

    @PostMapping("/register")
    public String registerUser(@ModelAttribute("registrationData") @Valid RegisterDTO register, BindingResult result) {
        if(result.hasErrors()) {
            return "register";
        }
        try {
            userService.registerUser(register);
        } catch (RuntimeException e) {
            return "redirect:/register?error=Wystapil blad podczas rejestracji";
        }
        return "menu";
    }

}
