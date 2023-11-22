package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
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

import java.io.IOException;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThongTinNhaHangActivity extends AppCompatActivity {
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

        // Initialize Retrofit
        apiInterface = APIClient.getInstance().create(APIInterface.class);


        getRestaurantInfo();
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateRestaurantInfo();
            }
        });
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
    private void setOnclickToolbar(){
        myBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
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
        updatedRestaurant.setFanPage(edFanpage.getText().toString());
        updatedRestaurant.setDiaChi(edAddress.getText().toString());

        // Make the API call to update the restaurant information
        Call<Void> call = apiInterface.updateNhahang(updatedRestaurant);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Handle successful response
                    Toast.makeText(ThongTinNhaHangActivity.this, "Restaurant information updated successfully", Toast.LENGTH_SHORT).show();
                    // You may update UI or perform other actions if needed
                } else {
                    handleErrorResponse(response);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                handleFailure(t);
            }
        });
    }


    private void handleFailure(Throwable t) {
        Log.e("TAG", "onFailure: " + t.getMessage());

        // You can handle specific failure cases here if needed

        Toast.makeText(ThongTinNhaHangActivity.this, "Failed to update restaurant information", Toast.LENGTH_SHORT).show();
    }

    private void handleErrorResponse(Response<Void> response) {
        if (response.errorBody() != null) {
            try {
                String errorBody = response.errorBody().string();
                Log.e("TAG", "onResponse error: " + errorBody);

                // Use Gson to parse the error body if needed
                Gson gson = new Gson();
                ErrorResponseModel errorResponse = gson.fromJson(errorBody, ErrorResponseModel.class);

                // Now you can access fields in the error response model and handle accordingly
                String errorMessage = errorResponse.getMessage();

                Toast.makeText(ThongTinNhaHangActivity.this, "Failed to update restaurant information: " + errorMessage, Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(ThongTinNhaHangActivity.this, "Failed to update restaurant information", Toast.LENGTH_SHORT).show();
        }
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
            edFanpage.setText(restaurant.getFanPage());
            edAddress.setText(restaurant.getDiaChi());
        }
    }
}