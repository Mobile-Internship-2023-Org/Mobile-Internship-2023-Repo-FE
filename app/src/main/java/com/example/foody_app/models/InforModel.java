package com.example.foody_app.models;

import android.widget.ImageView;

public class InforModel {
    private Integer soluong;
    private ImageView anh;
    private String ten;
    private Integer gia;



    public InforModel(Integer soluong, ImageView anh, String ten, Integer gia) {
        this.soluong = soluong;
        this.anh = anh;
        this.ten = ten;
        this.gia = gia;
    }

    public Integer getSoLuong() {
        return soluong;
    }

    public void setSoLuong(Integer soluong) {this.soluong = soluong;}

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

    public Integer getGia() {
        return gia;
    }

    public void setIdTheLoai(Integer gia) {
        this.gia = gia;
    }
}
