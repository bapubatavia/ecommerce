package com.batavia.ecommerce.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.batavia.ecommerce.DTOs.RegistrationRequest;
import com.batavia.ecommerce.services.UserServiceInterface;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthAPIcontroller {
    @Autowired
    private UserServiceInterface userService;


    @PostMapping("/register-user")
    public ResponseEntity<String> registerUser(@ModelAttribute("user") RegistrationRequest regRequest, RedirectAttributes redirectAttributes){
        try {
            userService.registerUser(regRequest);
            redirectAttributes.addFlashAttribute("Message", "Registration successful! Please log in to continue.");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/login?success").build();         
        } catch (Exception e) {
            e.printStackTrace();
            redirectAttributes.addAttribute("errorMessage", "Registration failed. Please try again.");
            return ResponseEntity.status(HttpStatus.FOUND).header("Location", "/registration?fail").build();      
        }


    }
}
