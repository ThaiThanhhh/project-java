package com.uth.pickleball.model;
import jakarta.persistence.*;

@Entity
@Table(name = "video_library")
public class VideoLibrary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "video_id")
    private Long id;

    @Column(name = "title")
    private String title;

    @Column(name = "youtube_id")
    private String youtubeId;

    @Column(name = "duration")
    private String duration;

    @Column(name = "category")
    private String category;
    // Default constructor
    public VideoLibrary() {
    }
    // Parameterized constructor
    public VideoLibrary(String title, String youtubeId, String duration, String category) {
        this.title = title;
        this.youtubeId = youtubeId;
        this.duration = duration;
        this.category = category;
    }
    // Getters and Setters
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getYoutubeId() {
        return youtubeId;
    }
    public void setYoutubeId(String youtubeId) {
        this.youtubeId = youtubeId;
    }
    public String getDuration() {
        return duration;
    }
    public void setDuration(String duration) {
        this.duration = duration;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    

   
}
