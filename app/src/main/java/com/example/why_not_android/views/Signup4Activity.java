package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.io.File;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Signup4Activity extends AppCompatActivity {



    @BindView(R.id.test)
    RadioGroup activiteRadio;
    @BindView(R.id.signup4radioGroupFilm)
    RadioGroup filmRadio;
    @BindView(R.id.signup4radioGroupMusique)
    RadioGroup musiqueRadio;
    int film = -1;
    int activite = -1;
    int musique = -1;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_signup4);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);

        filmRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup4radioButtonScienceFiction) {
                film = 0;
            } else if (checkedId == R.id.signup4radioButtonAction) {
                film = 1;
            } else if(checkedId == R.id.signup4radioButtonFantasie) {
                film = 2;
            }else{
                film = 3;

            }
        });

        activiteRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup4radioButtonCinema) {
                activite = 0;
            } else if (checkedId == R.id.signup4radioButtonSport) {
                activite = 1;
            } else if(checkedId == R.id.signup4radioButtonVoyage) {
                activite = 2;
            }else{
                activite = 3;

            }
        });

        musiqueRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.signup4radioButtonRap) {
                musique = 0;
            } else if (checkedId == R.id.signup4radioButtonRock) {
                musique = 1;
            } else if(checkedId == R.id.signup4radioButtonPop) {
                musique = 2;
            }else{
                musique = 3;

            }
        });
    }




    @OnClick(R.id.signup2Button)
    void submit() {
        if (film == -1) {
            Toast.makeText(this, "Selectionner un genre de film favoris", Toast.LENGTH_SHORT).show();
        } else if (activite == -1) {
            Toast.makeText(this, "Selectionner une activité favorite", Toast.LENGTH_SHORT).show();
        } else if(musique == -1){
            Toast.makeText(this, "Selectionner un genre musicale favoris", Toast.LENGTH_SHORT).show();
        }else{
            Signup.getClient().setActivite(activite);
            Signup.getClient().setFilm(film);
            Signup.getClient().setMusique(musique);
            Intent intent = new Intent(this, Signup5Activity.class);
            startActivity(intent);
        }
    }
}
