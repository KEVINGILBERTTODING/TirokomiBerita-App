package com.example.tiroberita.model;

public class PostModel {
    private String link;
    private String title;
    private String description;
    private String pubDate;
    private String thumbnail;

    public PostModel(String link, String title, String description, String pubDate, String thumbnail) {
        this.link = link;
        this.title = title;
        this.description = description;
        this.pubDate = pubDate;
        this.thumbnail = thumbnail;
    }

    public String getLink() {
        return link;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPubDate() {
        return pubDate;
    }

    public String getThumbnail() {
        return thumbnail;
    }
}
