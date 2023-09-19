package com.example.tiroberita.model;

public class SaveModel {
    private String link;
    private String title;
    private String description;
    private String pubDate;
    private String thumbnail;
    private String user_id;
    private String username;
    private String created_at;
    private String redaction_name;
    private String post_id;

    public SaveModel(String link, String title, String description, String pubDate, String thumbnail, String user_id, String username, String created_at, String redaction_name, String post_id) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.thumbnail = thumbnail;
        this.user_id = user_id;
        this.username = username;
        this.created_at = created_at;
        this.redaction_name = redaction_name;
        this.post_id = post_id;
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

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
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

    public String getPost_id() {
        return post_id;
    }

    public void setPost_id(String post_id) {
        this.post_id = post_id;
    }
}


