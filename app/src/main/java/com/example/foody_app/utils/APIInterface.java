package com.example.foody_app.utils;

import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.LoginRegisterModel;
import com.example.foody_app.models.LoginRegisterModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    //lấy dữ liệu món ăn
    @GET("/monan")
    Call<List<FoodModel>> getAllFood();
    //lấy dữ liệu món ăn theo thể loại


    @GET("/monanId/{foodId}")
    Call<FoodModel> getFoodById(@Path("foodId") String id);


    @POST("/login")
    Call<LoginRegisterModel> loginModelCall(@Body LoginRegisterModel loginRegisterModel);

    @POST("/register")
    Call<LoginRegisterModel> loginRegisterModelCall (@Body LoginRegisterModel loginRegisterModel);




}
