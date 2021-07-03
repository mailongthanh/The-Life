package com.ntsua.thelife;

public class Sick {
    private boolean isSick;
    private String SickName;
    private String docter;
    private String json;
    private int health; //Sức khỏe bị trừ mỗi năm nếu có bệnh mà không chữa

    public Sick() {
    }

    public Sick(boolean isSick, String sickName, String docter, String json, int health) {
        this.isSick = isSick;
        SickName = sickName;
        this.docter = docter;
        this.json = json;
        this.health = health;
    }

    public boolean isSick() {
        return isSick;
    }

    public void setSick(boolean sick) {
        isSick = sick;
    }

    public String getSickName() {
        return SickName;
    }

    public void setSickName(String sickName) {
        SickName = sickName;
    }

    public String getDocter() {
        return docter;
    }

    public void setDocter(String docter) {
        this.docter = docter;
    }

    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }
}
