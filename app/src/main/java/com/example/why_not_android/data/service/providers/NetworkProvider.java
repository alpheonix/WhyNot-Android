package com.example.why_not_android.data.service.providers;


import android.util.Log;

import com.example.why_not_android.data.Models.User;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.EventDTO;
import com.example.why_not_android.data.dto.EventsDTO;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.dto.mapper.EventMapper;
import com.example.why_not_android.data.Models.Event;
import com.example.why_not_android.data.dto.mapper.UserMapper;
import com.example.why_not_android.data.service.EventService;
import com.example.why_not_android.data.service.UserService;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkProvider {

    private EventService eventService;
    private UserService userService;
    private static NetworkProvider instance;
    private static Retrofit retrofit = null;
    private final static String localBaseUrl = "http://10.0.2.2:3000/";
    private final static String prodBaseUrl = "https://why-not-api.herokuapp.com/";

    public static NetworkProvider getInstance() {
        if (instance == null) {
            instance = new NetworkProvider();
        }
        return instance;
    }

    public static Retrofit getClient() {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(localBaseUrl)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    private NetworkProvider() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(localBaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        eventService = retrofit.create(EventService.class);
        userService = retrofit.create(UserService.class);
    }

    public void getEvents(Listener<List<Event>> listener) {
        eventService.getEvents(SharedPref.getToken()).enqueue(new Callback<EventsDTO>() {

            @Override
            public void onResponse(Call<EventsDTO> call, Response<EventsDTO> response) {
                if (response.isSuccessful()) {
                    EventsDTO eventsDTOList = response.body();
                    List<EventDTO> event = eventsDTOList.getEventDTOArrayList();
                    List<Event> eventList = EventMapper.map(event);
                    listener.onSuccess(eventList);
                }
            }

            @Override
            public void onFailure(Call<EventsDTO> call, Throwable t) {
                listener.onError(t);
                Log.d("toz", "fail");
            }
        });
    }

    public void getMyEvents(Listener<List<Event>> listener) {
        eventService.getMyEvents(SharedPref.getToken()).enqueue(new Callback<EventsDTO>() {

            @Override
            public void onResponse(Call<EventsDTO> call, Response<EventsDTO> response) {
                if (response.isSuccessful()) {
                    EventsDTO eventsDTOList = response.body();
                    List<EventDTO> event = eventsDTOList.getEventDTOArrayList();
                    if (event != null) {
                        List<Event> eventList = EventMapper.map(event);
                        listener.onSuccess(eventList);
                    }
                }
            }

            @Override
            public void onFailure(Call<EventsDTO> call, Throwable t) {
                listener.onError(t);
                Log.d("toz", "fail");
            }
        });
    }

    public void getMatch(Listener<List<User>> listener) {
        userService.getMatch(SharedPref.getToken()).enqueue(new Callback<List<UserDTO>>() {

            @Override
            public void onResponse(Call<List<UserDTO>> call, Response<List<UserDTO>> response) {
                if (response.isSuccessful()) {
                    List<UserDTO> userDTOList = response.body();
                    List<User> userList = UserMapper.map(userDTOList);
                    listener.onSuccess(userList);
                }
            }

            @Override
            public void onFailure(Call<List<UserDTO>> call, Throwable t) {
                listener.onError(t);
                Log.d("toz", "fail");
            }
        });
    }

    public interface Listener<T> {
        void onSuccess(T data);

        void onError(Throwable t);
    }
}
