package com.example.individualproject.models;

public class ShowAllModel {
    String description;
    String name;
    String rating;
    String image_url;
    String type;
    int price;

    public ShowAllModel() {
    }

    public ShowAllModel(String description, String name, String rating, String image_url, String type, int price) {
        this.description = description;
        this.name = name;
        this.rating = rating;
        this.image_url = image_url;
        this.type = type;
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public String getImage_url() {
        return image_url;
    }

    public void setImage_url(String image_url) {
        this.image_url = image_url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
