package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.Survey;
import com.uth.pickleball.model.User;
import java.util.Optional;

@Repository
public interface ISurveyRepository extends JpaRepository<Survey, Long> {
    // Additional query methods can be defined here if needed
    Optional<Survey> findByUserId(Long userId);
    Survey findByUser(User user);

}
