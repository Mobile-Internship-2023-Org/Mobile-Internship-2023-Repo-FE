package com.example.foody_app.activities;

import static androidx.core.content.ContentProviderCompat.requireContext;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.example.foody_app.R;
import com.example.foody_app.adapter.FoodAdapter2;
import com.example.foody_app.models.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class ChiTietMonAnActivity extends AppCompatActivity {

    private List<FoodModel> mFoodModels;
    private FoodAdapter2 mAdapter2;
    private RecyclerView mRecyclerView;
    private ImageView imgEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_mon_an);

        onBindView();

        mFoodModels = new ArrayList<>();
        for (int i = 0 ; i < 6 ; i++){
            FoodModel foodModel = new FoodModel();
            foodModel.setIdMonAn(i);
            foodModel.setTen("Hamburger");
            foodModel.setGiaBan(40000);
            mFoodModels.add(foodModel);
        }
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

    private void onBindView(){
        mRecyclerView = findViewById(R.id.rcvCTMA);
        imgEdit = findViewById(R.id.imgEdit);
    }
}