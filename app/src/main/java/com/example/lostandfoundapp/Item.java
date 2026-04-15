package com.example.lostandfoundapp;
public class Item {

    private int id;
    private String postType;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;
    private String category;
    private String image;
    private String timestamp;

    public Item(int id, String postType, String name, String phone, String description, String date, String location, String category, String image, String timestamp) {
        this.id = id;
        this.postType = postType;
        this.name = name;
        this.phone = phone;
        this.description = description;
        this.date = date;
        this.location = location;
        this.category = category;
        this.image = image;
        this.timestamp = timestamp;
    }

    public int getId() {
        return id;
    }
    public String getPostType() {
        return postType;
    }
    public String getName() {
        return name;
    }
    public String getPhone() {
        return phone;
    }
    public String getDescription() {
        return description;
    }
    public String getDate() {
        return date;
    }
    public String getLocation() {
        return  location;
    }
    public String getCategory() {
        return category;
    }
    public String getImage() {
        return image;
    }
    public String getTimestamp() {
        return timestamp;
    }
}
