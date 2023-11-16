package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;

import com.example.foody_app.R;

public class ManHinhChaoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh_chao);
        new CountDownTimer(3000,1000){

            @Override
            public void onTick(long l) {
                //trong vong bao nhieu giay thi thuc hien 1 cong viec nao do
            }

            @Override
            public void onFinish() {
                // sau khi het tgian thi thuc hien cv gi do
                Intent intent  = new Intent(ManHinhChaoActivity.this,DangNhapActivity.class);
                startActivity(intent);
            }
        }.start();

    }
}