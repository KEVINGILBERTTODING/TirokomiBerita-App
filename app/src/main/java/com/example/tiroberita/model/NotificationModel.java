package com.example.tiroberita.model;

public class NotificationModel {
    private String created_at;
    private String description;
    private String image;
    private String notif_id;
    private String title;

    public NotificationModel(){

    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getNotif_id() {
        return notif_id;
    }

    public void setNotif_id(String notif_id) {
        this.notif_id = notif_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
