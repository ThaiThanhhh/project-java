
package com.uth.pickleball.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.GenerationType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Column;

@Entity
@Table(name = "Blogs")
public class Blog {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "blog_id")
    private Long id;

    @Column(name = "title",length=255)
    private String title;

    @Column(name = "summary",length=255)
    private String summary;

    @Column(name = "image_url", length=255)
    private String imageUrl;

    @Column(name = "article_url", length=255)
    private String articleUrl;

    @Column(name = "published_date")
    private String publishedDate;

    @Column(name = "hidden", length = 1)
    private int hidden;

    public Blog() {
    }
    public Blog(String title, String summary, String imageUrl, String articleUrl, String publishedDate, int hidden) {
        this.title = title;
        this.summary = summary;
        this.imageUrl = imageUrl;
        this.articleUrl = articleUrl;
        this.publishedDate = publishedDate;
        this.hidden = hidden;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getId() {
        return this.id;
    }
    public String getTitle() {
        return title;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public String getSummary() {
        return summary;
    }
    public void setSummary(String summary) {
        this.summary = summary;
    }
    public String getImageUrl() {
        return imageUrl;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getArticleUrl() {
        return articleUrl;
    }
    public void setArticleUrl(String articleUrl) {
        this.articleUrl = articleUrl;
    }
    public String getPublishedDate() {
        return publishedDate;
    }
    public void setPublishedDate(String publishedDate) {
        this.publishedDate = publishedDate;
    }
    public int getHidden() {
        return hidden;
    }
    public void setHidden(int hidden) {
        this.hidden = hidden;
    }

}
