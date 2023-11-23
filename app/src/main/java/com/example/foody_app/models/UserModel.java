package com.example.foody_app.models;

import kotlin.jvm.internal.PropertyReference0Impl;

public class UserModel {
    private String email;
    private String matKhau;
    private String hoTen;
    private String sdt;
    private String anh;
    private String diaChi;
    private String role;

    public UserModel() {
    }

    public UserModel(String email, String matKhau, String hoten, String sdt, String anh, String diaChi, String role) {
        this.email = email;
        this.matKhau = matKhau;
        this.hoTen = hoten;
        this.sdt = sdt;
        this.anh = anh;
        this.diaChi = diaChi;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getMatKhau() {
        return matKhau;
    }

    public void setMatKhau(String matKhau) {
        this.matKhau = matKhau;
    }

    public String getHoten() {
        return hoTen;
    }

    public void setHoten(String hoten) {
        this.hoTen = hoten;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
