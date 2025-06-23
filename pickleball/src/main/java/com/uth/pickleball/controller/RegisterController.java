package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import com.uth.pickleball.service.UserService;
import org.springframework.ui.Model;
import com.uth.pickleball.model.User;
import jakarta.servlet.http.HttpSession;


@Controller
public class RegisterController {
    private final UserService userService;
    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String showRegisterForm(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        if (session.getAttribute("role") != null) {
            return "redirect:/"; // hoặc redirect về trang chính
        }
        return "public/register";
    }
    @PostMapping("/register")
    public String register(
            @RequestParam("fullname") String fullName,
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            @RequestParam("confirm-password") String confirmPassword,
            Model model) {

        if (!password.equals(confirmPassword)) {
            model.addAttribute("error", "Passwords do not match!");
            model.addAttribute("user", new User(fullName, email, "", ""));
            return "public/register";
        }
            // Kiểm tra email đã tồn tại
        if (userService.existsByEmail(email)) {
            model.addAttribute("error", "Email already exists!");
            model.addAttribute("user", new User(fullName, "", "", ""));
            return "public/register";
        }
        // Hash password trước khi lưu
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String hashedPassword = encoder.encode(password);

        User user = new User(fullName, email, hashedPassword, "");
        userService.addUser(user);
        return "redirect:/login";
    }}
