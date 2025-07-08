package com.uth.pickleball.controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.ui.Model;
import com.uth.pickleball.model.VideoLibrary;
import com.uth.pickleball.service.VideoLibraryService;
import java.util.List;

@Controller
public class VideoLibraryController {

    private final VideoLibraryService videoLibraryService;

    public VideoLibraryController(VideoLibraryService videoLibraryService) {
        this.videoLibraryService = videoLibraryService;
    }

    @GetMapping("/videolibrary")
    public String showVideoLibrary(Model model, HttpSession session) {
        if (!"student".equals(session.getAttribute("role"))) {
            return "redirect:/login";
        }
        List<VideoLibrary> videos = videoLibraryService.getAllVideos();
        model.addAttribute("videos", videos);
        return "private/features/video_library";
    }

}