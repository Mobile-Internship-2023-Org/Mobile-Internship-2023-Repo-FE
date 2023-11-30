package com.example.foody_app.utils;

import com.example.foody_app.adapter.GioHangAdapter;
import com.example.foody_app.fragments.GioHangFragment;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.InforModel;
import com.example.foody_app.models.RatingModel;
import com.example.foody_app.models.RestaurantModel;
import com.example.foody_app.models.LoginRegisterModel;
import com.example.foody_app.models.ShoppingCartItem;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.models.ShoppingCartModel;
import com.example.foody_app.models.UserModel;
import com.google.gson.JsonObject;

import org.json.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.PUT;
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

    @GET("/getInfor")
    Call<List<InforModel>> getInfor();

    @POST("/addToCart")
    Call<Void> addToCart(@Body ShoppingCartModel model);
    //lấy đánh giá của người dùng
    @GET("/rating")
    Call<List<RatingModel>> getRating();

    // == Hóa đơn ==
    // Get nguoidung info by email
    @GET("/hoadon/getNguoiDungByEmail/{email}")
    Call<UserModel> getNguoidungByEmail(@Path("email") String email);

    @GET("/hoadon/getIdGioHang/{idNguoiDung}")
    Call<JsonObject> getIdGioHang(@Path("idNguoiDung") int idNguoiDung);

    @GET("/hoadon/getMonanByNguoiDung/{idNguoiDung}")
    Call<List<ShoppingCartItem>> getMonanByNguoiDung(@Path("idNguoiDung") Integer idNguoiDung);

    @GET("/hoadon/tongTienHoaDon/{idNguoiDung}")
    Call<JsonObject> getTongTienHoaDon(@Path("idNguoiDung") int idNguoiDung);

    @POST("/hoadon/createHoaDon/{idNguoiDung}")
    @FormUrlEncoded
    Call<Void> createHoaDon(
            @Path("idNguoiDung") int idNguoiDung,
            @Field("diaChi") String diaChi,
            @Field("tongTienHoaDon") int tongTienHoaDon,
            @Field("comment") String comment,
            @Field("idGioHang") int idGioHang,
            @Field("phuongThucTT") String phuongThucTT
    );

    @PUT("/hoadon/completeGioHang/{idGioHang}")
    Call<Void> completeGioHang(@Path("idGioHang") int idGioHang);
}
