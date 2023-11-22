package com.example.foody_app.utils;

import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.RestaurantModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface APIInterface {
    //lấy dữ liệu món ăn
    @GET("/monan")
    Call<List<FoodModel>> getAllFood();
    //lấy dữ liệu món ăn theo thể loại
    @GET("/monanId/{foodId}")
    Call<FoodModel> getFoodById(@Path("foodId") String id);
    // lấy thông tin nhà hàng
    @GET("/nhahang")
    Call<List<RestaurantModel>> getNhahang();
    // update nhà hàng
    @PUT("/updateNhahang")
    Call<Void> updateNhahang(@Body RestaurantModel restaurantModel);
}
