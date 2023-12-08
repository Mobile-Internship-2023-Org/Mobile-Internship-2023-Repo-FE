package com.example.foody_app.activities;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import androidx.appcompat.widget.SearchView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.adapter.FoodAdapter;
import com.example.foody_app.adapter.FoodAdapter2;
import com.example.foody_app.models.FoodModel;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class TimKiemActivity extends AppCompatActivity {


    Toolbar toolbar;
    ImageView btnBack, btnSearch;
    SearchView searchView;
    RecyclerView recyclerView;
    FoodAdapter2 mAdapter;
    APIInterface foodApi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tim_kiem);
        initView();
        setOnclickToolbar();
    }


    private void initView() {
        toolbar = findViewById(R.id.toolbar);
        btnBack = findViewById(R.id.btnBack);
        searchView = findViewById(R.id.searchView);
        recyclerView = findViewById(R.id.recyclerView);
        btnSearch = findViewById(R.id.btnSearch);
        mAdapter = new FoodAdapter2(new ArrayList<>(), new FoodAdapter2.OnItemClickListener() {
            @Override
            public void onItemClick(FoodModel item) {
                Intent intent = new Intent(TimKiemActivity.this, ChiTietMonAnActivity.class);
                intent.putExtra("idFood", (long) item.getIdMonAn());
                intent.putExtra("idType",item.getIdTheLoai());
                Log.e("TAG", "onItemClick: "+item.getIdTheLoai() );// Assuming FoodModel has getId() method
                startActivity(intent);
            }
        });
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        recyclerView.setAdapter(mAdapter);
        foodApi = APIClient.getInstance().create(APIInterface.class);
        btnSearch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String searchTerm = searchView.getQuery().toString();
                searchFood(searchTerm);
            }
        });
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                searchFood(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                // Xử lý khi thay đổi văn bản trong SearchView
                return false;
            }
        });
    }
    private void setOnclickToolbar(){
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }
    private void searchFood(String searchTerm) {
        Call<List<FoodModel>> call = foodApi.getFoodByNameRegex(searchTerm);
        call.enqueue(new Callback<List<FoodModel>>() {
            @Override
            public void onResponse(Call<List<FoodModel>> call, Response<List<FoodModel>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    if (mAdapter != null) {
                        mAdapter.updateList(response.body());
                    }
                }
            }
            @Override
            public void onFailure(Call<List<FoodModel>> call, Throwable t) {
                Toast.makeText(TimKiemActivity.this, "Lỗi", Toast.LENGTH_SHORT).show();
                Log.e("API_CALL", "Gọi API thất bại", t);
            }
        });
    }


}