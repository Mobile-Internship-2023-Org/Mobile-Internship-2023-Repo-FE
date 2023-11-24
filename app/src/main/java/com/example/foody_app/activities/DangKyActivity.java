package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.MainActivity;
import com.example.foody_app.R;
import com.example.foody_app.models.LoginRegisterModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {

    private TextView txtSignIn;
    private EditText edtUsername, edtEmail,edtPassword,edtRePassword;
    private Button btnRegister;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_ky);

//        edtUsername = findViewById(R.id.edtUsername);
        edtPassword= findViewById(R.id.edtPassword);
        edtEmail = findViewById(R.id.edtEmail);
        edtRePassword = findViewById(R.id.edtRePassword);
        btnRegister = findViewById(R.id.btnRegister);

        txtSignIn = findViewById(R.id.txtSignIn);


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                registerUser();
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangKyActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        });

    }

    private void registerUser(){
        String email = edtEmail.getText().toString();
        String password = edtPassword.getText().toString();
        LoginRegisterModel loginRegisterModel = new LoginRegisterModel(email,password);
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<LoginRegisterModel> call = apiInterface.loginRegisterModelCall(loginRegisterModel);
        call.enqueue(new Callback<LoginRegisterModel>() {
            @Override
            public void onResponse(Call<LoginRegisterModel> call, Response<LoginRegisterModel> response) {
                if (response.isSuccessful()) {
                    LoginRegisterModel loginRegisterModel1 = response.body();

                    // Chuyển sang màn hình chính (MainActivity)
                    Intent intent = new Intent(DangKyActivity.this, DangNhapActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình hiện tại nếu bạn không muốn quay lại màn hình đăng nhập
                    Toast.makeText(DangKyActivity.this, "Register successful", Toast.LENGTH_SHORT).show();
                }else {
                    // Xử lý khi response không thành công
                    Toast.makeText(DangKyActivity.this, "Register failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginRegisterModel> call, Throwable t) {
                Toast.makeText(DangKyActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

}

