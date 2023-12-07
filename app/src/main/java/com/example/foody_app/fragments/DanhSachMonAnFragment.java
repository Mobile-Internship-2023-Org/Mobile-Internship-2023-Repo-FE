package com.example.foody_app.fragments;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import com.example.foody_app.MainActivity;
import com.example.foody_app.R;
import com.example.foody_app.activities.ChiTietHoaDonActivity;
import com.example.foody_app.activities.ChiTietMonAnActivity;
import com.example.foody_app.activities.DangNhapActivity;
import com.example.foody_app.activities.DanhGiaActivity;
import com.example.foody_app.activities.ThemMonAnActivity;
import com.example.foody_app.activities.TimKiemActivity;
import com.example.foody_app.adapter.FoodAdapter;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DanhSachMonAnFragment extends Fragment {

    private CardView cardViewSearch;
    private FloatingActionButton fabAddFood;
    private List<FoodModel> mFoodModels;
    private FoodAdapter mAdapter;
    private GridView mGridView;
    private TextView tvNguoiDung;

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
        mAdapter = new FoodAdapter(getContext(),mFoodModels);
        getAllFood();
        mGridView.setAdapter(mAdapter);

        DangNhapActivity dangNhapActivity = new DangNhapActivity();
        getUserData(dangNhapActivity.readEmailLocally(getContext()));
        fabAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThemMonAnActivity.class);
                startActivity(intent);
            }
        });
        /**
         *sự kiện click tìm chuyển màn hình tìm kiếm món ăn
         */
        cardViewSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), TimKiemActivity.class);
                startActivity(intent);
            }
        });
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(requireContext(), ChiTietMonAnActivity.class);
                intent.putExtra("idFood",mAdapter.getItemId(i));
                intent.putExtra("idType",mFoodModels.get(i).getIdTheLoai());
                startActivity(intent);
            }
        });
    }

    /**
     *
     * ánh xạ view
     */
    private void onBindView(View view){
        fabAddFood = view.findViewById(R.id.fabAddFood);
        cardViewSearch = view.findViewById(R.id.cardViewSearch);
        mGridView = view.findViewById(R.id.grid_ds);
        tvNguoiDung = view.findViewById(R.id.tvNguoiDung);
    }

    /**
     * function lấy dữ liệu món ăn
     */
    private void getAllFood(){
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<FoodModel>> call = apiInterface.getAllFood();
        call.enqueue(new Callback<List<FoodModel>>() {
            @Override
            public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {
                if(response.isSuccessful()){
                    mFoodModels.clear();
                    mFoodModels.addAll(response.body());
                    mAdapter.notifyDataSetChanged();
                }else{
                    Log.e("TAG", "onResponse error: "+response.errorBody() );
                }
            }

            @Override
            public void onFailure(Call<List<FoodModel>> call, Throwable t) {
                Log.e("TAG", "onFailure: "+t.getMessage());
            }
        });
    }
    private void getUserData(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @SuppressLint("SetTextI18n")
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if(response.isSuccessful()){
                    UserModel userModel = response.body();
                    assert userModel != null;
                    Log.e("TAG", "onResponse: "+userModel.getHoTen());
                    if(response.body().getHoTen() != null){
                        tvNguoiDung.setText("Hi "+response.body().getHoTen());
                    }
                    if(userModel.getRole().equals("user")){
                        fabAddFood.setVisibility(View.GONE);
                    }
                }else{

                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {

            }
        });
    }
}