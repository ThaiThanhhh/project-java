package com.uth.pickleball.controller;


import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uth.pickleball.model.User;
import com.uth.pickleball.service.UserService;
import org.springframework.ui.Model;
import jakarta.servlet.http.HttpSession;

@Controller
public class LoginController {
    private final UserService userService;
    public LoginController(UserService _userService) {
        this.userService = _userService;
    }
   
    @GetMapping("/login")
     public String showLoginForm(Model model, HttpSession session) {
        model.addAttribute("user", new User());
        if (session.getAttribute("role") != null) {
        return "redirect:/"; // hoặc redirect về trang chính
    }
        return "public/login";
    }
   
    @PostMapping("/login")
    public String login(
            @RequestParam("email") String email,
            @RequestParam("password") String password,
            Model model,
            HttpSession session) { // Thêm HttpSession vào đây

        User user = userService.findByEmail(email);
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        if (user == null || !encoder.matches(password, user.getPassword())) {
            model.addAttribute("error", "Email hoặc mật khẩu không đúng!");
            model.addAttribute("user", new User());
            return "public/login";
        }
         // Lưu role vào session
    session.setAttribute("role", user.getRole());
    // Lưu userId nếu cần
    session.setAttribute("userId", user.getId());
        // Đăng nhập thành công, chuyển hướng tới trang chủ hoặc dashboard
        return "redirect:/home";
    }

}
