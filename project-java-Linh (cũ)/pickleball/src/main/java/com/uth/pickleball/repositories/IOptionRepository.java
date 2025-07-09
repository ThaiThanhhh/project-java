package com.uth.pickleball.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.Option;

@Repository
public interface IOptionRepository extends JpaRepository<Option, Long> {
    // Các phương thức truy vấn tùy chỉnh nếu cần
    Option findByQuestionIdAndValue(Long questionId, String value);


}
