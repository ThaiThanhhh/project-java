package com.uth.pickleball.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import com.uth.pickleball.model.Survey;
import com.uth.pickleball.model.SurveyAnswer;
import com.uth.pickleball.model.User;
import com.uth.pickleball.model.Question;
import com.uth.pickleball.repositories.ISurveyAnswerRepository;
import com.uth.pickleball.repositories.ISurveyRepository;
import com.uth.pickleball.repositories.IUserRepository;

import jakarta.servlet.http.HttpSession;

import com.uth.pickleball.repositories.IQuestionRepository;

@Controller
public class SurveyController {
    @Autowired
    private ISurveyRepository surveyRepository;
    @Autowired
    private ISurveyAnswerRepository surveyAnswerRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private IQuestionRepository questionRepository;

    // Hiển thị form khảo sát, chỉ cho user chưa có role
    @GetMapping("/survey")
    public String showSurveyForm(Model model,HttpSession session) {

            // Kiểm tra đăng nhập
        Long userId = (Long) session.getAttribute("userId");
        String role = (String) session.getAttribute("role");

        if (userId == null) {
            return "redirect:/login";
        }
        if (role != null && !role.isEmpty()) {
            return "redirect:/home";
        }
        List<Question> questions = questionRepository.findAllWithOptions();
       model.addAttribute("questions", questions);
        return "public/survey"; // Trả về view khảo sát
      
    }
    @PostMapping("/survey")
    public String submitSurvey(@RequestParam Map<String, String> params, HttpSession session, Model model) {
    Long userId = (Long) session.getAttribute("userId");
    if (userId == null) return "redirect:/login";
    User user = userRepository.findById(userId).orElse(null);
    if (user == null) return "redirect:/login";

    // Kiểm tra user đã có survey chưa
    Survey survey = surveyRepository.findByUser(user);
    if (survey == null) {
        survey = new Survey();
        survey.setUser(user);
        survey = surveyRepository.save(survey);
    }

    List<SurveyAnswer> answers = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().equals("_csrf")) continue;
            // Nếu là câu hỏi role thì lưu vào user, không lưu vào SurveyAnswer
            if (entry.getKey().equals("role")) {
                String roleValue = entry.getValue();
                if (user.getRole() == null || user.getRole().isEmpty()) {
                    user.setRole(roleValue);
                    userRepository.save(user);
                    session.setAttribute("role", roleValue);
                }
                continue;
            }
            SurveyAnswer answer = new SurveyAnswer();
            answer.setSurvey(survey);
            answer.setQuestionKey(entry.getKey());
            answer.setAnswerValue(entry.getValue());
            answers.add(answer);
        }
        surveyAnswerRepository.saveAll(answers);

        return "redirect:/profile"; // Chuyển hướng về trang profile sau khi lưu khảo sát
}


        

   
} 