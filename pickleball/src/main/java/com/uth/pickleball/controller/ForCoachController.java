package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForCoachController {

    @GetMapping("/forcoach")
    public String forCoach() {
        return "private/for_coach";
    }

}
