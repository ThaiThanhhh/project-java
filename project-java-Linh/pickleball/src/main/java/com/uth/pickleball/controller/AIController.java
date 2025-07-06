package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AIController {
    @GetMapping("/ai")
    public String showAIPage() {
        return "private/features/ai";
    }

}
