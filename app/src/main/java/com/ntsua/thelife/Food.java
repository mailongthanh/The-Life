package com.ntsua.thelife;

public class Food {
    private String foodName;
    private String description;
    private int image;

    public Food(String foodName, String description, int image) {
        this.foodName = foodName;
        this.description = description;
        this.image = image;
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
}
