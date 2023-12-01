package com.example.foody_app.models;

import com.google.gson.annotations.SerializedName;

public class updateResponse {
    @SerializedName("status")
    private String status;

    // Các trường dữ liệu phản hồi khác nếu cần

    public String getStatus() {
        return status;
    }
}
