package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class StructuredController {
    @GetMapping("/structured")
    public String structured() {
        return "private/features/structured";
    }
}
