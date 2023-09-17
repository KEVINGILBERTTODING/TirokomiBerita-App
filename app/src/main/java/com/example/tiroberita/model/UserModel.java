package com.example.tiroberita.model;

public class UserModel {
    private String userId;
    private String username;
    private String favorite;
    private String created_at;

    public UserModel(String userId, String username, String favorite, String createdAt) {
        this.userId = userId;
        this.username = username;
        this.favorite = favorite;
        this.created_at = createdAt;
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

    public String getFavorite() {
        return favorite;
    }

    public void setFavorite(String favorite) {
        this.favorite = favorite;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
