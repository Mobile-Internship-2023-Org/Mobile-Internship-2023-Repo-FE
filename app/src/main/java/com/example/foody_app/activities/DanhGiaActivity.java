package com.example.foody_app.activities;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.foody_app.R;
import com.example.foody_app.models.RatingModel2;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class DanhGiaActivity extends AppCompatActivity {

    private RatingBar ratingBar;
    private EditText commentEditText;
    private Button submitButton;
    private TextView btnSkip;

    private APIInterface apiInterface;
    private int idNguoiDung;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_gia);
        apiInterface = APIClient.getInstance().create(APIInterface.class);

        initView();

    }
    private void initView(){
        ratingBar = findViewById(R.id.ratingBar);
        commentEditText = findViewById(R.id.edComment);
        submitButton = findViewById(R.id.btnSendRating);
        btnSkip = findViewById(R.id.tvSkip);
        setClickListeners();
    }
    private void setClickListeners() {
        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Call the addReview method with the obtained idNguoiDung
                addReview(idNguoiDung);
            }
        });

        // Add any other click listeners as needed
    }

    private void getUserData(String email) {
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserModel userModel = response.body();
                    idNguoiDung = userModel.getIdNguoiDung();

                    // Now you can use the idNguoiDung for further processing
                    Log.e("TAG", "User ID: " + idNguoiDung);

                    // Use the idNguoiDung for adding a review
                    // Optionally, you can set UI elements or perform other actions here
                } else {
                    // Handle unsuccessful response
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void addReview(int idNguoiDung) {
        // Get the values from UI components
        float ratingValue = ratingBar.getRating();
        String reviewText = commentEditText.getText().toString();

        // Create a RatingModel object with the review details
        RatingModel2 ratingModel = new RatingModel2();
        ratingModel.setSoSao(ratingValue); // Use the provided rating value
        ratingModel.setMoTa(reviewText); // Use the provided review text
        ratingModel.setIdNguoiDung(idNguoiDung); // Use the provided idNguoiDung

        // Make the Retrofit API call for adding a review
        Call<RatingModel2> addReviewCall = apiInterface.addReview(ratingModel);
        addReviewCall.enqueue(new Callback<RatingModel2>() {
            @Override
            public void onResponse(Call<RatingModel2> call, Response<RatingModel2> response) {
                if (response.isSuccessful()) {
                    RatingModel2 addedReview = response.body();
                    Toast.makeText(DanhGiaActivity.this, "Review added successfully", Toast.LENGTH_SHORT).show();
                    // Handle successful review addition, if needed
                } else {
                    Toast.makeText(DanhGiaActivity.this, "Failed to add review", Toast.LENGTH_SHORT).show();
                    // Handle unsuccessful response


                }
            }

            @Override
            public void onFailure(Call<RatingModel2> call, Throwable t) {
                Toast.makeText(DanhGiaActivity.this, "Failed to add review", Toast.LENGTH_SHORT).show();
                // Handle failure
                Log.d("TAG","Failed",t);

            }
        });
    }

}