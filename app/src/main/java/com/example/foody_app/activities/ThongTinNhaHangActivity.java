package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.foody_app.R;

public class ThongTinNhaHangActivity extends AppCompatActivity {
    Toolbar toolbar;
    ImageView myBack,imgShop;
    TextView tvName,tvPhoneNumber,tvProduct,tvFanpage,tvAddress;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_nha_hang);
        myBack = (ImageView) findViewById(R.id.myBack);
        imgShop = (ImageView) findViewById(R.id.imgShop);
        tvName = (TextView) findViewById(R.id.tvName);
        tvPhoneNumber =  (TextView) findViewById(R.id.tvPhoneNumber);
        tvProduct = (TextView)  findViewById(R.id.tvProduct);
        tvFanpage = (TextView)  findViewById(R.id.tvFanpage);
        tvAddress = (TextView)  findViewById(R.id.tvAddress);
        myBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
}