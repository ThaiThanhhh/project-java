package com.uth.pickleball.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "skill")
public class Skill {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="skill_id")
    private Long id;


    @Column(name = "skill_name")
    private String skillName;
    @Column(name = "skill_level")
    private String skillLevel;
    @Column(name = "skill_description")
    private String skillDescription;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
    public Skill() {
    }

    public Skill(Long id, String skillName, String skillLevel, String skillDescription, Coach coach) {
        this.id = id;
        this.skillName = skillName;
        this.skillLevel = skillLevel;
        this.skillDescription = skillDescription;
        this.coach = coach;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }

    public String getSkillLevel() {
        return skillLevel;
    }

    public void setSkillLevel(String skillLevel) {
        this.skillLevel = skillLevel;
    }

    public String getSkillDescription() {
        return skillDescription;
    }

    public void setSkillDescription(String skillDescription) {
        this.skillDescription = skillDescription;
    }

    public Coach getCoach() {
        return coach;
    }

    public void setCoach(Coach coach) {
        this.coach = coach;
    }

}
