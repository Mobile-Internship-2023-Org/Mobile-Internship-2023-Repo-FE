package com.example.foody_app.dialogs;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import androidx.annotation.NonNull;

import com.example.foody_app.R;
import com.example.foody_app.utils.OnThanhToanChangedListener;

public class ChangePaymentMethodDialog extends Dialog {

    private OnThanhToanChangedListener listener;

    public ChangePaymentMethodDialog(@NonNull Context context, OnThanhToanChangedListener listener) {
        super(context);
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_change_thanh_toan);

        RadioGroup radioGroup = findViewById(R.id.radioGroup);
        Button btnSavePaymentMethod = findViewById(R.id.btnSavePaymentMethod);

        btnSavePaymentMethod.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedId = radioGroup.getCheckedRadioButtonId();

                if (selectedId != -1) {
                    RadioButton radioButton = findViewById(selectedId);
                    String selectedPaymentMethod = radioButton.getText().toString();
                    listener.onPaymentMethodChanged(selectedPaymentMethod);
                    dismiss();
                } else {

                }
            }
        });
    }
}
