package com.example.foody_app.utils;

import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.RestaurantModel;
import com.example.foody_app.models.LoginRegisterModel;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.models.UserModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.PUT;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APIInterface {
    //lấy dữ liệu món ăn
    @GET("/monan")
    Call<List<FoodModel>> getAllFood();

    //lấy dữ liệu món ăn theo thể loại
    @GET("/monanId/{foodId}")
    Call<FoodModel> getFoodById(@Path("foodId") String id);

    //author: Hoàng
    //thêm sửa xóa
    @GET("/listTheLoai")
    Call<List<TypeFood>> getTheLoai();
    @POST("/addFood")
    Call<Void> addFood(@Body FoodModel foodModel);

    // lấy thông tin nhà hàng
    @GET("/nhahang")
    Call<List<RestaurantModel>> getNhahang();

    // update nhà hàng
    @PUT("/updateNhahang")
    Call<Void> updateNhahang(@Body RestaurantModel restaurantModel);

    @GET("/monanType/{type}/{id}")
    Call<List<FoodModel>> getFoodByType(@Path("type") Integer type, @Path("id") Integer id);

    @POST("/login")
    Call<LoginRegisterModel> loginModelCall(@Body LoginRegisterModel loginRegisterModel);

    @POST("/register")
    Call<LoginRegisterModel> loginRegisterModelCall(@Body LoginRegisterModel loginRegisterModel);

    //lấy dữ liệu người dùng theo email
    @GET("/user/{email}")
    Call<UserModel> getUserByEmail(@Path("email") String email);
}