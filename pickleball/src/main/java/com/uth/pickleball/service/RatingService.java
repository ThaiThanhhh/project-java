package com.uth.pickleball.service;

import com.uth.pickleball.model.Rating;
import com.uth.pickleball.repositories.IRatingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {
    @Autowired
    private IRatingRepository ratingRepo;

    public Rating addRating(Rating rating) {
        return ratingRepo.save(rating);
    }

    public List<Rating> getRatingsByCoach(Long coachId) {
        return ratingRepo.findByCoachId(coachId);
    }

    public List<Rating> getRatingsByUser(Long userId) {
        return ratingRepo.findByUserId(userId);
    }
} 