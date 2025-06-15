package com.uth.pickleball.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

import com.uth.pickleball.model.User;
import com.uth.pickleball.service.UserService;
import org.springframework.ui.Model;
@Controller
public class LoginController {
    private final UserService userService;
    public LoginController(UserService _userService) {
        this.userService = _userService;
    }
    @ModelAttribute("users")
    public List<User> getAllUsers() {
        return userService.getAllUsers();
    }
    @RequestMapping({
        "/login"
    })
    public String login(Model model) {
        model.addAttribute("user", new User());
        return "login";
    }

}
