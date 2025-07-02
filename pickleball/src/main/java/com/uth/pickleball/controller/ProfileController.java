package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ProfileController {
    @GetMapping("/profile_student")
    public String profileStudent() {
        return "public/profile/student";
    }

    @GetMapping("/profile_coach")
    public String profileCoach() {
        return "public/profile/coach";
    }
} 