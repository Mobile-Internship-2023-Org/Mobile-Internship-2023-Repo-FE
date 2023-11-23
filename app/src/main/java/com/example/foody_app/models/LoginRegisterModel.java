package com.example.foody_app.models;

public class LoginRegisterModel {
    private String Email;
    private  String Password;

    public LoginRegisterModel() {
    }

    public LoginRegisterModel(String email, String password) {
        Email = email;
        Password = password;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }
}
