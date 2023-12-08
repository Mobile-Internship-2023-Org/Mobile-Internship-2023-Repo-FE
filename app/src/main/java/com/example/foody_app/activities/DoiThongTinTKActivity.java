package com.example.foody_app.activities;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.PickVisualMediaRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DoiThongTinTKActivity extends AppCompatActivity {

    private EditText edHoTen, edEmail, edDiaChi, edSdt;
    private ImageView imgAvt;
    private static final int REQUEST_IMAGE_PICKER = 1;
    private Bitmap mBitmap  = null;
    private Button mButtonChange;
    private int create;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_thong_tin_tkactivity);

        onBindView();

        imgAvt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                selectImage();
            }
        });

         create = getIntent().getIntExtra("createAccount", 0);
        if(create == 0){
            DangNhapActivity activity = new DangNhapActivity();
            getUser(activity.readEmailLocally(this));
        }else{
            String email = getIntent().getStringExtra("email");
            edEmail.setText(email);
            edEmail.setEnabled(false);
        }

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the activity when the back button is clicked
                finish();
            }
        });

        mButtonChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(validate()){
                    if(mBitmap != null){
                        uploadImageToFirebase(mBitmap);
                    }else{
                        updateUser();
                    }
                }
            }
        });
    }

    /**
     *
     * init view @param email
     */
    private void onBindView(){
        edEmail = findViewById(R.id.emailEditText);
        edHoTen = findViewById(R.id.hoVaTenEditText);
        edDiaChi = findViewById(R.id.diaChiEditText);
        edSdt = findViewById(R.id.soDienThoaiEditText);
        imgAvt = findViewById(R.id.avatarIv);
        mButtonChange = findViewById(R.id.confirmOrderBtn);
    }

    private void selectImage(){
        Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICKER);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_PICKER && resultCode == RESULT_OK) {
            Uri imageUri = data.getData();
            mBitmap = scaleImage(imageUri, 512, 512);

            // Hiển thị ảnh lên ImageView
            imgAvt.setImageBitmap(mBitmap);
        }
    }

    private void getUser(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    edHoTen.setText(response.body().getHoTen());
                    edEmail.setText(response.body().getEmail());
                    edEmail.setEnabled(false);
                    edDiaChi.setText(response.body().getDiaChi());
                    edSdt.setText(response.body().getSdt());
                    if(response.body().getAnh() != null){
                        Picasso.get().load(response.body().getAnh()).into(imgAvt);
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
    private Bitmap scaleImage(Uri imageUri, int maxWidth, int maxHeight) {
        try {
            InputStream inputStream = getContentResolver().openInputStream(imageUri);
            Bitmap originalBitmap = BitmapFactory.decodeStream(inputStream);

            float widthRatio = (float) originalBitmap.getWidth() / maxWidth;
            float heightRatio = (float) originalBitmap.getHeight() / maxHeight;
            float scaleFactor = Math.max(widthRatio, heightRatio);

            int scaledWidth = Math.round(originalBitmap.getWidth() / scaleFactor);
            int scaledHeight = Math.round(originalBitmap.getHeight() / scaleFactor);
            Bitmap scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, scaledWidth, scaledHeight, true);

            originalBitmap.recycle();

            return scaledBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    private void uploadImageToFirebase(Bitmap bitmap) {

        String fileName = UUID.randomUUID().toString();
        StorageReference storageRef = FirebaseStorage.getInstance().getReference().child("images/" + fileName);

        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageData = baos.toByteArray();

        storageRef.putBytes(imageData)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {

                        storageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                            @Override
                            public void onSuccess(Uri downloadUrl) {
                                String imageUrl = downloadUrl.toString();
                                updateUserandImage(imageUrl);
                            }
                        });
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        // Xử lý khi tải lên thất bại
                    }
                });
    }

    private boolean validate(){
        if(edSdt.getText().toString().isEmpty()){
            return false;
        }
        if(edDiaChi.getText().toString().isEmpty()){
            return false;
        }
        if(edHoTen.getText().toString().isEmpty()){
            return false;
        }
        return  true;
    }
    private void updateUser(){
        UserModel userModel = new UserModel();
        userModel.setHoTen(edHoTen.getText().toString());
        userModel.setEmail(edEmail.getText().toString());
        userModel.setSdt(edSdt.getText().toString());
        userModel.setDiaChi(edDiaChi.getText().toString());
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.updateInfoUser(edEmail.getText().toString(),userModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    if(create == 0){
                        Toast.makeText(DoiThongTinTKActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DoiThongTinTKActivity.this, "Hoàn tất thông tin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DoiThongTinTKActivity.this, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }

                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
    private void updateUserandImage(String urlImage){
        UserModel userModel = new UserModel();
        userModel.setHoTen(edHoTen.getText().toString());
        userModel.setEmail(edEmail.getText().toString());
        userModel.setSdt(edSdt.getText().toString());
        userModel.setDiaChi(edDiaChi.getText().toString());
        userModel.setAnh(urlImage);
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<Void> call = apiInterface.updateInfoUser(edEmail.getText().toString(),userModel);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    if(create == 0){
                        Toast.makeText(DoiThongTinTKActivity.this, "Đổi thông tin thành công", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(DoiThongTinTKActivity.this, "Hoàn tất thông tin", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(DoiThongTinTKActivity.this, DangNhapActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                    }
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}