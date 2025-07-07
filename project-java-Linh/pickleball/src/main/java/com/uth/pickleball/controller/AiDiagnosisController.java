package com.uth.pickleball.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.ui.Model;
import com.uth.pickleball.service.AiDiagnosisService;
import com.uth.pickleball.model.AnalysisResult;
import org.springframework.beans.factory.annotation.Autowired;

@Controller
@RequestMapping("/ai")
public class AiDiagnosisController {

    @Autowired
    private AiDiagnosisService aiDiagnosisService;

    @GetMapping("/diagnosis")
    public String showAnalysisPage(Model model) {
        System.out.println("Truy cập trang AI Technique Analysis");
        return "private/features/ai";
    }

    @PostMapping("/analyze")
    public String analyzeVideo(@RequestParam("video") MultipartFile videoFile, Model model) {
        try {
            AnalysisResult analysisResult = aiDiagnosisService.analyzeVideo(videoFile);
            System.out.println("Analysis Result: " + (analysisResult != null ? analysisResult.toString() : "null"));
            model.addAttribute("result", analysisResult);
            model.addAttribute("message", "Phân tích hoàn tất thành công!");
            System.out.println("analysisResult: " + analysisResult.getPerformanceOverTime());
        } catch (Exception e) {
            model.addAttribute("error", "Lỗi khi phân tích video: " + e.getMessage());
            System.out.println("Error: " + e.getMessage());
        }
        return "private/features/ai";
    }
}