package com.example.foody_app.models;

public class FoodModel {
    private Integer idMonAn;
    private String ten;
    private Integer giaBan;

    public FoodModel() {
    }

    public FoodModel(Integer idMonAn, String ten, Integer giaBan) {
        this.idMonAn = idMonAn;
        this.ten = ten;
        this.giaBan = giaBan;
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
}
