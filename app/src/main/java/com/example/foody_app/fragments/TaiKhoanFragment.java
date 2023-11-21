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
import com.example.foody_app.activities.DoiMatKhauActivity;
import com.example.foody_app.activities.DoiThongTinTKActivity;
import com.example.foody_app.activities.ThongTinNhaHangActivity;

import java.util.Objects;


public class TaiKhoanFragment extends Fragment {

    private CardView cvInfoRestaurant, cvChangeInfoUser, cvChangePassword, cvLogOut;

    public TaiKhoanFragment() {
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
        return inflater.inflate(R.layout.fragment_tai_khoan, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onBindView(view);

        cvInfoRestaurant.setOnClickListener(view14 -> {
            Intent intent = new Intent(getContext(), ThongTinNhaHangActivity.class);
            startActivity(intent);
        });

        cvChangeInfoUser.setOnClickListener(view12 -> {
            Intent intent = new Intent(getContext(), DoiThongTinTKActivity.class);
            startActivity(intent);
        });

        cvChangePassword.setOnClickListener(view1 -> {
            Intent intent = new Intent(getContext(), DoiMatKhauActivity.class);
            startActivity(intent);
        });

        cvLogOut.setOnClickListener(view13 -> requireActivity().finish());
    }
    private void onBindView(@NonNull View view){
        cvInfoRestaurant = view.findViewById(R.id.cvInfoRestaurant);
        cvChangeInfoUser = view.findViewById(R.id.cvChangeInfoUser);
        cvChangePassword = view.findViewById(R.id.cvChangePassword);
        cvLogOut = view.findViewById(R.id.cvLogOut);
    }
}