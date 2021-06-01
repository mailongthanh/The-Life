package com.ntsua.thelife;

public class University {
    private int image;
    private String name;
    private int score;

    public University(int image, String name, int score) {
        this.image = image;
        this.name = name;
        this.score = score;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }
}
