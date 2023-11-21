package com.example.foody_app.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
    private TextView tvTen, tvGia;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);

        onBindView();

        Long id = getIntent().getLongExtra("idFood", -1);
        if(id != null){
            getFoodById(Long.toString(id));
        }
        mFoodModels = new ArrayList<>();
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
        tvGia = findViewById(R.id.tvGiaMonAnCT);
    }

    private void getFoodById(String id){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<FoodModel> call = apiInterface.getFoodById(id);
        call.enqueue(new Callback<FoodModel>() {
            @Override
            public void onResponse(Call<FoodModel> call, Response<FoodModel> response) {
                if(response.isSuccessful()){
                    FoodModel model = response.body();
                    tvTen.setText(model.getTen());
                    tvGia.setText(model.getGiaBan()+"đ/suất");

                }else {
                    Log.e("TAG", "onResponse: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<FoodModel> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
}