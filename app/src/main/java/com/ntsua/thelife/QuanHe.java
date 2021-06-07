package com.ntsua.thelife;

import android.widget.ImageView;

import java.io.Serializable;

public class QuanHe implements Serializable {
    private String Hoten;
    private int Tuoi;
    private int DoThanMat;
    private NameOfRelationship QuanHe;
    public int HinhAnh;
    private boolean isBoy;

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

    public NameOfRelationship getQuanHe() {
        return QuanHe;
    }

    public void setQuanHe(NameOfRelationship quanHe) {
        QuanHe = quanHe;
    }

    public boolean isBoy() {
        return isBoy;
    }

    public void setBoy(boolean boy) {
        isBoy = boy;
    }

    public QuanHe(String hoten, int tuoi, int doThanMat, NameOfRelationship quanhe, int hinhAnh, boolean isBoy) {
        Hoten = hoten;
        Tuoi = tuoi;
        DoThanMat = doThanMat;
        QuanHe = quanhe;
        HinhAnh = hinhAnh;
        this.isBoy = isBoy;
    }
}
