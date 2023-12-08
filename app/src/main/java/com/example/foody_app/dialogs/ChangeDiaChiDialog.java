package com.example.foody_app.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.foody_app.R;
import com.example.foody_app.utils.OnDiaChiChangedListener;

public class ChangeDiaChiDialog extends Dialog {
    private OnDiaChiChangedListener listener;

    public ChangeDiaChiDialog(@NonNull Context context, OnDiaChiChangedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_dia_chi);

        EditText edtNewDiaChi = findViewById(R.id.edtNewDiaChi);
        EditText edtNewName = findViewById(R.id.edtNewName);
        EditText edtNewPhoneNumber = findViewById(R.id.edtNewPhoneNumber);
        Button btnSave = findViewById(R.id.btnSave);

        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String newDiaChi = edtNewDiaChi.getText().toString();
                String newName = edtNewName.getText().toString();
                String newPhoneNumber = edtNewPhoneNumber.getText().toString();

                listener.onDiaChiChanged(newDiaChi, newName, newPhoneNumber);
                dismiss();
            }
        });
    }
}
