package com.example.foody_app.fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietHoaDonActivity;
import com.example.foody_app.activities.DangNhapActivity;
import com.example.foody_app.adapter.LichSuAdapter;
import com.example.foody_app.models.LichSuModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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

        getRole(getEmail());

        mAdapter = new LichSuAdapter(getContext(), mList);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getContext(), ChiTietHoaDonActivity.class);
                intent.putExtra("idDonHang", mAdapter.getItemId(i));
                intent.putExtra("idGioHang", mList.get(i).getIdGioHang());
                startActivity(intent);
            }
        });
    }
    private void getHoaDon(String email){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<LichSuModel>> call = apiInterface.getHoaDon(email);
        call.enqueue(new Callback<List<LichSuModel>>() {
            @Override
            public void onResponse(Call<List<LichSuModel>> call, Response<List<LichSuModel>> response) {
                if(response.isSuccessful()){
                    mList.clear();
                    mList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<LichSuModel>> call, Throwable t) {

            }
        });
    }
    private String getEmail(){
        DangNhapActivity activity = new DangNhapActivity();
        return activity.readEmailLocally(getContext());
    }

    private void getRole(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    Log.e("TAG", "onResponse: "+response.body().getRole() );
                    if(response.body().getRole().equals("user")){
                        getHoaDon(email);
                    }else{
                        getHoaDonAll();
                    }
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
    private void getHoaDonAll(){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<LichSuModel>> call = apiInterface.getHoaDon();
        call.enqueue(new Callback<List<LichSuModel>>() {
            @Override
            public void onResponse(Call<List<LichSuModel>> call, Response<List<LichSuModel>> response) {
                if(response.isSuccessful()){
                    mList.clear();
                    mList.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<LichSuModel>> call, Throwable t) {

            }
        });
    }
}