package com.uth.pickleball.controller;

import com.uth.pickleball.model.Rating;
import com.uth.pickleball.service.RatingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ratings")
public class RatingController {
    @Autowired
    private RatingService ratingService;

    @PostMapping
    public ResponseEntity<Rating> addRating(@RequestBody Rating rating) {
        return ResponseEntity.ok(ratingService.addRating(rating));
    }

    @GetMapping("/coach/{coachId}")
    public ResponseEntity<List<Rating>> getRatingsByCoach(@PathVariable Long coachId) {
        return ResponseEntity.ok(ratingService.getRatingsByCoach(coachId));
    }
} 