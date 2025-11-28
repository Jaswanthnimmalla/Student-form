package com.scan.studentformapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

import androidx.activity.OnBackPressedCallback;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

public class SuccessActivity extends AppCompatActivity {

    private TextView tvMessage;
    private TextView tvCustomerName;
    private Button btnBackToHome;
    private CardView checkmarkCard;
    private CardView contentCard;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_success);

        tvMessage = findViewById(R.id.tvMessage);
        tvCustomerName = findViewById(R.id.tvCustomerName);
        btnBackToHome = findViewById(R.id.btnBackToHome);
        checkmarkCard = findViewById(R.id.checkmarkCard);
        contentCard = findViewById(R.id.contentCard);

        String message = getIntent().getStringExtra("message");
        String customerName = getIntent().getStringExtra("customerName");

        if (message != null) {
            tvMessage.setText(message);
        }

        if (customerName != null) {
            tvCustomerName.setText("Thank you, " + customerName);
        }

        // Start animations
        animateCheckmark();
        animateContentCard();

        btnBackToHome.setOnClickListener(v -> navigateToMainActivity());

        // Handle back button press
        getOnBackPressedDispatcher().addCallback(this, new OnBackPressedCallback(true) {
            @Override
            public void handleOnBackPressed() {
                navigateToMainActivity();
            }
        });
    }

    private void navigateToMainActivity() {
        Intent intent = new Intent(SuccessActivity.this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    private void animateCheckmark() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.checkmark_animation);
        checkmarkCard.startAnimation(animation);
    }

    private void animateContentCard() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        contentCard.startAnimation(animation);
    }
}
