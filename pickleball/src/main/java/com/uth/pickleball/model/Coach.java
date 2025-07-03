package com.uth.pickleball.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "coach")
public class Coach {
      @Id
    @Column(name = "coach_id", length = 10)
    private String coachId;

     @Column(name = "verified")
    private boolean verified;



    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "user_id", nullable = false, unique = true)
    private User user;
    // Default constructor
    public Coach() {
    }
    // Parameterized constructor
    public Coach(String coachId, boolean verified, User user) {
        this.coachId = coachId;
        this.verified = verified;
        this.user = user;
    }


    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }
    public User getUser() {
        return user;
    }
    public void setUser(User user) {
        this.user = user;
    }


}
