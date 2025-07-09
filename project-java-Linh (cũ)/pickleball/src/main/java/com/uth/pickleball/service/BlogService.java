package com.uth.pickleball.service;

import org.springframework.stereotype.Service;
import com.uth.pickleball.model.Blog;
import com.uth.pickleball.repositories.IBlogRepository;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

@Service
public class BlogService {
    public final IBlogRepository blogRepository;
    @Autowired
    public BlogService(IBlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }
    public Page<Blog> getBlogs(Pageable pageable) {
    return blogRepository.findAll(pageable);
}

    public List<Blog> getAllBlogs() {
        return blogRepository.findAll();
    }
}
