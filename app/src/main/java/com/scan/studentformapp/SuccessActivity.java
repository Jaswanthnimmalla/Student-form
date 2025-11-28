package com.scan.studentformapp;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class SuccessActivity extends AppCompatActivity {

    private TextView tvMessage;
    private TextView tvCustomerName;
    private Button btnBackToHome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        tvMessage = findViewById(R.id.tvMessage);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        btnBackToHome = findViewById(R.id.btnBackToHome);

        String message = getIntent().getStringExtra("message");
        String customerName = getIntent().getStringExtra("customerName");

        if (message != null) {
            tvMessage.setText(message);
        }

        if (customerName != null) {
            tvCustomerName.setText("Thank you, " + customerName);
        }

        btnBackToHome.setOnClickListener(v -> finish());
    }
}
