package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.LauncherActivity;
import android.content.ClipData;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.example.foody_app.R;
import com.example.foody_app.models.FoodModel;

import java.util.ArrayList;

public class TimKiemActivity extends AppCompatActivity {

    Toolbar toolbar;
    ImageView btnBack;
    SearchView searchView;
    RecyclerView recyclerView;
    ArrayList<String> arrayList;
    ArrayAdapter<String> adapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        initView();
        ActionToolBar();
    }

    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btnBack);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);

        arrayList = new ArrayList<>();
        arrayList.add("Mỳ Quảng");
        arrayList.add("Bún Đậu");
        arrayList.add("Chân Gà");
        arrayList.add("Mỳ Cay");
        arrayList.add("Bánh Mỳ");
        arrayList.add("Phở");
        arrayList.add("Xôi");
        arrayList.add("Cơm Tấm");

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1,arrayList);

    }

    private void ActionToolBar(){
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}