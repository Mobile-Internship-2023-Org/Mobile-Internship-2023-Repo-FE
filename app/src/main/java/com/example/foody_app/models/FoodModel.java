package com.example.foody_app.models;

import com.google.gson.annotations.SerializedName;

public class FoodModel {
    private Integer idMonAn;
    private String ten;
    private Integer giaBan;
    private Integer idTheLoai;

    public FoodModel() {
    }

    public FoodModel(Integer idMonAn, String ten, Integer giaBan, Integer idTheLoai) {
        this.idMonAn = idMonAn;
        this.ten = ten;
        this.giaBan = giaBan;
        this.idTheLoai = idTheLoai;
    }

    public Integer getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(Integer idMonAn) {
        this.idMonAn = idMonAn;
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

    public Integer getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(Integer idTheLoai) {
        this.idTheLoai = idTheLoai;
    }
}
