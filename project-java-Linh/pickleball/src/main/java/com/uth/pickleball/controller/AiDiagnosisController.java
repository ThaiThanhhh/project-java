package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/ai")
public class AiDiagnosisController {

    @GetMapping("/diagnosis")
    public String redirectToFlask() {
        // Redirect sang giao diện Flask (ví dụ: http://127.0.0.1:5000/)
        return "redirect:http://127.0.0.1:5000/";
    }
}