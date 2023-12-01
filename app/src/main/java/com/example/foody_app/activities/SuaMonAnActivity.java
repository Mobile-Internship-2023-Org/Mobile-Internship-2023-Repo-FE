package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.example.foody_app.R;

public class SuaMonAnActivity extends AppCompatActivity {

    String[] items = {"Trà sữa", "Gà", "Bún", "Mì"};
    AutoCompleteTextView autoCompleteTxt;
    ArrayAdapter<String> adapterItems;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_mon_an);
        //mapping
        Toolbar toolbar = findViewById(R.id.toolbar);
        autoCompleteTxt = findViewById(R.id.auto_complete_txt);

        //handle button back with toolbar
        toolbar.setNavigationOnClickListener(view -> onBackPressed());

        //handle list dropdown with AutoCompleteTextView
        adapterItems = new ArrayAdapter<String>(this, R.layout.item_list_drop_down);
        adapterItems.addAll(items);
        autoCompleteTxt.setAdapter(adapterItems);
        autoCompleteTxt.setOnItemClickListener((adapterView, view, i, l) -> {
            String item = adapterView.getItemAtPosition(i).toString();
        });
    }
}