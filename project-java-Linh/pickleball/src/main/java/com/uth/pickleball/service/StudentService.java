package com.uth.pickleball.service;

import com.uth.pickleball.model.Student;
import com.uth.pickleball.repositories.IStudentRepository;
import org.springframework.stereotype.Service;
import com.uth.pickleball.model.User;

@Service
public class StudentService {
    public final IStudentRepository studentRepository;

    public StudentService(IStudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }
     public Student findByUser(User user) {
        return studentRepository.findByUser(user);
    }

    public Student save(Student student) {
        return studentRepository.save(student);
    }

}
