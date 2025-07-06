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
import com.uth.pickleball.model.Student;
import com.uth.pickleball.model.Coach;
import com.uth.pickleball.model.Option;
import com.uth.pickleball.repositories.ISurveyAnswerRepository;
import com.uth.pickleball.repositories.ISurveyRepository;
import com.uth.pickleball.repositories.IUserRepository;
import com.uth.pickleball.repositories.IStudentRepository;
import com.uth.pickleball.repositories.IQuestionRepository;
import com.uth.pickleball.repositories.ICoachRepository;
import com.uth.pickleball.service.OptionService;

import jakarta.servlet.http.HttpSession;

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
    @Autowired
    private IStudentRepository studentRepository;
    @Autowired
    private OptionService optionService;
    @Autowired
    private ICoachRepository coachRepository;

    // Hiển thị form khảo sát, chỉ cho user chưa có role
    @GetMapping("/survey")
    public String showSurveyForm(Model model, HttpSession session) {
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
        return "public/survey";
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
        String roleValue = null;
        String levelValue = null;
        String levelContent = null;

        String learningStyleValue = null;
        String learningStyleContent = null;
        // Lấy ID câu hỏi tournaments động từ DB
        Question tournamentsQuestion = questionRepository.findByKey("tournaments");
        Long tournamentsQuestionId = tournamentsQuestion != null ? tournamentsQuestion.getId() : null;

        // Lấy ID câu hỏi movement động từ DB
        Question movementQuestion = questionRepository.findByKey("movement");
        Long movementQuestionId = movementQuestion != null ? movementQuestion.getId() : null;

        for (Map.Entry<String, String> entry : params.entrySet()) {
            if (entry.getKey().equals("_csrf")) continue;

            // Nếu là câu hỏi role thì lưu vào user, không lưu vào SurveyAnswer
            if (entry.getKey().equals("role")) {
                roleValue = entry.getValue();
                if (user.getRole() == null || user.getRole().isEmpty()) {
                    user.setRole(roleValue);
                    userRepository.save(user);
                    session.setAttribute("role", roleValue);

                    // Nếu là student thì sinh student_id
                    if ("student".equals(roleValue)) {
                        long count = studentRepository.count() + 1;
                        String studentId = String.format("STU_%03d", count);

                        Student student = new Student();
                        student.setStudentId(studentId);
                        student.setUser(user);
                        // Gán level nếu đã có
                        if (levelContent != null) {
                            student.setLevel(levelContent);
                        }
                        studentRepository.save(student);
                    }
                    // Nếu là coach thì sinh coach_id
                    if ("coach".equals(roleValue)) {
                        long count = coachRepository.count() + 1;
                        String coachId = String.format("COACH_%03d", count);

                        Coach coach = new Coach();
                        coach.setCoachId(coachId);
                        coach.setUser(user);
                        coachRepository.save(coach);
                    }
                }
                continue;
            }

            // Lấy đáp án câu 7 (question_key = "tournaments") để lấy level
            if (entry.getKey().equals("tournaments") && tournamentsQuestionId != null) {
                levelValue = entry.getValue();
                // Lấy content từ OptionService
                Option option = optionService.findOptionByQuestionIdAndValue(tournamentsQuestionId, levelValue);
                if (option != null) {
                    levelContent = option.getContent();
                }
            }
            // Lấy đáp án câu movement để lấy learningStyle
            if (entry.getKey().equals("movement") && movementQuestionId != null) {
                learningStyleValue = entry.getValue();
                Option option = optionService.findOptionByQuestionIdAndValue(movementQuestionId, learningStyleValue);
                if (option != null) {
                    learningStyleContent = learningStyleValue; // Lưu key
                }
            }

            SurveyAnswer answer = new SurveyAnswer();
            answer.setSurvey(survey);
            answer.setQuestionKey(entry.getKey());
            answer.setAnswerValue(entry.getValue());
            answers.add(answer);
        }
        surveyAnswerRepository.saveAll(answers);

        // Sau khi đã lưu student, cập nhật level nếu là student
        if ("student".equals(roleValue) && levelContent != null) {
            Student student = studentRepository.findByUser(user);
            if (student != null) {
                    if (levelContent != null) student.setLevel(levelContent);
                    if (learningStyleContent != null) student.setLearningStyle(learningStyleContent);
                studentRepository.save(student);
            }
        }

        return "redirect:/profile";
    }
}