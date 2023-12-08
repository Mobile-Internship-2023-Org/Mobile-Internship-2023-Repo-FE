package com.example.foody_app.models;

public class RatingModel2 {

    private int idDanhGia;

    private float soSao;

    private String moTa;

    private int idNguoiDung;

    public RatingModel2() {
    }

    public RatingModel2(int idDanhGia, float soSao, String moTa, int idNguoiDung) {
        this.idDanhGia = idDanhGia;
        this.soSao = soSao;
        this.moTa = moTa;
        this.idNguoiDung = idNguoiDung;
    }

    public int getIdDanhGia() {
        return idDanhGia;
    }

    public void setIdDanhGia(int idDanhGia) {
        this.idDanhGia = idDanhGia;
    }

    public float getSoSao() {
        return soSao;
    }

    public void setSoSao(float soSao) {
        this.soSao = soSao;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public int getIdNguoiDung() {
        return idNguoiDung;
    }

    public void setIdNguoiDung(int idNguoiDung) {
        this.idNguoiDung = idNguoiDung;
    }
}
