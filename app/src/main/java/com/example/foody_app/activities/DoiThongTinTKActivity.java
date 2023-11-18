package com.example.foody_app.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;

import com.example.foody_app.R;

public class DoiThongTinTKActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doi_thong_tin_tkactivity);

        // Toolbar setup
        Toolbar toolbar = findViewById(R.id.toolbar);

        // Set navigation icon click listener
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // Finish the activity when the back button is clicked
                finish();
            }
        });
    }
}