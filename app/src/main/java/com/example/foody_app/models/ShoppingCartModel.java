package com.example.foody_app.models;

public class ShoppingCartModel {
    private Integer soLuong;
    private Integer idMonAn;
    private Integer idNguoiDung;
    private Integer trangThai;

    public ShoppingCartModel() {
    }

    public Integer getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Integer soLuong) {
        this.soLuong = soLuong;
    }

    public Integer getIdMonAn() {
        return idMonAn;
    }

    public void setIdMonAn(Integer idMonAn) {
        this.idMonAn = idMonAn;
    }

    public Integer getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(Integer idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }

    public Integer getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(Integer trangThai) {
        this.trangThai = trangThai;
    }
}
