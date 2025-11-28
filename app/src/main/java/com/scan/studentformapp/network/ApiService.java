package com.scan.studentformapp.network;

import com.scan.studentformapp.model.ApiResponse;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiService {

    @Multipart
    @POST("/api/students/create")
    Call<ApiResponse> createStudent(
            @Part("customerName") RequestBody customerName,
            @Part("idNumber") RequestBody idNumber,
            @Part("email") RequestBody email,
            @Part("phoneNumber") RequestBody phoneNumber,
            @Part MultipartBody.Part panFile,
            @Part MultipartBody.Part aadhaarFile
    );
}
