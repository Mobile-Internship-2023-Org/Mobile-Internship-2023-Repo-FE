package com.example.foody_app.models;

import com.google.gson.annotations.SerializedName;

public class FoodModel {
    private Integer idMonAn;
    private String anh;
    private String ten;
    private Integer giaBan;
    private Integer giaGiam;
    private Integer idTheLoai;


    public FoodModel() {
    }

    public FoodModel(Integer idMonAn, String anh, String ten, Integer giaBan, Integer giaGiam, Integer idTheLoai) {
        this.idMonAn = idMonAn;
        this.anh = anh;
        this.ten = ten;
        this.giaBan = giaBan;
        this.giaGiam = giaGiam;
        this.idTheLoai = idTheLoai;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Integer getGiaGiam() {
        return giaGiam;
    }

    public void setGiaGiam(Integer giaGiam) {
        this.giaGiam = giaGiam;
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
