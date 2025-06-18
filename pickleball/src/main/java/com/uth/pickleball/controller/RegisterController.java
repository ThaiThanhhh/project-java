package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.uth.pickleball.service.UserService;
import org.springframework.ui.Model;
import com.uth.pickleball.model.User;

@Controller
public class RegisterController {
    private final UserService userService;
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model) {
        model.addAttribute("user", new User());
        return "register";
    }
    @PostMapping("/register")
    public String register(
            @RequestParam("fullname") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm-password") String confirmPassword,
            @RequestParam("role") String role,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            model.addAttribute("user", new User(fullName, email, "", null, null, role));
            return "register";
        }
            // Kiểm tra email đã tồn tại
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already exists!");
            model.addAttribute("user", new User(fullName, "", "", null, null, role));
            return "register";
        }
        // Hash password trước khi lưu
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        User user = new User(fullName, email, hashedPassword, null, null, role);
        userService.addUser(user);
        return "redirect:/login";
    }}
