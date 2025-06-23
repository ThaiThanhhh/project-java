package com.uth.pickleball.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
@Table(name = "experience")
public class Experience {
     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "experience_id")
    private Long id;
    @Column(name = "experience_name")
    private String experienceName;
    @Column(name = "work_place")
    private String workPlace;
    @Column(name = "experience_description")
    private String experienceDescription;
    @ManyToOne
@JoinColumn(name = "coach_id")
private Coach coach;

    // Default constructor
    public Experience() {
    }

    // Parameterized constructor
    public Experience(String experienceName, String workPlace, String experienceDescription, Coach coach) {
        this.experienceName = experienceName;
        this.workPlace = workPlace;
        this.experienceDescription = experienceDescription;
        this.coach = coach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getExperienceName() {
        return experienceName;
    }

    public void setExperienceName(String experienceName) {
        this.experienceName = experienceName;
    }

    public String getWorkPlace() {
        return workPlace;
    }

    public void setWorkPlace(String workPlace) {
        this.workPlace = workPlace;
    }

    public String getExperienceDescription() {
        return experienceDescription;
    }

    public void setExperienceDescription(String experienceDescription) {
        this.experienceDescription = experienceDescription;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }


}
