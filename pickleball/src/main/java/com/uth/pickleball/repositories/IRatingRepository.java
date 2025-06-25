package com.uth.pickleball.repositories;

import com.uth.pickleball.model.Rating;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface IRatingRepository extends JpaRepository<Rating, Long> {
    List<Rating> findByCoachId(Long coachId);
    List<Rating> findByUserId(Long userId);
} 