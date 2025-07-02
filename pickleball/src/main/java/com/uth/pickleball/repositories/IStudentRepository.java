package com.uth.pickleball.repositories;

import org.springframework.stereotype.Repository;
import com.uth.pickleball.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import com.uth.pickleball.model.User;

@Repository
public interface IStudentRepository  extends JpaRepository<Student, String> {
    // Các phương thức truy vấn tùy chỉnh nếu cần
    Student findByUser(User user);
 

}
