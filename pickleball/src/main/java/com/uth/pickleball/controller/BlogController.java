package com.uth.pickleball.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.uth.pickleball.model.Blog;
import com.uth.pickleball.service.BlogService;
import org.springframework.ui.Model;
import org.springframework.data.domain.Page;

@Controller
public class BlogController {

    @Autowired
    private BlogService blogService;

//     @GetMapping("/blog")
//     public String showBlogPage(Model model) {
//         model.addAttribute("blogs", blogService.getAllBlogs());
//         return "public/blog";
//     }
@GetMapping("/blog")
    public String showBlogPage(
            @RequestParam(defaultValue = "0") int page,
            Model model) {
        int pageSize = 4;
        Page<Blog> blogPage = blogService.getBlogs(PageRequest.of(page, pageSize));
        model.addAttribute("blogPage", blogPage);
        return "public/blog";
    }
 }
