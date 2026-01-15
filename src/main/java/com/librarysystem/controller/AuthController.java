package com.librarysystem.controller;

import com.librarysystem.model.User;
import com.librarysystem.service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/auth")
public class AuthController {
    
    private final UserService userService;
    
    @Autowired
    public AuthController(UserService userService) {
        this.userService = userService;
    }
    
    @GetMapping("/signup")
    public String showSignUpForm(Model model) {
        model.addAttribute("user", new User());
        return "auth/signup";
    }
    
    @PostMapping("/signup")
    public String processSignUp(@Valid User user, BindingResult bindingResult, 
                               RedirectAttributes redirectAttributes) {
        
        if (bindingResult.hasErrors()) {
            return "auth/signup";
        }
        
        if (userService.existsByEmail(user.getEmail())) {
            bindingResult.rejectValue("email", "error.user", "Email already exists!");
            return "auth/signup";
        }
        
        try {
            userService.saveUser(user);
            redirectAttributes.addFlashAttribute("successMessage", 
                "Registration successful! You can now login.");
            return "redirect:/login";
        } catch (Exception e) {
            bindingResult.rejectValue("email", "error.user", 
                "Registration failed. Please try again.");
            return "auth/signup";
        }
    }
    
    @GetMapping("/login")
    public String showLoginForm() {
        return "login";
    }
}
