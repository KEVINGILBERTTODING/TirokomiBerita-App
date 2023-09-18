package com.example.tiroberita.model;

public class SavePostModel {
    private String link;
    private String title;
    private String description;
    private String pubDate;
    private String thumbnail;
    private String userId;
    private String username;
    private String created_at;
    private String redaction_name;

    public SavePostModel(String link, String title, String description, String pubDate, String thumbnail, String userId, String username, String created_at, String redaction_name) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.thumbnail = thumbnail;
        this.userId = userId;
        this.username = username;
        this.created_at = created_at;
        this.redaction_name = redaction_name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public void setPubDate(String pubDate) {
        this.pubDate = pubDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getRedaction_name() {
        return redaction_name;
    }

    public void setRedaction_name(String redaction_name) {
        this.redaction_name = redaction_name;
    }
}


