package com.uth.pickleball.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;



@Entity
@Table(name = "Coach")
public class Coach {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id", unique = true, nullable = false)
    private User user;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Skill> skills;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Experience> experiences;

    @OneToMany(mappedBy = "coach", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Calendar> calendars;

    @Column(name = "price")
    private Double price;

    @Column(name = "verified")
    private Boolean verified;
    /////////////////////////

    @Column(name = "teaching_methods")
    private String teachingMethods;

    @ManyToOne
    @JoinColumn(name = "certifications_id")
    private Certifications certifications;
    // Default constructor
    public Coach() {

    
    }
    // Parameterized constructor
    public Coach(User user, List<Skill> skills, List<Experience> experiences, List
<Calendar> calendars, Double price, Boolean verified, String teachingMethods, Certifications certifications) {
        this.user = user;
        this.skills = skills;
        this.experiences = experiences;
        this.calendars = calendars;
        this.price = price;
        this.verified = verified;
        this.teachingMethods = teachingMethods;
        this.certifications = certifications;
    }
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
    public List<Skill> getSkills() {
        return skills;
    }
    public void setSkills(List<Skill> skills) {
        this.skills = skills;
    }
    public List<Experience> getExperiences() {
        return experiences;
    }
    public void setExperiences(List<Experience> experiences) {
        this.experiences = experiences;
    }
    public List<Calendar> getCalendars() {
        return calendars;
    }
    public void setCalendars(List<Calendar> calendars) {
        this.calendars = calendars;
    }
    public Double getPrice() {
        return price;
    }
    public void setPrice(Double price) {
        this.price = price;
    }
    public Boolean getVerified() {
        return verified;
    }
    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
    public String getTeachingMethods() {
        return teachingMethods;
    }
    public void setTeachingMethods(String teachingMethods) {
        this.teachingMethods = teachingMethods;
    }
    public Certifications getCertifications() {
        return certifications;
    }
    public void setCertifications(Certifications certifications) {
        this.certifications = certifications;
    }
    
    
}
