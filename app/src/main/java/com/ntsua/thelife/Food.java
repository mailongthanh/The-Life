package com.ntsua.thelife;

public class Food {
    private String foodName;
    private String description;
    private int image;
    private int price;

    public Food(String foodName, String description, int image, int price) {
        this.foodName = foodName;
        this.description = description;
        this.image = image;
        this.price = price;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }
}
