package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller

public class ProcessController {
    @GetMapping("/process")
    public String showProcessPage() {
        return "private/features/process";
    }

}
