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
            @RequestParam(value = "remember", required = false) String remember,

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
        // Nếu có "remember", set thời gian session lâu hơn mặc định
        if (remember != null) {
            session.setMaxInactiveInterval(60 * 60 * 24 * 7); // 7 ngày
        } else {
            session.setMaxInactiveInterval(60 * 30); // 30 phút mặc định
        }
         // Kiểm tra role: nếu chưa có thì chuyển sang survey, nếu có rồi thì về home
    if (user.getRole() == null || user.getRole().isEmpty()) {
        return "redirect:/survey";
    } else {
        return "redirect:/home"; // hoặc "/" hoặc dashboard tùy bạn
    }
}

}
