package com.uth.pickleball.controller;

import java.security.Principal;
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
import com.uth.pickleball.model.Question; // Entity cho câu hỏi
import com.uth.pickleball.repositories.ISurveyAnswerRepository;
import com.uth.pickleball.repositories.ISurveyRepository;
import com.uth.pickleball.repositories.IUserRepository;
import com.uth.pickleball.repositories.IQuestionRepository; // Repository cho câu hỏi

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

    // Hiển thị form khảo sát với dữ liệu động từ DB
    @GetMapping("/survey")
    public String showSurveyForm(Model model) {
        List<Question> questions = questionRepository.findAllWithOptions(); // Lấy tất cả câu hỏi và đáp án
        model.addAttribute("questions", questions);
        
        return "public/survey";
    }

    // Xử lý lưu khảo sát
    @PostMapping("/survey")
    public String submitSurvey(@RequestParam Map<String, String> params, Principal principal, Model model) {
        // Lấy user hiện tại (giả sử đã đăng nhập)
        User user = userRepository.findByEmail(principal.getName());

        // Tạo survey mới cho user (hoặc lấy survey cũ nếu chỉ cho làm 1 lần)
        Survey survey = new Survey();
        survey.setUser(user);
        survey = surveyRepository.save(survey);

        // Lưu từng câu trả lời vào SurveyAnswer
        List<SurveyAnswer> answers = new ArrayList<>();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            // Bỏ qua các tham số không phải câu hỏi (nếu có)
            if (entry.getKey().equals("_csrf")) continue;

            SurveyAnswer answer = new SurveyAnswer();
            answer.setSurvey(survey);
            answer.setQuestionKey(entry.getKey());
            answer.setAnswerValue(entry.getValue());
            answers.add(answer);
        }
        surveyAnswerRepository.saveAll(answers);

        model.addAttribute("message", "Cảm ơn bạn đã hoàn thành khảo sát!");
        return "public/home"; // Trang cảm ơn hoặc trang chủ
    }
}