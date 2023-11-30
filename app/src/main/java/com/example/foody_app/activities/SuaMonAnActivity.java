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

import com.example.foody_app.R;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.models.updateResponse;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.google.android.material.textfield.TextInputEditText;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SuaMonAnActivity extends AppCompatActivity {

    AutoCompleteTextView autoCompleteTxt;
    ImageView imgFood;
    TextInputEditText edtNameFood, edtPrice, edtPriceReduced;
    Button btnAddFood, btnDelete;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri selectedImageUri;
    private Map<String, Integer> idTypeMap;
    private List<TypeFood> typeFoods;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);
        //mapping
        Toolbar toolbar = findViewById(R.id.toolbar);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt_Edit);
        imgFood = findViewById(R.id.imgFoodEdit);
        edtNameFood = findViewById(R.id.edtNameEdit);
        edtPrice = findViewById(R.id.edtPriceEdit);
        edtPriceReduced = findViewById(R.id.edtReducedPriceEdit);
        btnAddFood = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);

        //handle button back with toolbar
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        loadTheLoai();

        btnAddFood.setOnClickListener(view -> updateFood());
//        btnDelete.setOnClickListener(view -> deleteFood());
    }

    private void updateFood() {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);

        String ten = Objects.requireNonNull(edtNameFood.getText()).toString();
        String giaBan = Objects.requireNonNull(edtPrice.getText()).toString();
        String giaGiam = Objects.requireNonNull(edtPriceReduced.getText()).toString();

//        truyền id vào theLoai
        String theLoai = Objects.requireNonNull(autoCompleteTxt.getText()).toString();
        int idTheLoai = idTypeMap.get(theLoai);
        int idMonAn = getIntent().getIntExtra("idMonAn", -1);
        Log.i("updateFood", "try to get id: " + autoCompleteTxt.getText());
        Log.i("updateFood", "try to get id: " + idTypeMap);

        // Tạo RequestBody cho các trường
        RequestBody tenRequestBody = RequestBody.create(MediaType.parse("text/plain"), ten);
        RequestBody giaBanRequestBody = RequestBody.create(MediaType.parse("text/plain"), giaBan);
        RequestBody giaGiamRequestBody = RequestBody.create(MediaType.parse("text/plain"), giaGiam);
        RequestBody theLoaiRequestBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(idTheLoai));

        MultipartBody.Part filePart = null;

        // Kiểm tra nếu đã chọn ảnh mới
        if (selectedImageUri != null) {
            File file = new File(getRealPathFromURI(selectedImageUri));
            RequestBody fileRequestBody = RequestBody.create(MediaType.parse("image/*"), file);
            filePart = MultipartBody.Part.createFormData("anh", file.getName(), fileRequestBody);
        }

        // Gọi API để cập nhật món ăn
        Call<updateResponse> call = apiInterface.updateFood(idMonAn, filePart, tenRequestBody, giaBanRequestBody, giaGiamRequestBody, theLoaiRequestBody);
        call.enqueue(new Callback<updateResponse>() {
            @Override
            public void onResponse(Call<updateResponse> call, Response<updateResponse> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(SuaMonAnActivity.this, "Cập nhật món ăn thành công", Toast.LENGTH_SHORT).show();
                    finish(); // Kết thúc activity sau khi cập nhật thành công
                } else {
                    if (response.errorBody() != null) {
                        try {
                            Log.d("Error Body", response.errorBody().string());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<updateResponse> call, Throwable t) {
                Toast.makeText(SuaMonAnActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
            }
        });
    }

//    private void deleteFood(){
//        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
//
//        int idMonAn = getIntent().getIntExtra("idMonAn", -1);
//
//        RequestBody emptyBody = RequestBody.create(MediaType.parse("text/plain"), "");
//        Call<updateResponse> call = apiInterface.deleteFood(idMonAn, emptyBody);
//        call.enqueue(new Callback<updateResponse>() {
//            @Override
//            public void onResponse(Call<updateResponse> call, Response<updateResponse> response) {
//                if (response.isSuccessful()) {
//                    Toast.makeText(SuaMonAnActivity.this, "Xóa món ăn thành công", Toast.LENGTH_SHORT).show();
//                    finish(); // Kết thúc activity sau khi ẩn thành công
//                } else {
//                    Log.d("delete", response.message());
//                }
//            }
//
//            @Override
//            public void onFailure(Call<updateResponse> call, Throwable t) {
//                Toast.makeText(SuaMonAnActivity.this, "Lỗi kết nối", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }


    private void setAutoCompleteTextById(int typeId) {
        if (idTypeMap != null) {
            for (Map.Entry<String, Integer> entry : idTypeMap.entrySet()) {
                Log.d("setAutoCompleteTextById", "Entry: " + entry.getKey() + ", ID: " + entry.getValue());
                if (entry.getValue().equals(typeId)) {
                    Log.d("setAutoCompleteTextById", "Setting text to: " + entry.getKey());
                    autoCompleteTxt.setText(entry.getKey());
                    break;
                }
            }
        } else {
            Log.e("setAutoCompleteTextById", "idTypeMap is null");
        }
    }

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
                        String tenLoai = typeFood.getTenTheLoai();
                        tenTheLoai.add(tenLoai);
                        idTypeMap.put(tenLoai, typeFood.getIdTheLoai());
                    }

                    Intent intent = getIntent();
                    if (intent != null) {
                        int inMonan = intent.getIntExtra("idMonAn", -1);
                        String anh = intent.getStringExtra("anh");
                        String ten = intent.getStringExtra("ten");
                        int theLoai = intent.getIntExtra("theLoai", -1);
                        int giaBan = intent.getIntExtra("giaBan", 0);
                        int giaGiam = intent.getIntExtra("giaGiam", 0);

                        setAutoCompleteTextById(theLoai);

                        edtNameFood.setText(ten);
                        edtPrice.setText(String.valueOf(giaBan));
                        edtPriceReduced.setText(String.valueOf(giaGiam));
                        Picasso.get().load(anh).into(imgFood);
                    }

                    Log.d("LoadTheLoai", "tenTheLoai: " + tenTheLoai);
                    Log.d("LoadTheLoai", "idTypeMap: " + idTypeMap);
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(SuaMonAnActivity.this, R.layout.custom_dropdown_item, R.id.customItemText, tenTheLoai);
                    autoCompleteTxt.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<Map<String, List<TypeFood>>> call, Throwable t) {
                Log.e("LoadTheLoai", "Error loading types: " + t.getMessage());
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