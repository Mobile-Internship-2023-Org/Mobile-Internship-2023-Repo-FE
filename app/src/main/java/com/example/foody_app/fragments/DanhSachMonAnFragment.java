package com.example.foody_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.foody_app.R;
import com.example.foody_app.activities.ThemMonAnActivity;
import com.example.foody_app.activities.TimKiemActivity;
import com.example.foody_app.adapter.FoodAdapter;
import com.example.foody_app.models.FoodModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class DanhSachMonAnFragment extends Fragment {

    private CardView cardViewSearch;
    private FloatingActionButton fabAddFood;
    private List<FoodModel> mFoodModels;
    private FoodAdapter mAdapter;
    private GridView mGridView;

    public DanhSachMonAnFragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_danh_sach_mon_an, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onBindView(view);

        mFoodModels = new ArrayList<>();
        for (int i = 0 ; i < 6 ; i++){
            FoodModel foodModel = new FoodModel();
            foodModel.setIdMonAn(i);
            foodModel.setTen("Hamburger");
            foodModel.setGiaBan(40000);
            mFoodModels.add(foodModel);
        }
        mAdapter = new FoodAdapter(getContext(),mFoodModels);
        mGridView.setAdapter(mAdapter);

        fabAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThemMonAnActivity.class);
                startActivity(intent);
            }
        });
        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
    }

    private void onBindView(View view){
        fabAddFood = view.findViewById(R.id.fabAddFood);
        cardViewSearch = view.findViewById(R.id.cardViewSearch);
        mGridView = view.findViewById(R.id.grid_ds);
    }
}