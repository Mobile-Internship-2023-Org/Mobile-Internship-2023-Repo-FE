package com.example.foody_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.os.FileUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.foody_app.R;
import com.example.foody_app.activities.XacNhanDonHangActivity;
import com.example.foody_app.adapter.FoodAdapter;
import com.example.foody_app.adapter.GioHangAdapter;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.InforModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;

import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangFragment extends Fragment {
    ImageView btnMinus, btnPlus;


    private TextView txtTongTien;
    private FoodAdapter mAdapter;

    private GioHangAdapter mGioHangAdapter;
    private List<InforModel> mInforModel;
    private ListView mListView;
    private Button btnDatHang;
    GioHangAdapter Utils;

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
        btnPlus = view.findViewById(R.id.btncong);
        btnMinus = view.findViewById(R.id.btntru);

        mInforModel = new ArrayList<>();
        getInfor();
        mGioHangAdapter = new GioHangAdapter(getContext(), mInforModel);
        mListView.setAdapter(mGioHangAdapter);

        btnDatHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), XacNhanDonHangActivity.class);
                startActivity(intent);
            }
        });
    }



    private void getInfor(){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<InforModel>> call = apiInterface.getInfor();
        call.enqueue(new Callback<List<InforModel>>() {
            @Override
            public void onResponse(Call<List<InforModel>> call, Response<List<InforModel>> response) {
                if(response.isSuccessful()){
                    Log.e("TAG", "onResponse: "+response.body().size());
                    mInforModel.clear();
                    mInforModel.addAll(response.body());
                    mGioHangAdapter.notifyDataSetChanged();
                }else{
                    Log.e("TAG", "onResponse error: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<List<InforModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }


}