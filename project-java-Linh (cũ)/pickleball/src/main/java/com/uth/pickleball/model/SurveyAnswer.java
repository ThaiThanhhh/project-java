package com.uth.pickleball.model;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
@Entity
@Table(name = "survey_answer")
public class SurveyAnswer {
      @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "survey_answer_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "survey_id")
    private Survey survey;

    @Column(name = "question_key")
    private String questionKey; // VD: "experience", "frequency", ...

    @Column(name = "answer_value")
    private String answerValue; // VD: "never", "beginner", ...

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    public Survey getSurvey() {
        return survey;
    }   
public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    public String getQuestionKey() {
        return questionKey;
    }
    public void setQuestionKey(String questionKey) {
        this.questionKey = questionKey;
    }
    public String getAnswerValue() {
        return answerValue;
    }
    public void setAnswerValue(String answerValue) {
        this.answerValue = answerValue;
    }
    

}
