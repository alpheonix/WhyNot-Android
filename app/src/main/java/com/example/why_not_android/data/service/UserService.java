package com.example.why_not_android.data.service;

import com.example.why_not_android.data.dto.MatchDTO;
import com.example.why_not_android.data.dto.RegisterResultDTO;
import com.example.why_not_android.data.dto.UserDTO;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.PUT;
import retrofit2.http.Query;

public interface UserService {
    @GET("users")
    Call<ArrayList<UserDTO>> getUsers(@Header("x-access-token") String token);

    @GET("users/modify")
    Call<ArrayList<UserDTO>> getUser(@Header("x-access-token") String token);

    @PATCH("users/viewers")
    Call<RegisterResultDTO> setViewed(@Header("x-access-token") String token, @Query("_id") String id);

    @PUT("users/like")
    Call<MatchDTO> like(@Header("x-access-token") String token, @Query("_id") String id);

    @GET("users/match")
    Call<List<UserDTO>> getMatch(@Header("x-access-token") String token);
}