package com.uth.pickleball.model;

import jakarta.persistence.*;

@Entity
@Table(name = "survey_option")
public class Option {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "option_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;

    @Column(name = "value")
    private String value; // VD: "never", "beginner", ...

 @Column(name = "content")
    private String content;

    // Getter/setter...
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Question getQuestion() {
        return question;
    }
    public void setQuestion(Question question) {
        this.question = question;
    }
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}