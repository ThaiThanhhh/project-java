package com.uth.pickleball.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForCoachController {

    @GetMapping("/forcoach")
    public String forCoach(HttpSession session) {
        if (!"coach".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "private/for_coach/for_coach";
    }

    @GetMapping("/forcoach/teach")
    public String teach(HttpSession session) {
        if (!"coach".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "private/for_coach/teach";
    }

    @GetMapping("/forcoach/viewfinancial")
    public String viewFinancial(HttpSession session) {
        if (!"coach".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "private/for_coach/view_financial";
    }
}