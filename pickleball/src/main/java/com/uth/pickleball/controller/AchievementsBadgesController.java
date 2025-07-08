package com.uth.pickleball.controller;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class AchievementsBadgesController {
    @GetMapping("/achievementsbadges")
    public String AchievementsBadges(HttpSession session) {
        if (!"student".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        return "private/features/achievements_badges"; 
    }
}