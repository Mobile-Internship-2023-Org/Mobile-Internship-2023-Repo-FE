package com.example.foody_app.utils;

import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.InforModel;
import com.example.foody_app.models.LichSuModel;
import com.example.foody_app.models.MonAnModel;
import com.example.foody_app.models.RatingModel;
import com.example.foody_app.models.RatingModel2;
import com.example.foody_app.models.RePassModel;
import com.example.foody_app.models.RestaurantModel;
import com.example.foody_app.models.LoginRegisterModel;
import com.example.foody_app.models.ShoppingCartItem;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.models.ShoppingCartModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.models.updateResponse;
import com.google.gson.JsonObject;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Multipart;
import retrofit2.http.PUT;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.Url;

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

    // thêm món ăn
    @Multipart
    @POST("/addFood")
    Call<FoodModel> addFood(
            @Part MultipartBody.Part anh,
            @Part("ten") RequestBody ten,
            @Part("giaBan") RequestBody giaBan,
            @Part("giaGiam") RequestBody giaGiam,
            @Part("idTheLoai") RequestBody idTheLoai
    );
    // Tìm kiếm tên món ăn
    @GET("/search")
    Call<List<FoodModel>> getFoodByNameRegex(@Query("ten") String searchTerm);
    @Multipart
    @PUT("/updateFood/{idMonAn}")
    Call<updateResponse> updateFood(
            @Path("idMonAn") int idMonAn,
            @Part MultipartBody.Part anh,
            @Part("ten") RequestBody ten,
            @Part("giaBan") RequestBody giaBan,
            @Part("giaGiam") RequestBody giaGiam,
            @Part("idTheLoai") RequestBody idTheLoai
    );
    // xóa món ăn(ẩn)
    @PUT("/deleteFood/{idMonAn}")
    Call<updateResponse> deleteFood(@Path("idMonAn") int idMonAn);

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

    @GET("/getInfor/{email}")
    Call<List<InforModel>> getInfor(@Path("email")String email);

    @POST("/addToCart")
    Call<Void> addToCart(@Body ShoppingCartModel model);
    //lấy đánh giá của người dùng
    @GET("/rating")
    Call<List<RatingModel>> getRating();

//    @PUT("/updatePassword/{userId}")
//    Call<RePassModel> rePassModelCall(@Path("userId") RePassModel userId);

    // == Hóa đơn ==
    // Get nguoidung info by email
    @GET("/hoadon/getNguoiDungByEmail/{email}")
    Call<UserModel> getNguoidungByEmail(@Path("email") String email);

    // API to update 'hoTen' and 'sdt' of a nguoidung using email
    @PUT("/hoadon/updateNguoidungInfo")
    Call<Void> updateNguoidungInfo(@Body RequestBody requestBody);

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
    @PUT("/updatePassword")
   Call<RePassModel> rePassModelCall(@Body RePassModel rePassModel);
    //đổi thông tin người dùng
    @POST("/user/{email}")
    Call<Void> updateInfoUser(@Path("email")String email,@Body UserModel userModel);
    @GET("/hoadon/getHoadonList/{email}")
    Call<List<LichSuModel>> getHoaDon(@Path("email")String email);
    @GET("hoadon/getHoadonById/{idHoaDon}")
    Call<LichSuModel> getHoaDonById(@Path("idHoaDon")int id);
    @GET("/hoadon/monan/{idGioHang}")
    Call<List<MonAnModel>> getListMonAn(@Path("idGioHang") int idGioHang);
    @PUT("/hoadon/updateTrangThai/{idHoaDon}/{trangThai}")
    Call<Void> updateTrangThaiHoaDon(@Path("idHoaDon") int idHoaDon, @Path("trangThai") int trangthai);
    @GET("/hoadon/getHoaDonListAll")
    Call<List<LichSuModel>> getHoaDon();

    @GET("/getAllReviews")
    Call<List<RatingModel2>> getAllReviews();

    @GET("/getReviewById/{id}")
    Call<RatingModel2> getReviewById(@Path("id") int id);

    @POST("/addReview")
    Call<RatingModel2> addReview(@Body RatingModel2 ratingModel);

    @PUT("/updateReview/{id}")
    Call<RatingModel2> updateReview(@Path("id") int id, @Body RatingModel2 ratingModel);

    @DELETE("/deleteReview/{id}")
    Call<Void> deleteReview(@Path("id") int id);
}
