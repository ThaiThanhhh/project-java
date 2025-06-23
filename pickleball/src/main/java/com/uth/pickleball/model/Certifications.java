package com.uth.pickleball.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;  
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "Certifications")
public class Certifications {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "certification_id")
    private Long id;

    
    @Column(name = "my_name")
    private String myName;

    @Column(name = "cccd")
    private String cccd;

    @Column(name = "certifications_name")
    private String certificationsName;

    @Column(name = "certification_image")
    private String certificationImage;

    @Column(name = "ce_id")
    private String ce_Id;

    // Default constructor
    public Certifications() {
    }
    // Parameterized constructor
    public Certifications(String myName, String cccd, String certificationsName, String certificationImage,
            String ce_Id) {
        this.myName = myName;
        this.cccd = cccd;
        this.certificationsName = certificationsName;
        this.certificationImage = certificationImage;
        this.ce_Id = ce_Id;
    }
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMyName() {
        return myName;
    }
    public void setMyName(String myName) {
        this.myName = myName;
    }
    public String getCccd() {
        return cccd;
    }
    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public String getCertificationsName() {
        return certificationsName;
    }
    public void setCertificationsName(String certificationsName) {
        this.certificationsName = certificationsName;
    }
    public String getCertificationImage() {
        return certificationImage;
    }
    public void setCertificationImage(String certificationImage) {
        this.certificationImage = certificationImage;
    }
    public String getCe_Id() {
        return ce_Id;
    }
    public void setCe_Id(String ce_Id) {
        this.ce_Id = ce_Id;
    }

}
