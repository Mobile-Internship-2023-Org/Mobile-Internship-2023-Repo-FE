package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.models.RePassModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiMatKhauActivity extends AppCompatActivity {

    private EditText edtCurrentPassword, edtNewPassword, edtConfirmPassword;
    private Button btnDoiMatKhau;
    private int userIdToUpdate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_mat_khau);
        initView();
        getUser(new DangNhapActivity().readEmailLocally(DoiMatKhauActivity.this));

        btnDoiMatKhau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                doiMatKhau();
            }
        });
    }

    private void doiMatKhau() {
        String currentPassword = edtCurrentPassword.getText().toString().trim();
        String newPassword = edtNewPassword.getText().toString().trim();
        String confirmPassword = edtConfirmPassword.getText().toString().trim();

        if (TextUtils.isEmpty(currentPassword) || TextUtils.isEmpty(newPassword) || TextUtils.isEmpty(confirmPassword)) {
            Toast.makeText(this, "Vui lòng nhập đầy đủ thông tin", Toast.LENGTH_SHORT).show();
            return;
        }

//        if (newPassword.length() < 6) {
//            Toast.makeText(this, "Mật khẩu phải có ít nhất 6 ký tự", Toast.LENGTH_SHORT).show();
//            return;
//        }
//
//        if (!newPassword.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!%*#?&]).+$")) {
//            Toast.makeText(this, "Mật khẩu phải bao gồm chữ cái, chữ số và ký tự đặc biệt", Toast.LENGTH_SHORT).show();
//            return;
//        }
        if (!newPassword.equals(confirmPassword)) {
            Toast.makeText(this, "Mật khẩu xác nhận không khớp", Toast.LENGTH_SHORT).show();
            return;
        }


        RePassModel rePassModel = new RePassModel(userIdToUpdate, currentPassword, newPassword);

        // Call API to change password
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<RePassModel> call = apiInterface.rePassModelCall(rePassModel);

        call.enqueue(new Callback<RePassModel>() {
            @Override
            public void onResponse(Call<RePassModel> call, Response<RePassModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                    finish();
                } else {
                    Toast.makeText(DoiMatKhauActivity.this, "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<RePassModel> call, Throwable t) {
                Toast.makeText(DoiMatKhauActivity.this, "Có lỗi xảy ra khi đổi mật khẩu", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    private void initView() {
        edtCurrentPassword = findViewById(R.id.edt_current_password);
        edtNewPassword = findViewById(R.id.edt_new_password);
        edtConfirmPassword = findViewById(R.id.edt_confirm_password);
        btnDoiMatKhau = findViewById(R.id.btn_doi_mat_khau);
    }
    private void getUser(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                userIdToUpdate = response.body().getIdNguoiDung();
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
}
