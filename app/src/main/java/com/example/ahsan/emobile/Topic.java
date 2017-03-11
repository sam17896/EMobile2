package com.example.ahsan.emobile;



public class Topic {

    private String title;
    private String description;
    private String adminId;
    private String id;

    public Topic(String title, String description, String adminId, String id){
        this.title = title;
        this.description = description;
        this.adminId = adminId;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getAdminId() {
        return adminId;
    }

    public void setAdminId(String adminId) {
        this.adminId = adminId;
    }
}
