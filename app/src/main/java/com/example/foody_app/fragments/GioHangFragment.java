package com.example.foody_app.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietMonAnActivity;
import com.example.foody_app.activities.DangNhapActivity;
import com.example.foody_app.activities.XacNhanDonHangActivity;
import com.example.foody_app.adapter.GioHangAdapter;
import com.example.foody_app.models.InforModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangFragment extends Fragment {
    private ImageView btnCong, btnTru;

    private List<InforModel> mInforModel;
    private GioHangAdapter mGioHangAdapter;
    private ListView mListView;
    private Button btnDatHang;
    private String email;

    public GioHangFragment() {
        // Required empty public constructor
    }

    public void onQuantityChanged() {
        updateTotalAmount();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gio_hang, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        mListView = view.findViewById(R.id.listViewCart);
        btnDatHang = view.findViewById(R.id.btnDatHang);

        mInforModel = new ArrayList<>();

        DangNhapActivity activity = new DangNhapActivity();
        String userEmail = activity.readEmailLocally(getContext());
        getInfor(userEmail);
        updateTotalAmount();

        mGioHangAdapter = new GioHangAdapter(getContext(), mInforModel);
        mListView.setAdapter(mGioHangAdapter);

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), XacNhanDonHangActivity.class);
                startActivity(intent);
                setAndRefreshCart(userEmail);
                mGioHangAdapter.notifyDataSetChanged();
                updateTotalAmount();
            }
        });
    }

    public void setAndRefreshCart(String userEmail) {
        getInfor(userEmail);
    }

    private void getInfor(String userEmail) {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<InforModel>> call = apiInterface.getInfor(userEmail);
        call.enqueue(new Callback<List<InforModel>>() {
            @Override
            public void onResponse(Call<List<InforModel>> call, Response<List<InforModel>> response) {
                if (response.isSuccessful()) {
                    Log.e("TAG", "onResponse: " + response.body());
                    mInforModel.clear();
                    mInforModel.addAll(response.body());
                    mGioHangAdapter.notifyDataSetChanged();
                    updateTotalAmount();
                } else {
                    Log.e("TAG", "onResponse error: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<List<InforModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }

    public void updateTotalAmount() {
        long totalAmount = calculateTotal();
        TextView txtTongTien = getView().findViewById(R.id.txtTongTien);
        txtTongTien.setText("Tổng tiền: " + ChiTietMonAnActivity.currencyFormat(totalAmount+"") + "đ");
    }

    private long calculateTotal() {
        long total = 0;
        for (InforModel item : mInforModel) {
            total += item.getGiaBan() * item.getSoLuong();
        }
        return total;
    }
}
