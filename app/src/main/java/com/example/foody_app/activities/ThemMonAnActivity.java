package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.foody_app.R;
import com.example.foody_app.models.TypeFood;
import com.example.foody_app.utils.APIClient;
import com.example.foody_app.utils.APIInterface;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemMonAnActivity extends AppCompatActivity {
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    ImageView imgFood;
    TextInputEditText edtNameFood, edtPrice, edtPriceReduced;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_mon_an);

        //mapping
        Toolbar toolbar = findViewById(R.id.toolbar);
        imgFood = findViewById(R.id.imgFood);
        edtNameFood = findViewById(R.id.edtNameFood);
        edtPriceReduced = findViewById(R.id.edtReducedPrice);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        //handle button back with toolbar
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        loadTheLoai();
    }

    private void loadTheLoai() {
        APIInterface apiInterface = APIClient.getInstance().create(APIInterface.class);
        Call<List<TypeFood>> call = apiInterface.getTheLoai();
        call.enqueue(new Callback<List<TypeFood>>() {
            @Override
            public void onResponse(Call<List<TypeFood>> call, Response<List<TypeFood>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    List<TypeFood> typeFoods = response.body();

                    List<String> tenTheLoai = new ArrayList<>();
                    for (TypeFood typeFood : typeFoods) {
                        tenTheLoai.add(typeFood.getTenTheLoai());
                    }
                    ArrayAdapter<String> adapter = new ArrayAdapter<>(ThemMonAnActivity.this, android.R.layout.simple_spinner_item, tenTheLoai);
                    adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    autoCompleteTxt.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<TypeFood>> call, Throwable t) {
                Log.e("API Error", "onFailure: " + t.getMessage());
            }
        });
    }
    public void buttonClick(View view){
        Toast.makeText(this, "Alo", Toast.LENGTH_SHORT).show();
    }
}