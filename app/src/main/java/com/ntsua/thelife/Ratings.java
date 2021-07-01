package com.ntsua.thelife;

import android.net.Uri;

import java.util.Comparator;

public class Ratings {
    private String Name;
    private int Money;
    private String PhotoUri;

    public Ratings(String name, int money, String photoUri) {
        Name = name;
        Money = money;
        PhotoUri = photoUri;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public int getMoney() {
        return Money;
    }

    public void setMoney(int money) {
        Money = money;
    }

    public String getPhotoUri() {
        return PhotoUri;
    }

    public void setPhotoUri(String photoUri) {
        PhotoUri = photoUri;
    }

    public static Comparator<Ratings> RatingComparator = new Comparator<Ratings>() {

        public int compare(Ratings s1, Ratings s2) {

            if (s1.getMoney() < s2.getMoney())
                return 1;
            else if (s1.getMoney() > s2.getMoney())
                return -1;
            return 0;
        }
    };
}
