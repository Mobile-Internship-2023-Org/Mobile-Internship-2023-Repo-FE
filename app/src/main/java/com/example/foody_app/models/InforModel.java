package com.example.foody_app.models;

import android.widget.ImageView;

public class InforModel {
    private Integer soLuong;
    private ImageView anh;
    private String ten;
    private Integer giaBan;

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public ImageView getAnh() {
        return anh;
    }

    public void setAnh(ImageView anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public Integer getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(Integer giaBan) {
        this.giaBan = giaBan;
    }
}
