package com.uth.pickleball.controller;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;

import com.uth.pickleball.service.BookingService;
import jakarta.servlet.http.HttpSession;
import com.uth.pickleball.repositories.ICoachRepository;
import com.uth.pickleball.repositories.ISkillRepository;
import com.uth.pickleball.repositories.IExperienceRepository;
import com.uth.pickleball.repositories.IUserRepository;
import com.uth.pickleball.model.*;

@Controller
public class BookingController {

    @Autowired
    private BookingService bookingService;
    @Autowired
    private ICoachRepository coachRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private ISkillRepository skillRepository;
    @Autowired
    private IExperienceRepository experienceRepository;

    // Hiển thị form tạo booking
    @GetMapping("/booking")
    public String bookingForm(HttpSession session, Model model) {
        model.addAttribute("booking", null);
        model.addAttribute("isEdit", false);
        model.addAttribute("skills", new ArrayList<Skill>());
        model.addAttribute("experiences", new ArrayList<Experience>());
        return "private/for_coach/form_booking";
    }

    // Hiển thị form sửa booking
    @GetMapping("/booking/edit/{id}")
    public String editBookingForm(
            @PathVariable Long id,
            HttpSession session,
            Model model
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";
        Coach coach = coachRepository.findByUser(user);

        Booking booking = bookingService.findById(id);
        if (booking == null || !booking.getCoach().getCoachId().equals(coach.getCoachId())) {
            return "redirect:/profile";
        }
        // Lấy skill/experience theo booking
        List<Skill> skills = skillRepository.findByBooking(booking);
        List<Experience> experiences = experienceRepository.findByBooking(booking);

        // Xóa booking khỏi skill/experience để tránh vòng lặp khi render JS
        if (skills != null) {
            for (Skill s : skills) s.setBooking(null);
        }
        if (experiences != null) {
            for (Experience e : experiences) e.setBooking(null);
        }

        model.addAttribute("booking", booking);
        model.addAttribute("isEdit", true);
        model.addAttribute("skills", skills != null ? skills : new ArrayList<>());
        model.addAttribute("experiences", experiences != null ? experiences : new ArrayList<>());
        return "private/for_coach/form_booking";
    }

    // Xử lý tạo booking mới
    @PostMapping("/booking")
    public String createBooking(
            HttpSession session,
            @RequestParam String describe,
            @RequestParam("price") Double amount,
            @ModelAttribute BookingForm bookingForm
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";
        Coach coach = coachRepository.findByUser(user);

        Booking booking = new Booking();
        booking.setCoach(coach);
        booking.setDescribe(describe);
        booking.setAmount(amount);
        booking.setDateCreate(LocalDateTime.now());
        bookingService.save(booking);

        // Lưu skills
        List<Skill> skills = bookingForm.getSkills();
        if (skills != null) {
            for (Skill skill : skills) {
                skill.setBooking(booking); // Gắn booking
                skillRepository.save(skill);
            }
        }
        // Lưu experiences
        List<Experience> experiences = bookingForm.getExperiences();
        if (experiences != null) {
            for (Experience exp : experiences) {
                exp.setBooking(booking); // Gắn booking
                experienceRepository.save(exp);
            }
        }

        return "redirect:/profile";
    }

    // Xử lý cập nhật booking
    @PostMapping("/booking/edit/{id}")
    public String updateBooking(
            @PathVariable Long id,
            HttpSession session,
            @RequestParam String describe,
            @RequestParam("price") Double amount,
            @ModelAttribute BookingForm bookingForm
    ) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";
        Coach coach = coachRepository.findByUser(user);

        Booking booking = bookingService.findById(id);
        if (booking != null && booking.getCoach().getCoachId().equals(coach.getCoachId())) {
            booking.setDescribe(describe);
            booking.setAmount(amount);
            bookingService.save(booking);

              // XÓA SKILL/EXPERIENCE CŨ CỦA BOOKING NÀY
                List<Skill> oldSkills = skillRepository.findByBooking(booking);
                skillRepository.deleteAll(oldSkills);
                List<Experience> oldExps = experienceRepository.findByBooking(booking);
                experienceRepository.deleteAll(oldExps);

            // Lưu mới skill/experience từ form
                List<Skill> skills = bookingForm.getSkills();
                if (skills != null) {
                    for (Skill skill : skills) {
                        skill.setBooking(booking);
                        skillRepository.save(skill);
                    }
                }
                List<Experience> experiences = bookingForm.getExperiences();
                if (experiences != null) {
                    for (Experience exp : experiences) {
                        exp.setBooking(booking);
                        experienceRepository.save(exp);
                    }
                }
            }
                    return "redirect:/profile";
            }

    // Xóa booking
    @PostMapping("/booking/delete/{id}")
    public String deleteBooking(@PathVariable Long id, HttpSession session) {
        Long userId = (Long) session.getAttribute("userId");
        if (userId == null) return "redirect:/login";
        User user = userRepository.findById(userId).orElse(null);
        if (user == null) return "redirect:/login";
        Coach coach = coachRepository.findByUser(user);

        Booking booking = bookingService.findById(id);
        if (booking != null && booking.getCoach().getCoachId().equals(coach.getCoachId())) {
            // Xóa skills/experiences của booking này
            List<Skill> oldSkills = skillRepository.findByBooking(booking);
            skillRepository.deleteAll(oldSkills);
            List<Experience> oldExps = experienceRepository.findByBooking(booking);
            experienceRepository.deleteAll(oldExps);

            bookingService.delete(id);
        }
        return "redirect:/profile";
    }
}