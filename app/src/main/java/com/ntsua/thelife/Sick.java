package com.ntsua.thelife;

public class Sick {
    private boolean isSick;
    private String SickName;
    private String docter;
    private String json;

    public Sick(boolean isSick, String sickName, String docter, String json) {
        this.isSick = isSick;
        SickName = sickName;
        this.docter = docter;
        this.json = json;
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
}
