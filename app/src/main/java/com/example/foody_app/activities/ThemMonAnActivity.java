package com.example.foody_app.activities;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foody_app.MainActivity;
import com.example.foody_app.R;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemMonAnActivity extends AppCompatActivity {

    /*
     * Author: Hoàng
     * Date: 01/12/2023
     * xử lý thêm món ăn
     */

    AutoCompleteTextView autoCompleteTxt;
    ImageView imgFood;
    TextInputEditText edtNameFood, edtPrice, edtPriceReduced;
    Button btnAddFood;

    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private Map<String, Integer> idTypeMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);

        //mapping
        Toolbar toolbar = findViewById(R.id.toolbar);
        imgFood = findViewById(R.id.imgFood);
        edtNameFood = findViewById(R.id.edtNameFood);
        edtPrice = findViewById(R.id.edtPrice);
        edtPriceReduced = findViewById(R.id.edtReducedPrice);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);
        btnAddFood = findViewById(R.id.btnAddFood);

        // xử lý toolbar nút quay lại
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        loadTheLoai();
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addFood();
            }
        });
    }

    // xử lý thêm món ăn mới
    private void addFood() {
        // kiểm tra
        if (selectedImageUri == null || edtNameFood.getText().toString().isEmpty() ||
                edtPrice.getText().toString().isEmpty() || edtPriceReduced.getText().toString().isEmpty() ||
                autoCompleteTxt.getText().toString().isEmpty()) {
            Toast.makeText(ThemMonAnActivity.this, "Vui lòng nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
            return;
        }

        String ten = edtNameFood.getText().toString();
        int giaBan = Integer.parseInt(edtPrice.getText().toString());
        int giaGiam = Integer.parseInt(edtPriceReduced.getText().toString());
        String theLoai = autoCompleteTxt.getText().toString();
        int idTheLoai = idTypeMap.get(theLoai);

        // chuyển ổi uri hình thành file
        File file = new File(getRealPathFromURI(selectedImageUri));

        // RequestBody cho file hình ảnh
        RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part filePart = MultipartBody.Part.createFormData("anh", file.getName(), requestBody);

        // RequestBody cho các tham số truyền vào
        RequestBody tenBody = RequestBody.create(MediaType.parse("multipart/form-data"), ten);
        RequestBody giaBanBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(giaBan));
        RequestBody giaGiamBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(giaGiam));
        RequestBody idTheLoaiBody = RequestBody.create(MediaType.parse("multipart/form-data"), String.valueOf(idTheLoai));

        // gọi api thêm món ăn
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<FoodModel> call = apiInterface.addFood(filePart, tenBody, giaBanBody, giaGiamBody, idTheLoaiBody);
        call.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(ThemMonAnActivity.this, "Thêm món ăn thành công.", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ThemMonAnActivity.this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                } else {
                    Toast.makeText(ThemMonAnActivity.this, "Lỗi khi thêm món ăn.", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {
                Log.e("AddFoodError", Objects.requireNonNull(t.getMessage()));
                Toast.makeText(ThemMonAnActivity.this, "Lỗi khi thêm món ăn.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private String getRealPathFromURI(Uri uri) {
        String[] projection = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(uri, projection, null, null, null);
        if (cursor == null) return null;

        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        cursor.moveToFirst();
        String path = cursor.getString(column_index);
        cursor.close();
        return path;
    }

    // xử lý lấy tên loại của món ăn để hiển thị lên AutoCompleteTextView
    private void loadTheLoai() {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Map<String, List<TypeFood>>> call = apiInterface.getTheLoai();
        call.enqueue(new Callback<Map<String, List<TypeFood>>>() {
            @Override
            public void onResponse(Call<Map<String, List<TypeFood>>> call, Response<Map<String, List<TypeFood>>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Map<String, List<TypeFood>> responseData = response.body();
                    List<TypeFood> typeFoods = responseData.get("types");

                    List<String> tenTheLoai = new ArrayList<>();
                    idTypeMap = new HashMap<>();
                    for (TypeFood typeFood : typeFoods) {
                        tenTheLoai.add(typeFood.getTenTheLoai());
                        idTypeMap.put(typeFood.getTenTheLoai(), typeFood.getIdTheLoai());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ThemMonAnActivity.this, R.layout.custom_dropdown_item, R.id.customItemText, tenTheLoai);
                    autoCompleteTxt.setAdapter(adapter);
                }
            }
            @Override
            public void onFailure(Call<Map<String, List<TypeFood>>> call, Throwable t) {

            }
        });
    }

    // xử lý mở thư viện ảnh của thiết bị
    public void openGallery(View v) {
        Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(galleryIntent, PICK_IMAGE_REQUEST);
    }

    // xử lý chọn ảnh từ thư viện hiển thị lên imgFood
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            selectedImageUri = data.getData();
            imgFood.setImageURI(selectedImageUri);
        }
    }
}

