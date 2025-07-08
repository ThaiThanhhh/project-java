package com.uth.pickleball.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class MyLearningController {

    @GetMapping("/mylearning")
    public String myLearning(HttpSession session) {
        if (!"student".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "private/my_learning/my_learning";
    }

    @GetMapping("/mylearning/learn")
    public String learn(HttpSession session) {
        if (!"student".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "private/my_learning/learn";
    }
}