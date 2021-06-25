package com.ntsua.thelife;

public class University {
    private int image;
    private String name;
    private int score;
    private int require;

    public University(int image, String name, int score, int require) {
        this.image = image;
        this.name = name;
        this.score = score;
        this.require = require;
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

    public int getRequire() {
        return require;
    }

    public void setRequire(int require) {
        this.require = require;
    }
}
