package com.example.duan1.Model;

public class Recommended {
    private String resourceId;
    private String title;

    public Recommended(String resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Recommended{" +
                "resourceId='" + resourceId + '\'' +
                ", title='" + title + '\'' +
                '}';
    }
}
