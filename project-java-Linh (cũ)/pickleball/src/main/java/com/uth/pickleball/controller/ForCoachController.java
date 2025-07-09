package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ForCoachController {

    @GetMapping("/forcoach")
    public String forCoach() {
        return "private/for_coach/for_coach";
    }
    @GetMapping("/forcoach/addcertifications")
    public String addCertifications() {
        return "private/for_coach/add_certifications";
    }
    @GetMapping("/forcoach/teach")
    public String teach() {
        return "private/for_coach/teach";
    }
    @GetMapping("/forcoach/viewfinancial")
    public String viewFinancial() {
        return "private/for_coach/view_financial";
    }
    @GetMapping("/forcoach/edit")
    public String edit() {
        return "private/for_coach/edit";
    }

}
