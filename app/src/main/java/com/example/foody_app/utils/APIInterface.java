package com.example.foody_app.utils;

import com.example.foody_app.models.RePassModel;

import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.InforModel;
import com.example.foody_app.models.RatingModel;
import com.example.foody_app.models.RestaurantModel;
import com.example.foody_app.models.LoginRegisterModel;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.models.ShoppingCartModel;
import com.example.foody_app.models.UserModel;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
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
    @GET("/listTypeFood")
    Call<Map<String, List<TypeFood>>> getTheLoai();

    @Multipart
    @POST("/addFood")
    Call<FoodModel> addFood(
            @Part MultipartBody.Part anh,
            @Part("ten") RequestBody ten,
            @Part("giaBan") RequestBody giaBan,
            @Part("giaGiam") RequestBody giaGiam,
            @Part("idTheLoai") RequestBody idTheLoai
    );

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

    @GET("/getInfor")
    Call<List<InforModel>> getInfor();

    @POST("/addToCart")
    Call<Void> addToCart(@Body ShoppingCartModel model);
    //lấy đánh giá của người dùng
    @GET("/rating")
    Call<List<RatingModel>> getRating();

    @POST("/RePassController/{userId}")
    Call<RePassModel> rePassModelCall(@Path("userId") RePassModel userId);
}
