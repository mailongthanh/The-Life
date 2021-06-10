package com.ntsua.thelife;

public class Sick {
    private boolean isSick;
    private String SickName;

    public Sick(boolean isSick, String sickName) {
        this.isSick = isSick;
        SickName = sickName;
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
}
