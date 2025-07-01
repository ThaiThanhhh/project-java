package com.uth.pickleball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import com.uth.pickleball.repositories.IUserRepository;
import jakarta.servlet.http.HttpSession;

import com.uth.pickleball.model.User;
@Controller
public class ProfileController {
    @Autowired
    private IUserRepository userRepository;

    @GetMapping("/profile")
    public String showProfile(HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        String role = user.getRole();
        if ("student".equalsIgnoreCase(role)) {
            return "public/profile/student";
        } else if ("coach".equalsIgnoreCase(role)) {
            return "public/profile/coach";
        } else {
            return "redirect:/home";
        }
    }

}
