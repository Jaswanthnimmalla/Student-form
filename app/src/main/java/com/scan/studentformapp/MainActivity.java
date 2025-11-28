package com.scan.studentformapp;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.scan.studentformapp.model.ApiResponse;
import com.scan.studentformapp.network.RetrofitClient;
import com.scan.studentformapp.utils.FileUtils;
import com.scan.studentformapp.utils.ValidationUtils;

import java.io.File;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    private EditText etCustomerName, etIdNumber, etEmail, etPhoneNumber;
    private Button btnSelectPan, btnSelectAadhaar, btnSubmit;
    private TextView tvPanFileName, tvAadhaarFileName;
    private ProgressBar progressBar;
    private RelativeLayout loadingOverlay;
    private TextView tvMarquee;

    private Uri panFileUri = null;
    private Uri aadhaarFileUri = null;

    private ActivityResultLauncher<String> panFileLauncher;
    private ActivityResultLauncher<String> aadhaarFileLauncher;
    private ActivityResultLauncher<String[]> permissionLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Setup custom centered action bar
        setupActionBar();

        initializeViews();
        setupFilePickers();
        setupClickListeners();
    }

    private void setupActionBar() {
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayShowCustomEnabled(true);
            getSupportActionBar().setDisplayShowTitleEnabled(false);
            getSupportActionBar().setCustomView(R.layout.custom_action_bar);
        }
    }

    private void initializeViews() {
        etCustomerName = findViewById(R.id.etCustomerName);
        etIdNumber = findViewById(R.id.etIdNumber);
        etEmail = findViewById(R.id.etEmail);
        etPhoneNumber = findViewById(R.id.etPhoneNumber);

        btnSelectPan = findViewById(R.id.btnSelectPan);
        btnSelectAadhaar = findViewById(R.id.btnSelectAadhaar);
        btnSubmit = findViewById(R.id.btnSubmit);

        tvPanFileName = findViewById(R.id.tvPanFileName);
        tvAadhaarFileName = findViewById(R.id.tvAadhaarFileName);

        progressBar = findViewById(R.id.progressBar);
        loadingOverlay = findViewById(R.id.loadingOverlay);

        // Initialize and start marquee
        tvMarquee = findViewById(R.id.tvMarquee);
        tvMarquee.setSelected(true);
    }

    private void setupFilePickers() {
        // PAN file picker
        panFileLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        long sizeKB = FileUtils.getFileSizeInKB(this, uri);
                        if (ValidationUtils.isFileSizeValid(sizeKB)) {
                            panFileUri = uri;
                            String fileName = FileUtils.getFileName(this, uri);
                            tvPanFileName.setText("Selected: " + fileName + " (" + sizeKB + " KB)");
                            tvPanFileName.setVisibility(View.VISIBLE);
                            Toast.makeText(this, "PAN file selected", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(this, "PAN file must be between 90-120 KB (Current: " + sizeKB + " KB)", Toast.LENGTH_LONG).show();
                        }
                    }
                }
        );

        // Aadhaar file picker
        aadhaarFileLauncher = registerForActivityResult(
                new ActivityResultContracts.GetContent(),
                uri -> {
                    if (uri != null) {
                        aadhaarFileUri = uri;
                        String fileName = FileUtils.getFileName(this, uri);
                        tvAadhaarFileName.setText("Selected: " + fileName);
                        tvAadhaarFileName.setVisibility(View.VISIBLE);
                        Toast.makeText(this, "Aadhaar file selected", Toast.LENGTH_SHORT).show();
                    }
                }
        );

        // Permission launcher
        permissionLauncher = registerForActivityResult(
                new ActivityResultContracts.RequestMultiplePermissions(),
                result -> {
                    boolean allGranted = true;
                    for (Boolean granted : result.values()) {
                        if (!granted) {
                            allGranted = false;
                            break;
                        }
                    }
                    if (!allGranted) {
                        Toast.makeText(this, "Permission denied. Cannot select files.", Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }

    private void setupClickListeners() {
        btnSelectPan.setOnClickListener(v -> {
            if (checkPermissions()) {
                panFileLauncher.launch("*/*");
            } else {
                requestPermissions();
            }
        });

        btnSelectAadhaar.setOnClickListener(v -> {
            if (checkPermissions()) {
                aadhaarFileLauncher.launch("*/*");
            } else {
                requestPermissions();
            }
        });

        btnSubmit.setOnClickListener(v -> submitForm());
    }

    private boolean checkPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_MEDIA_IMAGES)
                    == PackageManager.PERMISSION_GRANTED;
        } else {
            return ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE)
                    == PackageManager.PERMISSION_GRANTED;
        }
    }

    private void requestPermissions() {
        String[] permissions;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            permissions = new String[]{Manifest.permission.READ_MEDIA_IMAGES};
        } else {
            permissions = new String[]{Manifest.permission.READ_EXTERNAL_STORAGE};
        }
        permissionLauncher.launch(permissions);
    }

    private void submitForm() {
        String customerName = etCustomerName.getText().toString().trim();
        String idNumber = etIdNumber.getText().toString().trim();
        String email = etEmail.getText().toString().trim();
        String phoneNumber = etPhoneNumber.getText().toString().trim();

        Long panSizeKB = panFileUri != null ? FileUtils.getFileSizeInKB(this, panFileUri) : null;
        boolean hasAadhaar = aadhaarFileUri != null;

        String validationError = ValidationUtils.validateFormData(
                customerName, idNumber, email, phoneNumber, panSizeKB, hasAadhaar
        );

        if (validationError != null) {
            Toast.makeText(this, validationError, Toast.LENGTH_LONG).show();
            return;
        }

        // Show loading
        btnSubmit.setEnabled(false);
        loadingOverlay.setVisibility(View.VISIBLE);

        // Get files
        File panFile = FileUtils.getFileFromUri(this, panFileUri);
        File aadhaarFile = FileUtils.getFileFromUri(this, aadhaarFileUri);

        if (panFile == null || aadhaarFile == null) {
            Toast.makeText(this, "Error reading files", Toast.LENGTH_SHORT).show();
            loadingOverlay.setVisibility(View.GONE);
            btnSubmit.setEnabled(true);
            return;
        }

        // Create multipart body parts
        MultipartBody.Part panPart = FileUtils.createMultipartBodyPart(panFile, "panFile");
        MultipartBody.Part aadhaarPart = FileUtils.createMultipartBodyPart(aadhaarFile, "aadhaarFile");

        // Make API call
        Call<ApiResponse> call = RetrofitClient.getApiService().createStudent(
                FileUtils.createRequestBody(customerName),
                FileUtils.createRequestBody(idNumber),
                FileUtils.createRequestBody(email),
                FileUtils.createRequestBody(phoneNumber),
                panPart,
                aadhaarPart
        );

        call.enqueue(new Callback<ApiResponse>() {
            @Override
            public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                loadingOverlay.setVisibility(View.GONE);
                btnSubmit.setEnabled(true);

                if (response.isSuccessful() && response.body() != null && response.body().isSuccess()) {
                    Intent intent = new Intent(MainActivity.this, SuccessActivity.class);
                    intent.putExtra("message", response.body().getMessage());
                    intent.putExtra("customerName", customerName);
                    startActivity(intent);
                    finish();
                } else {
                    String message = response.body() != null ? response.body().getMessage() : "Failed to submit form";
                    Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ApiResponse> call, Throwable t) {
                loadingOverlay.setVisibility(View.GONE);
                btnSubmit.setEnabled(true);
                Toast.makeText(MainActivity.this, "Error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                t.printStackTrace();
            }
        });
    }
}
