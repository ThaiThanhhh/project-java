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
import com.uth.pickleball.repositories.ICoachRepository;
import com.uth.pickleball.model.Certification;
import com.uth.pickleball.model.Coach;
import com.uth.pickleball.model.Student;
import com.uth.pickleball.model.User;
import java.util.UUID;
import java.util.List;


import com.uth.pickleball.repositories.ICertificationRepository;
@Controller
public class ProfileController {
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private ICoachRepository coachRepository;
    @Autowired
    private ICertificationRepository certificationRepository;

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
        String avatarUrl = user.getAvatarUrl();
        if (avatarUrl == null || avatarUrl.isEmpty()) {
            avatarUrl = "/pickleball/images/avatar.jpg";
        }
        model.addAttribute("avatarUrl", avatarUrl);

        if ("student".equalsIgnoreCase(role)) {
            Student student = studentRepository.findByUser(user);
            model.addAttribute("student", student);
            return "public/profile/student";
        } else if ("coach".equalsIgnoreCase(role)) {
            Coach coach = coachRepository.findByUser(user);
            List<Certification> certifications = certificationRepository.findByCoach(coach);

            model.addAttribute("coach", coach);
            model.addAttribute("certifications", certifications);
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
    @PostMapping("/profile/coach")
        public String updateCoachProfile(
                HttpSession session,
                @RequestParam String fullName,
                @RequestParam String phone,
                @RequestParam String address,
                @RequestParam(value = "avatar", required = false) MultipartFile avatarFile
        ) {
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

            // Cập nhật địa chỉ cho coach
            Coach coach = coachRepository.findByUser(user);
            if (coach != null) {
                coach.setAddress(address);
                coachRepository.save(coach);
            }

            return "redirect:/profile";
        }
        @PostMapping("/profile/coach/certification")
    public String addCertification(
            HttpSession session,
            @RequestParam String certificationName,
            @RequestParam("certification__img") MultipartFile imgFile,
            @RequestParam String certificationID
    ) {
            Long userId = (Long) session.getAttribute("userId");
            if (userId == null) {
                return "redirect:/login";
            }
            User user = userRepository.findById(userId).orElse(null);
            if (user == null) {
                return "redirect:/login";
            }
            Coach coach = coachRepository.findByUser(user);
        // Lưu ảnh chứng chỉ
        String imgUrl = "";
        if (imgFile != null && !imgFile.isEmpty()) {
            try {
                String uploadDir = "src/main/resources/static/uploads/";
                String fileName = UUID.randomUUID() + "_" + imgFile.getOriginalFilename();
                Path uploadPath = Paths.get(uploadDir);
                if (!Files.exists(uploadPath)) Files.createDirectories(uploadPath);
                Files.copy(imgFile.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
                imgUrl = "/pickleball/uploads/" + fileName;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Certification cert = new Certification();
        cert.setCoach(coach);
        cert.setCertificationName(certificationName);
        cert.setImgUrl(imgUrl);
        cert.setIdNumber(certificationID);
        certificationRepository.save(cert);

        return "redirect:/profile";
    }

}
