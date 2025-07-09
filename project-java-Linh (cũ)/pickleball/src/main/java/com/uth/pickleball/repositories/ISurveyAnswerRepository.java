package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.SurveyAnswer;
import java.util.List;

@Repository 
public interface ISurveyAnswerRepository extends JpaRepository<SurveyAnswer, Long> {
    // Additional query methods can be defined here if needed
    List<SurveyAnswer> findBySurveyId(Long surveyId);

}
