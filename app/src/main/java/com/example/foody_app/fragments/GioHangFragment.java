package com.example.foody_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.example.foody_app.R;
import com.example.foody_app.activities.XacNhanDonHangActivity;
import com.example.foody_app.adapter.GioHangAdapter;
import com.example.foody_app.models.FoodModel;

import java.util.ArrayList;
import java.util.List;

public class GioHangFragment extends Fragment {


    private GioHangAdapter mGioHangAdapter;
    private List<FoodModel> mFoodModels;
    private ListView mListView;
    private Button btnDatHang;

    public GioHangFragment() {
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
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = view.findViewById(R.id.listViewCart);
        btnDatHang = view.findViewById(R.id.btnDatHang);
        mFoodModels = new ArrayList<>();
        for (int i = 0 ; i < 2 ; i++){
            FoodModel foodModel = new FoodModel();
            foodModel.setIdMonAn(i);
            foodModel.setTen("Xúc xích");
            foodModel.setGiaBan(40000);
            mFoodModels.add(foodModel);
        }
        mGioHangAdapter = new GioHangAdapter(getContext(), mFoodModels);
        mListView.setAdapter(mGioHangAdapter);

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), XacNhanDonHangActivity.class);
                startActivity(intent);
            }
        });
    }
}