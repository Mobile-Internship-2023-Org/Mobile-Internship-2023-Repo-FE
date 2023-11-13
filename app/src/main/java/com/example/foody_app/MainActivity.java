package com.example.foody_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.view.MenuItem;

import com.example.foody_app.fragments.DanhSachMonAnFragment;
import com.example.foody_app.fragments.GioHangFragment;
import com.example.foody_app.fragments.LichSuDatHangFragment;
import com.example.foody_app.fragments.TaiKhoanFragment;
import com.example.foody_app.fragments.ThongKeFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView mNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mNavigationView = findViewById(R.id.bottom_navigation);

        //ẩn item giỏ hàng hoặc thông kê theo từng role
//        mNavigationView.getMenu().findItem(R.id.nav_statistical).setVisible(false);
//        mNavigationView.getMenu().findItem(R.id.nav_shopping_cart).setVisible(false);

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
}