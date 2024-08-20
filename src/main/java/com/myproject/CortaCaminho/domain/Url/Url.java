package com.myproject.CortaCaminho.domain.Url;

import com.myproject.CortaCaminho.domain.user.User;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
public class Url {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String url;
    private String shortenedUrl;
    private Status status;
    private LocalDateTime statusTime;
    private Boolean scheduledStatus;
    private String img;
    private int views;
    private String keywords;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Url(Optional<Url> byId) {
    }

    public Url() {

    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getShortenedUrl() {
        return shortenedUrl;
    }

    public void setShortenedUrl(String shortenedUrl) {
        this.shortenedUrl = shortenedUrl;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public LocalDateTime getStatusTime() {
        return statusTime;
    }

    public void setStatusTime(LocalDateTime statusTime) {
        this.statusTime = statusTime;
    }

    public Boolean getScheduledStatus() {
        return scheduledStatus;
    }

    public void setScheduledStatus(Boolean scheduledStatus) {
        this.scheduledStatus = scheduledStatus;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    @Override
    public String toString() {
        return "Url{" +
                "id=" + id +
                ", titulo='" + title + '\'' +
                ", url='" + url + '\'' +
                ", shortenedUrl='" + shortenedUrl + '\'' +
                ", status=" + status +
                ", statusTime=" + statusTime +
                ", scheduledStatus=" + scheduledStatus +
                ", img='" + img + '\'' +
                ", views=" + views +
                ", user=" + (user != null ? user.getProfile().getName() : "N/A") +
                '}';
    }

}
