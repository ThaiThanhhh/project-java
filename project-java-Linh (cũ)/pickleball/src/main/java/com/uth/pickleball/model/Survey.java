package com.uth.pickleball.model;
import jakarta.persistence.*;
import java.util.List;


@Entity
@Table(name = "survey")
public class Survey {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_id")
    private Long id;

      @OneToOne
    @JoinColumn(name = "user_id", unique = true)
    private User user;

    @OneToMany(mappedBy = "survey", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<SurveyAnswer> answers;

    // Getters and setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }
    public List<SurveyAnswer> getAnswers() {
        return answers;
    }
    public void setAnswers(List<SurveyAnswer> answers) {
        this.answers = answers;
    }
    
}
