package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class ContactUsController {

    @GetMapping("/contact")
    public String showContactForm() {
        return "public/contact_us";
    }

}
