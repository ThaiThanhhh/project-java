package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.Question;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
@Repository
public interface IQuestionRepository extends JpaRepository<Question, Long> {
    // Lấy tất cả câu hỏi và options (fetch join để tránh N+1)
    @Query("SELECT DISTINCT q FROM Question q LEFT JOIN FETCH q.options")
    List<Question> findAllWithOptions();

    // Nếu cần lấy theo key
    Question findByKey(String key);
}
