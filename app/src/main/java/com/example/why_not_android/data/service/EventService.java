package com.example.why_not_android.data.service;


import com.example.why_not_android.data.dto.EventsDTO;
import com.example.why_not_android.data.dto.RegisterDTO;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Query;


public interface EventService {

    @GET("events")
    Call<EventsDTO> getEvents(@Header("x-access-token") String token);

    @POST("events/register")
    Call<RegisterDTO> register(@Header("x-access-token") String token, @Body RegisterDTO registerDTO);

    @POST("events/usregister")
    Call<RegisterDTO> unregister(@Header("x-access-token") String token, @Body RegisterDTO registerDTO);

    @GET("events/myevents")
    Call<EventsDTO> getMyEvents(@Header("x-access-token") String token);
}
