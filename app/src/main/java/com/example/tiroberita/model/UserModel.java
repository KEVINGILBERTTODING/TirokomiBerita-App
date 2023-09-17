package com.example.tiroberita.model;

public class UserModel {
    private String userId;
    private String username;
    private String favorite;

    public UserModel(String userId, String username, String favorite) {
        this.userId = userId;
        this.username = username;
        this.favorite = favorite;
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
}
