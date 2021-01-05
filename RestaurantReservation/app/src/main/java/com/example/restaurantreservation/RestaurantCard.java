package com.example.restaurantreservation;

public class RestaurantCard {
   // RestaurantCard for the Home page
    private String title;
    private String adress;
    private int thumbnail;

    public RestaurantCard(String title, String adress, int thumbnail) {
        this.title = title;
        this.adress = adress;
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

    public String getAdress() {
        return adress;
    }

    public void setAdress(String adress) {
        this.adress = adress;
    }

    public int getThumbnail() {
        return thumbnail;
    }

    public void setThumbnail(int thumbnail) {
        this.thumbnail = thumbnail;
    }

}
