package com.uth.pickleball.controller;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.uth.pickleball.repositories.IStudentRepository;
import com.uth.pickleball.repositories.IUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.ui.Model;



import com.uth.pickleball.model.Student;
import com.uth.pickleball.model.User;
import java.util.UUID;
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
            //    // Xử lý avatar base64
            // String avatarBase64 = null;
            // if (user.getAvatarData() != null) {
            //     avatarBase64 = Base64.getEncoder().encodeToString(user.getAvatarData());
            // }
            // model.addAttribute("avatarBase64", avatarBase64);

             // Lấy đường dẫn ảnh đại diện
            String avatarUrl = user.getAvatarUrl();
            if (avatarUrl == null || avatarUrl.isEmpty()) {
                avatarUrl = "/pickleball/images/avatar.jpg";
            }
            model.addAttribute("avatarUrl", avatarUrl);
            return "public/profile/student";
        } else if ("coach".equalsIgnoreCase(role)) {
            // Nếu có trang coach profile thì truyền dữ liệu tương tự
            return "public/profile/coach";
        } else {
            return "redirect:/home";
        }
    }
    @PostMapping("/profile/student")
public String updateStudentProfile(
            HttpSession session,
            @RequestParam String fullName,
            @RequestParam String phone,
            @RequestParam String level,
            @RequestParam String learningStyle,
            @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
    ) { 
        // Kiểm tra session để lấy userId      
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
         // Lưu file ảnh lên server và cập nhật đường dẫn
        if (avatarFile != null && !avatarFile.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/static/uploads/";
                String fileName = UUID.randomUUID() + "_" + avatarFile.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                Files.copy(avatarFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                user.setAvatarUrl("/pickleball/uploads/" + fileName);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        userRepository.save(user);

        // Cập nhật level và learningStyle cho student
        Student student = studentRepository.findByUser(user);
        if (student != null) {
            if (level != null) student.setLevel(level);
            if (learningStyle != null) student.setLearningStyle(learningStyle);
            studentRepository.save(student);
        }

        return "redirect:/profile";
    }

}
