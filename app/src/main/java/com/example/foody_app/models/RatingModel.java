package com.example.foody_app.models;

public class RatingModel {
    private int id;
    private String hoTen;
    private String anh;
    private String email;
    private String moTa;
    private int soSao;

    public RatingModel() {
    }

    public String getHoTen() {
        return hoTen;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setEmail(String email){
        this.email = email;
    }
    public String getEmail(){
        return  email;
    }
    public void setSoSao(int soSao){
        this.soSao = soSao;
    }
    public int getSoSao(){
        return soSao;
    }
    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }
    public String getMoTa() {
        return moTa;
    }
}
