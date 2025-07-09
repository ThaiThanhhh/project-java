package com.uth.pickleball.model;
import jakarta.persistence.Entity;

import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Column;

@Entity
@Table(name = "student")
public class Student {
       @Id
    @Column(name = "student_id", length = 10)
    private String studentId;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;

    @Column(name = "level", length = 50)
    private String level;

    @Column(name = "learning_style", length = 100)
    private String learningStyle;

    public Student() {
    }
    public Student(String studentId, User user, String level, String learningStyle) {
        this.studentId = studentId;
        this.user = user;
        this.level = level;
        this.learningStyle = learningStyle;
    }
    public String getStudentId() {
        return studentId;
    }
    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public String getLevel() {
        return level;
    }
    public void setLevel(String level) {
        this.level = level;
    }

    public String getLearningStyle() {
        return learningStyle;
    }
    public void setLearningStyle(String learningStyle) {
        this.learningStyle = learningStyle;
    }

}
