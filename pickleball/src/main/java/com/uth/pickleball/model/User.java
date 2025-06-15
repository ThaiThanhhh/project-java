package com.uth.pickleball.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Table;
import jakarta.persistence.Column;
@Entity
@Table(name = "Users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "full_name")
    private String FullName;
    @Column(name = "email", unique = true)
    private String Email;
    @Column(name = "password")
    private String Password;
    @Column(name = "phone_number")
    private String PhoneNumber;
    @Column(name = "address")
    private String Address;
    @Column(name = "role")
    private String Role;

    public User() {
    }
    public User(String fullName, String email, String password, String phoneNumber, String address, String role) {
        FullName = fullName;
        Email = email;
        Password = password;
        PhoneNumber = phoneNumber;
        Address = address;
        Role = role;
    }

     public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }
    public String getFullName() {
        return FullName;
    }
    public void setFullName(String fullName) {
        FullName = fullName;
    }
    public String getEmail() {
        return Email;
    }
    public void setEmail(String email) {
        Email = email;
    }
    public String getPassword() {
        return Password;
    }
    public void setPassword(String password) {
        Password = password;
    }
    public String getPhoneNumber() {
        return PhoneNumber;
    }
    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }
    public String getAddress() {
        return Address;
    }
    public void setAddress(String address) {
        Address = address;
    }
    public String getRole() {
        return Role;
    }
    public void setRole(String role) {
        Role = role;
    }


   

}
