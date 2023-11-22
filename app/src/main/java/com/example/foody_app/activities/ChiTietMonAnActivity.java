package com.example.foody_app.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.adapter.FoodAdapter2;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietMonAnActivity extends AppCompatActivity {

    private List<FoodModel> mFoodModels;
    private FoodAdapter2 mAdapter2;
    private RecyclerView mRecyclerView;
    private ImageView imgEdit;
    private TextView tvTen, tvGiaBan, tvSoLuong, tvGia;
    private ImageView imgMinus, imgPlus;
    private FoodModel model;
    private LinearLayout mLayout;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);

        onBindView();

        long id = getIntent().getLongExtra("idFood", -1);
        Integer type = getIntent().getIntExtra("idType",-1);
        tvSoLuong.setText(1+"");
        /**
         * xử lý sự kiện click image (-)
         */
        imgMinus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tvSoLuong.setText((Integer.parseInt(tvSoLuong.getText().toString()) - 1)+"");
                if(Integer.parseInt(tvSoLuong.getText().toString()) < 1){
                    tvSoLuong.setText(1+"");
                }
                tvGia.setText((model.getGiaBan()*Integer.parseInt(tvSoLuong.getText().toString()))+"đ");
            }
        });
        /**
         * xử lý sự kiện click image (+)
         */
        imgPlus.setOnClickListener(new View.OnClickListener() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onClick(View view) {
                tvSoLuong.setText((Integer.parseInt(tvSoLuong.getText().toString()) + 1)+"");
                tvGia.setText((model.getGiaBan()*Integer.parseInt(tvSoLuong.getText().toString()))+"đ");
            }
        });
        mFoodModels = new ArrayList<>();
        getFoodById(Long.toString(id));
        getFoodByType(type, (int) id);
        mAdapter2 = new FoodAdapter2(mFoodModels);
        LinearLayoutManager layoutManager
                = new LinearLayoutManager(ChiTietMonAnActivity.this, LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setAdapter(mAdapter2);
        mRecyclerView.setLayoutManager(layoutManager);

        imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ChiTietMonAnActivity.this, SuaMonAnActivity.class);
                startActivity(intent);
            }
        });

    }

    /**
     * ánh xạ view
     */
    private void onBindView(){
        mRecyclerView = findViewById(R.id.rcvCTMA);
        imgEdit = findViewById(R.id.imgEdit);
        tvTen = findViewById(R.id.tvTenMonAnCT);
        tvGiaBan = findViewById(R.id.tvGiaMonAnCT);
        tvSoLuong = findViewById(R.id.tvSoLuongCT);
        imgMinus = findViewById(R.id.imgMinus);
        imgPlus = findViewById(R.id.imgPlus);
        tvGia = findViewById(R.id.tvGia);
        mLayout = findViewById(R.id.layout_cart);
    }

    /**
     *
     * function laays thoong tin mons awn theo id
     */
    private void getFoodById(String id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<FoodModel> call = apiInterface.getFoodById(id);
        call.enqueue(new Callback<FoodModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(@NonNull Call<FoodModel> call, @NonNull Response<FoodModel> response) {
                if(response.isSuccessful()){
                    model = response.body();
                    assert model != null;
                    tvTen.setText(model.getTen());
                    tvGiaBan.setText(model.getGiaBan()+"đ/suất");
                    tvGia.setText((model.getGiaBan()*Integer.parseInt(tvSoLuong.getText().toString()))+"đ");

                }else {
                    Log.e("TAG", "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<FoodModel> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }

    private void getFoodByType(Integer type, Integer id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<FoodModel>> call = apiInterface.getFoodByType(type, id);

        call.enqueue(new Callback<List<FoodModel>>() {
            @SuppressLint("NotifyDataSetChanged")
            @Override
            public void onResponse(@NonNull Call<List<FoodModel>> call, @NonNull Response<List<FoodModel>> response) {
                if(response.isSuccessful()){
                    mFoodModels.clear();
                    assert response.body() != null;
                    mFoodModels.addAll(response.body());
                    mAdapter2.notifyDataSetChanged();
                }else{
                    Log.e("TAG", "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<FoodModel>> call, @NonNull Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage() );
            }
        });
    }

}