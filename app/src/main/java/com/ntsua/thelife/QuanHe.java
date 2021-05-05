package com.ntsua.thelife;

import android.widget.ImageView;

public class QuanHe {
    private String Hoten;
    private int Tuoi;
    private int DoThanMat;
    private String QuanHe;
    //public int HinhAnh;

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public int getTuoi() {
        return Tuoi;
    }

    public void setTuoi(int tuoi) {
        Tuoi = tuoi;
    }

    public int getDoThanMat() {
        return DoThanMat;
    }

    public void setDoThanMat(int doThanMat) {
        DoThanMat = doThanMat;
    }

    public String getQuanHe() {
        return QuanHe;
    }

    public void setQuanHe(String quanHe) {
        QuanHe = quanHe;
    }

    public QuanHe(String hoten, int tuoi, int doThanMat, String quanhe) {
        Hoten = hoten;
        Tuoi = tuoi;
        DoThanMat = doThanMat;
        QuanHe = quanhe;
        //HinhAnh = hinhAnh;
    }
}
