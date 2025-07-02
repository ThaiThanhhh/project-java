package com.uth.pickleball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.uth.pickleball.repositories.IStudentRepository;
import com.uth.pickleball.repositories.IUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;

import com.uth.pickleball.model.Student;
import com.uth.pickleball.model.User;
@Controller
public class ProfileController {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IStudentRepository studentRepository;

  @GetMapping("/profile")
    public String showProfile(HttpSession session, Model model) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        String role = user.getRole();
        if ("student".equalsIgnoreCase(role)) {
            Student student = studentRepository.findByUser(user);
            model.addAttribute("student", student);
            return "public/profile/student";
        } else if ("coach".equalsIgnoreCase(role)) {
            // Nếu có trang coach profile thì truyền dữ liệu tương tự
            return "public/profile/coach";
        } else {
            return "redirect:/home";
        }
    }
     @PostMapping("/profile/student")
    public String updateStudentProfile(HttpSession session, String fullName, String phone,String level) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) {
            return "redirect:/login";
        }
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) {
            return "redirect:/login";
        }
        // Cập nhật tên
        user.setFullName(fullName);
        user.setPhone(phone); 
        
        userRepository.save(user);

         // Cập nhật level cho student
        Student student = studentRepository.findByUser(user);
        if (student != null && level != null) {
            student.setLevel(level);
            studentRepository.save(student);
        }

        return "redirect:/profile";
    }

}
