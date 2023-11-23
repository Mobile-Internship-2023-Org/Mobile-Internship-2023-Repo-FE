package com.example.foody_app.models;

public class RePassModel {
    String Password;

    public RePassModel() {
    }

    public RePassModel(String password) {
        Password = password;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
