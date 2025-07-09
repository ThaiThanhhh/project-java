package com.uth.pickleball.service;

import com.uth.pickleball.model.VideoLibrary;
import com.uth.pickleball.repositories.IVideoLibraryRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class VideoLibraryService {
    private final IVideoLibraryRepository repo;

    public VideoLibraryService(IVideoLibraryRepository repo) {
        this.repo = repo;
    }

    public List<VideoLibrary> getAllVideos() {
        return repo.findAll();
    }
}