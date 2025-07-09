package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import jakarta.servlet.http.HttpSession;


@Controller
public class LogoutController {
    // Xử lý đăng xuất
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        // Xóa thông tin người dùng khỏi session
        session.invalidate();
        // Chuyển hướng về trang đăng nhập hoặc trang chính
        return "redirect:/login";
    }

}
