package com.example.foody_app.models;
public class RestaurantModel {
    private String anh;
    private String ten;
    private String sdt;
    private String fanpage;
    private String diaChi;

    public RestaurantModel() {
    }

    public RestaurantModel(String anh, String ten, String sdt, String fanpage, String diaChi) {
        this.anh = anh;
        this.ten = ten;
        this.sdt = sdt;
        this.fanpage = fanpage;
        this.diaChi = diaChi;
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

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getFanpage() {
        return fanpage;
    }

    public void setFanpage(String fanpage) {
        this.fanpage = fanpage;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
