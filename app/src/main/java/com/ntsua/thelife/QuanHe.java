package com.ntsua.thelife;

import android.widget.ImageView;

import java.io.Serializable;

public class QuanHe implements Serializable {
    private String Hoten;
    private int Tuoi;
    private int DoThanMat;
    private String QuanHe;
    public int HinhAnh;

    public void setHinhAnh(int hinhAnh) { HinhAnh = hinhAnh; }

    public int getHinhAnh() {  return HinhAnh; }

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

    public QuanHe(String hoten, int tuoi, int doThanMat, String quanhe, int hinhAnh) {
        Hoten = hoten;
        Tuoi = tuoi;
        DoThanMat = doThanMat;
        QuanHe = quanhe;
        HinhAnh = hinhAnh;
    }
}
