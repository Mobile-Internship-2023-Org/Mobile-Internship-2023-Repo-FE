package com.example.foody_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.example.foody_app.activities.DangNhapActivity;
import com.example.foody_app.fragments.DanhSachMonAnFragment;
import com.example.foody_app.fragments.GioHangFragment;
import com.example.foody_app.fragments.LichSuDatHangFragment;
import com.example.foody_app.fragments.TaiKhoanFragment;
import com.example.foody_app.fragments.ThongKeFragment;
import com.example.foody_app.models.UserModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.example.foody_app.utils.UserModelHelper;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.gson.JsonObject;

import java.util.concurrent.CompletableFuture;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.bottom_navigation);

        DangNhapActivity dangNhapActivity = new DangNhapActivity();
        String email = dangNhapActivity.readEmailLocally(this);
        Log.e("TAG", "onCreate: "+email );
        getUserData(email);

        //ẩn item giỏ hàng hoặc thông kê theo từng role
//        mNavigationView.getMenu().findItem(R.id.nav_statistical).setVisible(false);
//        mNavigationView.getMenu().findItem(R.id.nav_shopping_cart).setVisible(false);
        //thsmh text

        ReplaceFragment(new DanhSachMonAnFragment());

        mNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if(item.getItemId() == R.id.nav_home){
                    ReplaceFragment(new DanhSachMonAnFragment());
                    return true;
                }
                if(item.getItemId() == R.id.nav_history){
                    ReplaceFragment(new LichSuDatHangFragment());
                    return true;
                }
                if(item.getItemId() == R.id.nav_shopping_cart){
                    ReplaceFragment(new GioHangFragment());
                    return true;
                }
                if(item.getItemId() == R.id.nav_statistical){
                    ReplaceFragment(new ThongKeFragment());
                    return true;
                }
                if(item.getItemId() == R.id.nav_account){
                    ReplaceFragment(new TaiKhoanFragment());
                    return true;
                }
                return false;
            }
        });
    }
    private void ReplaceFragment(Fragment fragment){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.frame_layout_container,fragment);
        fragmentTransaction.commit();
    }
    /**
     * function lấy dữ liệu người dùng theo email
     */
    private void getUserData(String email) {
        UserModelHelper.getInstance().getUserByEmail(email, new Callback<UserModel>() {
            @Override
            public void onResponse(Call<UserModel> call, Response<UserModel> response) {
                if (response.isSuccessful()) {
                    UserModel userModel = response.body();
                    Log.e("", "onResponse: "+userModel.getHoTen() );
                    if(userModel.getRole().equals("admin")){
                        mNavigationView.getMenu().findItem(R.id.nav_shopping_cart).setVisible(false);
                    }else{
                        mNavigationView.getMenu().findItem(R.id.nav_statistical).setVisible(false);
                    }
                } else {
                    Log.e("TAG", "onResponse: " + response.errorBody());
                }
            }

            @Override
            public void onFailure(Call<UserModel> call, Throwable t) {
                Log.e("TAG", "onFailure: " + t.getMessage());
            }
        });
    }
}