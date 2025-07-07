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
@Table(name = "schedules")
public class Shedule {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Schedule_id")
    private Long sheduleId;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @Column(name = "date")
    private String date;
    @Column(name = "start_time")
    private String startTime;
    @Column(name = "end_time")
    private String endTime;

    // default constructor
    public Shedule() {
    }
    // parameterized constructor
    public Shedule(Coach coach, String date, String startTime, String endTime)
    {
        this.coach = coach;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }
    
        // getters, setters
    public Long getSheduleId() {
        return sheduleId;
    }
    public void setSheduleId(Long sheduleId) {
        this.sheduleId = sheduleId;
    }
    public Coach getCoach() {
        return coach;
    }
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getStartTime() {
        return startTime;
    }
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }
    public String getEndTime() {
        return endTime;
    }
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
    


}
