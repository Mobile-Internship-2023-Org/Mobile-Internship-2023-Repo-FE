package com.example.foody_app.models;

public class LichSuModel {
    private int idMonAn;
    private String anh;
    private String tenMonAn;
    private String diaChi;
    private String loai;
    private float gia;
    private int soLuong;
    private int trangThai;

    public LichSuModel(int idMonAn, String anh, String tenMonAn, String diaChi, String loai, float gia, int soLuong, int trangThai) {
        this.idMonAn = idMonAn;
        this.anh = anh;
        this.tenMonAn = tenMonAn;
        this.diaChi = diaChi;
        this.loai = loai;
        this.gia = gia;
        this.soLuong = soLuong;
        this.trangThai = trangThai;
    }

    public LichSuModel() {
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

    public String getTenMonAn() {
        return tenMonAn;
    }

    public void setTenMonAn(String tenMonAn) {
        this.tenMonAn = tenMonAn;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public float getGia() {
        return gia;
    }

    public void setGia(float gia) {
        this.gia = gia;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(int trangThai) {
        this.trangThai = trangThai;
    }
}
