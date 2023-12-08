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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DangKyActivity extends AppCompatActivity {

    private TextView txtSignIn;
    private EditText edtUsername, edtEmail,edtPassword,edtRePassword;
    private Button btnRegister;
    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);



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
                if(validate()){
                    if(edtPassword.getText().toString().equals(edtRePassword.getText().toString())){
                        if(regexEmail(edtEmail.getText().toString())){
                            registerUser();
                        }else{
                            Toast.makeText(DangKyActivity.this, "email không đúng", Toast.LENGTH_SHORT).show();
                        }
                    }else{
                        Toast.makeText(DangKyActivity.this, "Mật khẩu không chính xác", Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Toast.makeText(DangKyActivity.this, "Bạn phải nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
                }
            }
        });

        txtSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
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
                    Toast.makeText(DangKyActivity.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(DangKyActivity.this, DoiThongTinTKActivity.class);
                    intent.putExtra("createAccount",1);
                    intent.putExtra("email", edtEmail.getText().toString());
                    startActivity(intent);
                } else {
                    // Xử lý khi response không thành công
                    try {
                        String errorMessage = "";
                        if (response.errorBody() != null) {
                            JSONObject errorObject = new JSONObject(response.errorBody().string());
                            errorMessage = errorObject.getString("message");
                        }
                        Toast.makeText(DangKyActivity.this, errorMessage, Toast.LENGTH_SHORT).show();
                    } catch (JSONException | IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<LoginRegisterModel> call, Throwable t) {
                Toast.makeText(DangKyActivity.this, "lỗi"+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    private boolean validate(){
        if(edtEmail.getText().toString().isEmpty()){
            return false;
        }
        if (edtPassword.getText().toString().isEmpty()){
            return false;
        }
        if(edtRePassword.getText().toString().isEmpty()){
            return false;
        }
        return true;
    }
    public static boolean regexEmail(String emailStr) {
        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(emailStr);
        return matcher.matches();
    }

}

