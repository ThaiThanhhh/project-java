package com.uth.pickleball.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.OneToOne;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.CascadeType;
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(name = "full_name", nullable = false)
    private String fullName;
    @Column(name = "email", unique = true, nullable = false)
    private String email;
    @Column(name = "password", nullable = false)
    private String password;
    @Column(name = "phone", length = 15)
    private String phone;
    @Column(name = "avatar_url", length = 255)
    private String avatarUrl;
    @Column(name = "role", length = 20)
    private String role;


    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private Survey survey;

    public User() {
    }
    public User(String fullName, String email, String password, String phone, String role, String avatarUrl) {
        this.fullName = fullName;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.role = role;
        this.avatarUrl = avatarUrl;
    }

     public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    public String getFullName() {
        return fullName;
    }
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }


   public Survey getSurvey() {
       return survey;
   }

   public void setSurvey(Survey survey) {
       this.survey = survey;
   }
    public String getAvatarUrl() {
          return avatarUrl;
     }
    
     public void setAvatarUrl(String avatarUrl) {
          this.avatarUrl = avatarUrl;
     }
 

}
