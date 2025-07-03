package com.uth.pickleball.advice;

import com.uth.pickleball.model.User;
import com.uth.pickleball.repositories.IUserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.ui.Model;

@ControllerAdvice
public class GlobalModelAttributeAdvice {
    @Autowired
    private IUserRepository userRepository;

    @ModelAttribute
    public void addAvatarUrlToModel(HttpSession session, Model model) {
        String avatarUrl = "/pickleball/images/avatar.jpg";
        if (session != null) {
            Long userId = (Long) session.getAttribute("userId");
            if (userId != null) {
                User user = userRepository.findById(userId).orElse(null);
                if (user != null && user.getAvatarUrl() != null && !user.getAvatarUrl().isEmpty()) {
                    avatarUrl = user.getAvatarUrl();
                }
            }
        }
        model.addAttribute("avatarUrl", avatarUrl);
    }
}