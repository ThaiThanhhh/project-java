package com.uth.pickleball.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uth.pickleball.model.User;
import com.uth.pickleball.service.UserService;
import org.springframework.ui.Model;
@Controller
public class LoginController {
    private final UserService userService;
    public LoginController(UserService _userService) {
        this.userService = _userService;
    }
   
    @RequestMapping({
        "/login"
    })
     public String showLoginForm(Model model) {
        model.addAttribute("user", new User());
        return "public/login";
    }
    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model) {
        User user = userService.findByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (user == null || !encoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
            model.addAttribute("user", new User());
            return "public/login";
        }
        // Đăng nhập thành công, chuyển hướng tới trang chủ hoặc dashboard
        return "redirect:/home";
    }

}
