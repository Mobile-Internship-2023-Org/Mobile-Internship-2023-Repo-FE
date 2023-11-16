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

import com.example.foody_app.R;
import com.example.foody_app.activities.ThemMonAnActivity;
import com.example.foody_app.activities.TimKiemActivity;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class DanhSachMonAnFragment extends Fragment {

    private CardView cardViewSearch;
    private FloatingActionButton fabAddFood;

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
    }
}