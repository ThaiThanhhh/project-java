package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
@Controller

public class MyLearningController {

    @GetMapping("/mylearning")
    public String myLearning() {
        return "private/my_learning/my_learning";
    }
    @GetMapping("/mylearning/learn")
    public String learn() {
        return "private/my_learning/learn";
    }

}
// This controller handles the "My Learning" section of the application.