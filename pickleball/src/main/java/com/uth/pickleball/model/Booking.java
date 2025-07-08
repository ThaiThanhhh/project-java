package com.uth.pickleball.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;

import java.util.List;

@Entity
@Table(name = "booking")
public class Booking {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingId;

    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;
    @OneToMany(mappedBy = "booking")
    private List<Skill> skills;

    @Column(name = "description")
    private String describe;

    @Column(name = "amount")
    private Double amount;

    @Column(name = "date_create")
    private LocalDateTime dateCreate;

    @Column(name = "location")
    private String location;

    //getter and setter
    
    public Long getBookingId() {
        return bookingId;
    }
    public void setBookingId(Long bookingId) {
        this.bookingId = bookingId;
    }
    public Coach getCoach() {
        return coach;
    }
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
    public String getDescribe() {
        return describe;
    }
    public void setDescribe(String describe) {
        this.describe = describe;
    }
    public Double getAmount() {
        return amount;
    }
    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public LocalDateTime getDateCreate() {
        return dateCreate;
    }
    public void setDateCreate(LocalDateTime dateCreate) {
        this.dateCreate = dateCreate;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }

    // Default constructor
    public Booking() {
    }
    // Parameterized constructor
    public Booking(Coach coach, String describe, Double amount, LocalDateTime dateCreate, String location) {
        this.coach = coach;
        this.describe = describe;
        this.amount = amount;
        this.dateCreate = dateCreate;
        this.location = location;
    }

}
