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
    private Long experienceId;

    @Column(name = "experience_name")
    private String experienceName;
    @Column(name = "work_place")
    private String workPlace;
    @Column(name = "description")
    private String description;

    @ManyToOne
@JoinColumn(name = "booking_id")
private Booking booking;

    public Long getExperienceId() {
        return experienceId;
    }
    public void setExperienceId(Long experienceId) {
        this.experienceId = experienceId;
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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Booking getBooking() {
        return booking;
    }
    public void setBooking(Booking booking) {
        this.booking = booking;
    }
    


}
