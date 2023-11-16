package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody_app.R;

public class DoiMatKhauActivity extends AppCompatActivity {
    EditText edt_current_password, edt_new_password, edt_confirm_password;
    Button btn_doi_mat_khau;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        initControll();
        initView();
    }


    private void initControll() {
        btn_doi_mat_khau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiMatKhau();
            }
        });
    }

    private void doiMatKhau() {
        String str_current_pass = edt_current_password.getText().toString().trim();
        String str_new_pass = edt_new_password.getText().toString().trim();
        String str_confirm_pass = edt_confirm_password.getText().toString().trim();

        if (TextUtils.isEmpty(str_current_pass)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập mật khẩu hiện tại!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_new_pass)) {
            Toast.makeText(getApplicationContext(), "Vui lòng nhập mật khẩu mới!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(str_confirm_pass)) {
            Toast.makeText(getApplicationContext(), "Bạn chưa nhập xác nhận mật khẩu hoặc nhập sai mật khẩu mới!", Toast.LENGTH_SHORT).show();
        } else if (str_new_pass.length() < 6) {
            Toast.makeText(getApplicationContext(), "Mật khẩu mới phải có ít nhất 6 ký tự!", Toast.LENGTH_SHORT).show();
        } else if (!str_new_pass.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&]).+$")) {
            Toast.makeText(getApplicationContext(), "Mật khẩu mới phải bao gồm chữ cái, chữ số và ký tự đặc biệt!", Toast.LENGTH_SHORT).show();
        } else if (str_new_pass != str_confirm_pass) {
            Toast.makeText(getApplicationContext(), "Xác nhận mật khẩu không khớp mật khẩu mới", Toast.LENGTH_SHORT).show();
        }
    }


    private void initView() {
        edt_current_password = findViewById(R.id.edt_current_password);
        edt_new_password = findViewById(R.id.edt_new_password);
        edt_confirm_password = findViewById(R.id.edt_confirm_password);
        btn_doi_mat_khau = findViewById(R.id.btn_doi_mat_khau);

    }

}