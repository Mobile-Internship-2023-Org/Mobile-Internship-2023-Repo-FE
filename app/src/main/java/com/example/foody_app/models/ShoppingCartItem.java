package com.example.foody_app.models;

public class ShoppingCartItem {
    private int idMonAn;
    private String anh;
    private String ten;
    private int giaGoc;
    private int giaBan;
    private Integer giaGiam;
    private int idTheLoai;
    private int soLuong;

    public ShoppingCartItem() {
    }

    public ShoppingCartItem(int idMonAn, String anh, String ten, int giaGoc, int giaBan, Integer giaGiam, int idTheLoai, int soLuong) {
        this.idMonAn = idMonAn;
        this.anh = anh;
        this.ten = ten;
        this.giaGoc = giaGoc;
        this.giaBan = giaBan;
        this.giaGiam = giaGiam;
        this.idTheLoai = idTheLoai;
        this.soLuong = soLuong;
    }

    public int getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(int idMonAn) {
        this.idMonAn = idMonAn;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getTen() {
        return ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getGiaGoc() {
        return giaGoc;
    }

    public void setGiaGoc(int giaGoc) {
        this.giaGoc = giaGoc;
    }

    public int getGiaBan() {
        return giaBan;
    }

    public void setGiaBan(int giaBan) {
        this.giaBan = giaBan;
    }

    public Integer getGiaGiam() {
        return giaGiam;
    }

    public void setGiaGiam(Integer giaGiam) {
        this.giaGiam = giaGiam;
    }

    public int getIdTheLoai() {
        return idTheLoai;
    }

    public void setIdTheLoai(int idTheLoai) {
        this.idTheLoai = idTheLoai;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
