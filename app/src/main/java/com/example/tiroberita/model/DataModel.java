package com.example.tiroberita.model;

import java.util.List;

public class DataModel {
    private String link;
    private String image;
    private String description;
    private String title;
    private List<PostModel> postModelList;

    public DataModel(String link, String image, String description, String title, List<PostModel> postModelList) {
        this.link = link;
        this.image = image;
        this.description = description;
        this.title = title;
        this.postModelList = postModelList;
    }

    public String getLink() {
        return link;
    }

    public String getImage() {
        return image;
    }

    public String getDescription() {
        return description;
    }

    public String getTitle() {
        return title;
    }

    public List<PostModel> getPostModelList() {
        return postModelList;
    }
}
