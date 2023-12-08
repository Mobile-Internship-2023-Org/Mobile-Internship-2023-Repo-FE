package com.example.foody_app.models;

public class MonAnModel {
    private String ten;
    private int giaBan;
    private int soLuong;

    public MonAnModel(String ten, int giaBan, int soLuong) {
        this.ten = ten;
        this.giaBan = giaBan;
        this.soLuong = soLuong;
    }

    public MonAnModel() {
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
