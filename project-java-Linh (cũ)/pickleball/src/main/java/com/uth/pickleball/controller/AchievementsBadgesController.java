package com.uth.pickleball.controller;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller
public class AchievementsBadgesController {
    @GetMapping("/achievementsbadges")
    public String AchievementsBadges() {
        return "private/features/achievements_badges"; 
    }

}
