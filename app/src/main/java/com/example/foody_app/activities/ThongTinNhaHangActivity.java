package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.models.ErrorResponseModel;
import com.example.foody_app.models.RestaurantModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.google.gson.Gson;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ThongTinNhaHangActivity extends AppCompatActivity {
    APIInterface apiInterface;
    ImageView myBack,imgShop;
    private static final int PICK_IMAGE_REQUEST = 1;
    private String base64Image = null;
    TextView edName,edPhoneNumber,edFanPage,edAddress;
    Button btnUpdate;
    private boolean flag;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nha_hang);
        initView();
        setOnclickToolbar();
        setOnclickUpdateRestaurant();
        setOnclickUploadImage();
        // Initialize Retrofit
        apiInterface = APIClient.getInstance().create(APIInterface.class);


        getRestaurantInfo();


    }
    private void initView(){
        myBack = (ImageView) findViewById(R.id.myBack);
        imgShop = (ImageView) findViewById(R.id.imgShop);
        edName = (TextView) findViewById(R.id.edName);
        edPhoneNumber =  (TextView) findViewById(R.id.edPhoneNumber);
        edFanPage = (TextView)  findViewById(R.id.edFanpage);
        edAddress = (TextView)  findViewById(R.id.edAddress);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }
    private void setOnclickToolbar(){
        myBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private  void  setOnclickUpdateRestaurant(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRestaurantInfo();
            }
        });
    }
    private void setOnclickUploadImage(){
        imgShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openImageChooser();
            }
        });
    }

    /**
     *  Chọn ảnh từ thiết bị
     */
    private void openImageChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    /**
     *
     * Xữ lí kết quả khi chọn ảnh
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            Uri selectedImageUri = data.getData();
            Bitmap bitmap = decodeUri(selectedImageUri);

            if (bitmap != null) {
                imgShop.setImageBitmap(bitmap);
                base64Image = encodeBitmapToBase64(bitmap);
            }
        }
    }

    /**
     *
     * Giải mã uri thành bitmap
     */
    private Bitmap decodeUri(Uri selectedImage) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(selectedImage);
            return BitmapFactory.decodeStream(inputStream);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     *
     * Mã hóa bitmap thành base64
     */
    private String encodeBitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }

    private void getRestaurantInfo() {
        Call<List<RestaurantModel>> call = apiInterface.getNhahang();
        call.enqueue(new Callback<List<RestaurantModel>>() {
            @Override
            public void onResponse(Call<List<RestaurantModel>> call, Response<List<RestaurantModel>> response) {
                Log.d("TAG", "onResponse: " + response.toString());
                if (response.isSuccessful()) {
                    List<RestaurantModel> restaurantList = response.body();
                    // Handle the list or extract the first item if needed
                    if (!restaurantList.isEmpty()) {
                        RestaurantModel restaurant = restaurantList.get(0);
//                        flag = true;
                        // Update UI or perform other actions
                        updateUI(restaurant);
                    }
                } else {
                    Log.e("TAG", "onResponse error: " + response.errorBody().toString());
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
    /**
     * Gọi API để cập nhật thông tin nhà hàng dựa trên dữ liệu nhập từ bàn phím người dùng.
     * Nếu thành công, hiển thị thông báo và cập nhật giao diện người dùng nếu cần.
     * Nếu không thành công, xử lý phản hồi lỗi.
     */
    private void updateRestaurantInfo() {
        // Get user input from EditText or other input fields
        String phoneNumber = edPhoneNumber.getText().toString();

        // Validate phone number
        if (!isValidPhoneNumber(phoneNumber)) {
            // Show an error message or handle the invalid phone number case
            Toast.makeText(ThongTinNhaHangActivity.this, "Invalid phone number", Toast.LENGTH_SHORT).show();
            return;
        }

        // Create a RestaurantModel object with the updated information based on user input
        RestaurantModel updatedRestaurant = new RestaurantModel();
        updatedRestaurant.setTen(edName.getText().toString());
        updatedRestaurant.setSdt(phoneNumber);  // Set the validated phone number
        updatedRestaurant.setFanpage(edFanPage.getText().toString());
        updatedRestaurant.setDiaChi(edAddress.getText().toString());

        updatedRestaurant.setAnh(base64Image);
        Log.d("TAG", "Base64 Image: " + base64Image);

        // Make the API call to update the restaurant information
        Call<Void> call = apiInterface.updateNhahang(updatedRestaurant);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                Log.d("TAG", "Server response: " + response.body());
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(ThongTinNhaHangActivity.this, "Restaurant information updated successfully", Toast.LENGTH_SHORT).show();
                    getRestaurantInfo();
                } else {
                    Log.e("TAG", "onResponse: error "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
                Toast.makeText(ThongTinNhaHangActivity.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    private boolean isValidPhoneNumber(String phoneNumber) {
        return phoneNumber.matches("\\d{10}");  // Example: Check if it's a 10-digit number
    }




    private void updateUI(RestaurantModel restaurant) {
        Log.d("TAG", "updateUI: Called");
        if (restaurant != null) {
            Log.d("TAG", "updateUI: Restaurant data received - " + restaurant.toString());

            edName.setText(restaurant.getTen());
            edPhoneNumber.setText(restaurant.getSdt());
            edFanPage.setText(restaurant.getFanpage());
            edAddress.setText(restaurant.getDiaChi());

            String base64Image = restaurant.getAnh();
            if (base64Image != null && !base64Image.isEmpty()) {
                Bitmap bitmap = decodeBase64(base64Image);
                imgShop.setImageBitmap(bitmap);
            }
        }
    }
    // Helper method to decode base64 string to Bitmap
    private Bitmap decodeBase64(String base64Image) {
        byte[] decodedBytes = Base64.decode(base64Image, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(decodedBytes, 0, decodedBytes.length);
    }
}