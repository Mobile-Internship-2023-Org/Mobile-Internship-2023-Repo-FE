package com.example.foody_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietHoaDonActivity;
import com.example.foody_app.adapter.LichSuAdapter;
import com.example.foody_app.models.LichSuModel;

import java.util.ArrayList;
import java.util.List;

public class LichSuDatHangFragment extends Fragment {

    private List<LichSuModel> mList;
    private ListView mListView;
    private LichSuAdapter mAdapter;

    public LichSuDatHangFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_lich_su_dat_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mList = new ArrayList<>();
        mListView = view.findViewById(R.id.listViewLS);

        for(int i = 0 ; i < 4 ; i++){
            LichSuModel model = new LichSuModel();
            model.setIdMonAn(i);
            model.setGia(20000);
            model.setLoai("Món ăn");
            model.setSoLuong(1);
            model.setDiaChi("Địa chỉ: 116 Nguyễn Huy Tưởng, Hòa An, Liên Chiểu, Đà Nẵng");
            model.setTrangThai(0);
            model.setTenMonAn("xúc xích chiên");
            mList.add(model);
        }

        mAdapter = new LichSuAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietHoaDonActivity.class);
                startActivity(intent);
            }
        });
    }
}