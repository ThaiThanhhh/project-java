package com.uth.pickleball.model;

import jakarta.persistence.Table;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;   
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;   

@Entity
@Table(name = "certification")
public class Certification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long certificationsId;
    @ManyToOne
    @JoinColumn(name = "coach_id")
    private Coach coach;

    @Column(name = "certification_name", length = 100, nullable = false)
    private String certificationName;

    @Column(name = "img_url", length = 255, nullable = false)
    private String imgUrl;

    @Column(name = "id_number", length = 100, nullable = false)
    private String idNumber;
    @Column(name = "verify", nullable = false)
    private boolean verify = false; // mặc định là chưa xác thực

    // Default constructor
    public Certification() {
    }
    // Parameterized constructor
    public Certification(Coach coach, String certificationName, String imgUrl, String idNumber, boolean verify)
    {
        this.coach = coach;
        this.certificationName = certificationName;
        this.imgUrl = imgUrl;
        this.idNumber = idNumber;
        this.verify = verify;
    }
    // Getters and Setters
    public boolean isVerify() {
        return verify;
    }
    public void setVerify(boolean verify) {
        this.verify = verify;
    }
   
    public Long getCertificationsId() {
        return certificationsId;
    }
    public void setCertificationsId(Long certificationsId) {
        this.certificationsId = certificationsId;
    }
    public Coach getCoach() {
        return coach;
    }
    public void setCoach(Coach coach) {
        this.coach = coach;
    }
    public String getCertificationName() {
        return certificationName;
    }
    public void setCertificationName(String certificationName) {
        this.certificationName = certificationName;
    }
    public String getImgUrl() {
        return imgUrl;
    }
    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }
    public String getIdNumber() {
        return idNumber;
    }
    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }


}
