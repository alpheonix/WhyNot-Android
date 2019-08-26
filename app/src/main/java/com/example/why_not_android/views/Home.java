package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.MatchDTO;
import com.example.why_not_android.data.dto.RegisterResultDTO;
import com.example.why_not_android.data.dto.UserDTO;
import com.example.why_not_android.data.service.UserService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Home extends MenuActivity implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.activity_home_iv)
    ImageView imageView;
    @BindView(R.id.activity_home_user_description_tv)
    TextView textView;
    @BindView(R.id.activity_home_like_button)
    Button likeButton;
    @BindView(R.id.activity_home_dislike_button)
    Button dislikeButton;
    @BindView(R.id.activity_home_empty_tv)
    TextView emptyTextView;
    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.drawer_layout)
    DrawerLayout drawerLayout;
    @BindView(R.id.nav_view)
    NavigationView navigationView;

    private SharedPreferences sharedPreferences;
    private ArrayList<UserDTO> userDTOList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);
        setupToolbar();
        hideButtons();
        getUsers();
    }

    @OnClick(R.id.activity_home_like_button)
    void like() {
        setLiked(userDTOList.get(0).get_id());
    }

    @OnClick(R.id.activity_home_dislike_button)
    void dislike() {
        setViewed(userDTOList.get(0).get_id());
        cleanUserList();
    }

    void getUsers() {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");
Log.d("test",token);
        Call<ArrayList<UserDTO>> userDTOCall = userService.getUsers(token);
        userDTOCall.enqueue(new Callback<ArrayList<UserDTO>>() {
            @Override
            public void onResponse(Call<ArrayList<UserDTO>> call, Response<ArrayList<UserDTO>> response) {
                if (response.isSuccessful()) {
                    if (response.body().size() != 0) {
                        userDTOList = response.body();
                        UserDTO userDTO = userDTOList.get(0);
                        textView.setText(userDTO.getUsername() + "\n" + userDTO.getBirthdate());
                        String url = userDTO.getPhoto();
                        Log.d("toz", url);
                        //url = url.replace("localhost", "10.0.2.2");
                        Glide.with(Home.this).load(url).into(imageView);
                        displayButtons();
                    } else {
                        emptyUserList();
                    }
                } else {
                    try {
                        //Return to MainActivity if the user is no longer connected.
                        Intent intent = new Intent(Home.this, LoginActivity.class);
                        startActivity(intent);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<UserDTO>> call, Throwable t) {
                Log.d("toz", "CA MARCHE PAS CA MARCHE PAS QUAND JE CLIQUE SUR LE LIEN CA MARCHE PAS");
            }
        });

    }

    void setViewed(String id) {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");

        Call<RegisterResultDTO> registerResultDTOCall = userService.setViewed(token, id);
        registerResultDTOCall.enqueue(new Callback<RegisterResultDTO>() {
            @Override
            public void onResponse(Call<RegisterResultDTO> call, Response<RegisterResultDTO> response) {

            }

            @Override
            public void onFailure(Call<RegisterResultDTO> call, Throwable t) {
                Toast.makeText(Home.this, "CA MARCHE PAS :(", Toast.LENGTH_SHORT).show();
            }
        });
    }

    void setLiked(String id) {
        UserService userService;
        userService = NetworkProvider.getClient().create(UserService.class);
        String token = sharedPreferences.getString("token", "");

        Call<MatchDTO> matchDTOCall = userService.like(token, id);
        matchDTOCall.enqueue(new Callback<MatchDTO>() {
            @Override
            public void onResponse(Call<MatchDTO> call, Response<MatchDTO> response) {
                if (response.isSuccessful()) {
                    MatchDTO matchDTO = response.body();
                    if (matchDTO.getMatch()) {
                        ViewDialog matchDialog = new ViewDialog();
                        String username = userDTOList.get(0).getUsername();
                        String imageURL = userDTOList.get(0).getPhoto();
                        setViewed(id);
                        cleanUserList();
                        matchDialog.showDialog(Home.this, username, imageURL, userDTOList);
                    } else {
                        setViewed(id);
                        cleanUserList();
                    }

                } else {
                    Log.d("CA MARCHE", "CA MARCHE PAS :(");
                }
            }

            @Override
            public void onFailure(Call<MatchDTO> call, Throwable t) {
                Log.d("CA MARCHE", "CA FAIL :(");
            }
        });
    }

    private void emptyUserList() {
        imageView.setVisibility(View.INVISIBLE);
        textView.setVisibility(View.INVISIBLE);
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        emptyTextView.setText(getString(R.string.home_activity_no_users_found));
        emptyTextView.setVisibility(View.VISIBLE);
    }

    private void cleanUserList() {
        if (userDTOList.size() == 1) {
            getUsers();
        } else {
            userDTOList.remove(0);
            UserDTO userDTO = userDTOList.get(0);
            textView.setText(userDTO.getUsername() + "\n" + userDTO.getPreference());
            Glide.with(Home.this).load(userDTO.getPhoto().replace("localhost", "10.0.2.2")).into(imageView);
        }
    }

    private void hideButtons() {
        likeButton.setVisibility(View.INVISIBLE);
        dislikeButton.setVisibility(View.INVISIBLE);
        emptyTextView.setVisibility(View.INVISIBLE);
    }

    private void displayButtons() {
        likeButton.setVisibility(View.VISIBLE);
        dislikeButton.setVisibility(View.VISIBLE);
    }

    private void setupToolbar() {
        setSupportActionBar(toolbar);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @OnClick(R.id.activity_home_user_description_tv)
    void detail() {
        Intent intent = new Intent(Home.this, DetailUser.class);
        intent.putExtra("userName", userDTOList.get(0).getUsername());
        intent.putExtra("userBio", userDTOList.get(0).getBio());
        intent.putExtra("userBirth", userDTOList.get(0).getBirthdate());
        intent.putExtra("userPic", userDTOList.get(0).getPhoto());
        intent.putExtra("userid", userDTOList.get(0).get_id());
        startActivity(intent);
    }
}
