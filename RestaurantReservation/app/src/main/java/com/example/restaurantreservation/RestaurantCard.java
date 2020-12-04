package com.example.restaurantreservation;

public class RestaurantCard {

    private String title;
    private String category;
    private int thumbnail;

    public RestaurantCard(String title, String category, int thumbnail) {
        this.title = title;
        this.category = category;
        this.thumbnail = thumbnail;
    }


    public RestaurantCard() {
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
