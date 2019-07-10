package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.LoginDTO;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.dto.UsersListDTO;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface SessionService {
    @POST("users/login")
    Call<SessionDTO> login(@Body LoginDTO loginDTO);

    @GET("users/")
    Call<ArrayList<UserDTO>> getUsers(@Header("x-access-token") String token);

    @Multipart
    @POST("users/signup")
    Call<SessionDTO> signup(
            @Part("email") RequestBody email,
            @Part("username") RequestBody username,
            @Part("password") RequestBody password,
            @Part("gender") RequestBody gender,
            @Part("birthdate") RequestBody birthdate,
            @Part("bio") RequestBody bio,
            @Part("preference") RequestBody preference,
            @Part MultipartBody.Part file
    );*/
}