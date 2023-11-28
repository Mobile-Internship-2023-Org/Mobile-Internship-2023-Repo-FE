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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.activities.DangNhapActivity;
import com.example.foody_app.activities.DoiMatKhauActivity;
import com.example.foody_app.activities.DoiThongTinTKActivity;
import com.example.foody_app.activities.ThongTinNhaHangActivity;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.UserModelHelper;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TaiKhoanFragment extends Fragment {

    private CardView cvInfoRestaurant, cvChangeInfoUser, cvChangePassword, cvLogOut;
    private ImageView imgAVT;
    private TextView tvTen;

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

        DangNhapActivity activity = new DangNhapActivity();
        getUserData(activity.readEmailLocally(requireContext()));

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

        cvLogOut.setOnClickListener(view13 -> {
            requireActivity().finish();
        });
    }
    private void onBindView(@NonNull View view){
        cvInfoRestaurant = view.findViewById(R.id.cvInfoRestaurant);
        cvChangeInfoUser = view.findViewById(R.id.cvChangeInfoUser);
        cvChangePassword = view.findViewById(R.id.cvChangePassword);
        cvLogOut = view.findViewById(R.id.cvLogOut);
        imgAVT = view.findViewById(R.id.imgAVT);
        tvTen = view.findViewById(R.id.tvTenND);
    }

    /**
     * lấy dữ liệu người dùng theo @param email
     */
    private void getUserData(String email){
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @Override
            public void onResponse(@NonNull Call<UserModel> call, @NonNull Response<UserModel> response) {
                if(response.isSuccessful()){
                    assert response.body() != null;
                    if(response.body().getAnh() != null){
                        Picasso.get().load(response.body().getAnh()).into(imgAVT);
                    }
                    if(response.body().getHoTen() != null){
                        tvTen.setText(response.body().getHoTen());
                    }else{
                        tvTen.setText(response.body().getEmail());
                    }
                }else{
                    Toast.makeText(getActivity(), "lấy thông tin người dùng thất bại", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(@NonNull Call<UserModel> call, @NonNull Throwable t) {
                Toast.makeText(getActivity(), "lỗi "+t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}