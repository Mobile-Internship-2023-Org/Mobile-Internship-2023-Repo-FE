package com.example.foody_app.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.foody_app.R;
import com.example.foody_app.models.ErrorResponseModel;
import com.example.foody_app.models.RestaurantModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinNhaHangActivity extends AppCompatActivity {
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;

    APIInterface apiInterface;
    ImageView myBack,imgShop;
    TextView edName,edPhoneNumber,edFanpage,edAddress;
    Button btnUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nha_hang);
        initView();
        setOnclickToolbar();
        setOnclickUpdate();
        // Initialize Retrofit
        apiInterface = APIClient.getInstance().create(APIInterface.class);
        getRestaurantInfo();
        pickImageFromGallery();
        DangNhapActivity activity = new DangNhapActivity();
        getUserData(activity.readEmailLocally(this));
    }
    private void initView(){
        myBack = (ImageView) findViewById(R.id.myBack);
        imgShop = (ImageView) findViewById(R.id.imgShop);
        edName = (TextView) findViewById(R.id.edName);
        edPhoneNumber =  (TextView) findViewById(R.id.edPhoneNumber);
        edFanpage = (TextView)  findViewById(R.id.edFanpage);
        edAddress = (TextView)  findViewById(R.id.edAddress);
        btnUpdate = (Button) findViewById(R.id.btnUpdate);
    }
    private void pickImageFromGallery() {
        imgShop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
            }
        });
    }
    private void setOnclickUpdate(){
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRestaurantInfo();
            }
        });
    }
    private void setOnclickToolbar(){
        myBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            // Lấy đường dẫn của ảnh được chọn
            selectedImageUri  = Uri.parse(data.getData().toString());

            // Hiển thị ảnh đã chọn trên ImageView
            imgShop.setImageURI(data.getData());
        }
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
                        // Update UI or perform other actions
                        updateUI(restaurant);
                        // Load and display the image from Firebase Storage
                        loadFirebaseImage(restaurant.getAnh());
                    }
                } else {
                    Log.e("TAG", "onResponse error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<RestaurantModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
    // Add the following method to load and display the image using Glide
    private void loadFirebaseImage(String imageUrl) {
        if (imageUrl != null && !imageUrl.isEmpty()) {
            Glide.with(this)
                    .load(imageUrl)
                    .into(imgShop);
        }
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
        updatedRestaurant.setFanpage(edFanpage.getText().toString());
        updatedRestaurant.setDiaChi(edAddress.getText().toString());

        // Check if an image is selected
        if (selectedImageUri != null) {
            // Upload image to Firebase Storage
            uploadImageToFirebase(updatedRestaurant);
        } else {
            // If no image is selected, directly make the API call to update the restaurant information
            updateRestaurantInfoWithoutImage(updatedRestaurant);
        }
    }

    private void uploadImageToFirebase(final RestaurantModel restaurant) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference().child("images");

        // Create a unique file name (e.g., timestamp to ensure uniqueness)
        String fileName = "image_" + System.currentTimeMillis() + ".jpg";

        StorageReference imageRef = storageRef.child(fileName);

        imageRef.putFile(selectedImageUri)
                .addOnSuccessListener(taskSnapshot -> {
                    // Get the download URL of the uploaded image
                    imageRef.getDownloadUrl().addOnSuccessListener(uri -> {
                        // Update the image URL in the RestaurantModel object
                        restaurant.setAnh(uri.toString());

                        // Continue to update restaurant information with the image
                        updateRestaurantInfoWithImage(restaurant);
                    });
                })
                .addOnFailureListener(e -> {
                    // Handle the case when image upload fails
                    Toast.makeText(ThongTinNhaHangActivity.this, "Failed to upload image", Toast.LENGTH_SHORT).show();
                });
    }

    private void updateRestaurantInfoWithImage(RestaurantModel restaurant) {
        // Make the API call to update the restaurant information, including the image URL
        Call<Void> call = apiInterface.updateNhahang(restaurant);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(ThongTinNhaHangActivity.this, "Restaurant information updated successfully", Toast.LENGTH_SHORT).show();
                    // You may update UI or perform other actions if needed
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ThongTinNhaHangActivity.this, "Failed to update restaurant information", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void updateRestaurantInfoWithoutImage(RestaurantModel restaurant) {
        // Make the API call to update the restaurant information without the image
        Call<Void> call = apiInterface.updateNhahang(restaurant);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(ThongTinNhaHangActivity.this, "Restaurant information updated successfully", Toast.LENGTH_SHORT).show();
                    // You may update UI or perform other actions if needed
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Toast.makeText(ThongTinNhaHangActivity.this, "Failed to update restaurant information", Toast.LENGTH_SHORT).show();
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
            edFanpage.setText(restaurant.getFanpage());
            edAddress.setText(restaurant.getDiaChi());
        }
    }

    /**
     *
     * check role
     */
    private void getUserData(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    assert userModel != null;
                    Log.e("TAG", "onResponse: "+userModel.getHoTen());
                    if(userModel.getRole().equals("user")){
                        imgShop.setOnClickListener(null);
                        edName.setEnabled(false);
                        edAddress.setEnabled(false);
                        edPhoneNumber.setEnabled(false);
                        edFanpage.setEnabled(false);
                        btnUpdate.setVisibility(View.GONE);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {

            }
        });
    }

}