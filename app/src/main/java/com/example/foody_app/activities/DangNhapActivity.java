package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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

public class DangNhapActivity extends AppCompatActivity {


    private TextView txtSignUp;
    private EditText edtEmail, edtPass;
    private  Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dang_nhap);

        // Khởi tạo các thành phần giao diện
        txtSignUp = findViewById(R.id.txtSignUp);
        edtPass = findViewById(R.id.edtPass);
        edtEmail = findViewById(R.id.edtEmail);
        btnLogin = findViewById(R.id.btnLogin);

        //đọc email
        edtEmail.setText(readEmailLocally());


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
//                startActivity(intent);
                loginUser();
            }
        });


        txtSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DangNhapActivity.this,DangKyActivity.class);
                startActivity(intent);
            }
        });

    }

//    private void  loginUser(){
//        String email = edtEmail.getText().toString();
//        String password = edtPass.getText().toString();
//        LoginModel loginModel = new LoginModel(email,password);
//        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
//        Call<LoginModel> call = apiInterface.loginModelCall(loginModel);
//        call.enqueue(new Callback<LoginModel>() {
//            @Override
//            public void onResponse(Call<LoginModel> call, Response<LoginModel> response) {
//                Toast.makeText(DangNhapActivity.this, "ok", Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onFailure(Call<LoginModel> call, Throwable t) {
//                Toast.makeText(DangNhapActivity.this, "Error", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    //

    private void  loginUser(){
        String email = edtEmail.getText().toString();
        String password = edtPass.getText().toString();
        LoginRegisterModel loginRegisterModel = new LoginRegisterModel(email,password);
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<LoginRegisterModel> call = apiInterface.loginModelCall(loginRegisterModel);
        call.enqueue(new Callback<LoginRegisterModel>() {
            @Override
            public void onResponse(Call<LoginRegisterModel> call, Response<LoginRegisterModel> response) {
                if (response.isSuccessful()) {
                    LoginRegisterModel loginModel1 = response.body();

                    // Chuyển sang màn hình chính (MainActivity)
                    Intent intent = new Intent(DangNhapActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish(); // Đóng màn hình hiện tại nếu bạn không muốn quay lại màn hình đăng nhập
                    Toast.makeText(DangNhapActivity.this, "Login successful", Toast.LENGTH_SHORT).show();
                    saveEmailLocally(email);
                }else {
                    // Xử lý khi response không thành công
                    Toast.makeText(DangNhapActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
                }

            }

            @Override
            public void onFailure(Call<LoginRegisterModel> call, Throwable t) {
                Toast.makeText(DangNhapActivity.this, "Error", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void saveEmailLocally(String email) {
        // Use SharedPreferences to save the email locally
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("email", email);
        editor.apply();
    }
    private String readEmailLocally() {
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", Context.MODE_PRIVATE);
        return sharedPreferences.getString("email", "");
    }


}