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

    private void signup(Uri fileUri, File imageFile) {
        SessionService sessionService;
        sessionService = NetworkProvider.getClient().create(SessionService.class);

        // create RequestBody instance from file
        RequestBody requestFile =
                RequestBody.create(
                        MediaType.parse(getContentResolver().getType(fileUri)),
                        imageFile
                );

        // MultipartBody.Part is used to send also the actual file name
        MultipartBody.Part body =
                MultipartBody.Part.createFormData("image", imageFile.getName(), requestFile);

        // add another part within the multipart request
        RequestBody email =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getEmail());
        RequestBody username =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getUsername());
        RequestBody password =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getPassword());
        RequestBody gender =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getGender()));
        RequestBody birthdate =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getBirthdate());
        RequestBody bio =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getBio());
        RequestBody preference =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getPreferences()));
        RequestBody film =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getFilm()));
        RequestBody activite =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getActivite()));
        RequestBody musique =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Signup.getClient().getMusique()));

        // finally, execute the request
        Call<SessionDTO> call = sessionService.signup(
                email,
                username,
                password,
                gender,
                birthdate,
                bio,
                preference,
                film,
                activite,
                musique,
                body);
        call.enqueue(new Callback<SessionDTO>() {
            @Override
            public void onResponse(Call<SessionDTO> call,
                                   Response<SessionDTO> response) {
                if (response.isSuccessful()) {
                    SessionDTO sessionDTO = response.body();
                    sharedPreferences.edit()
                            .putString("token", sessionDTO.getToken())
                            .apply();
                    Intent intent = new Intent(Signup4Activity.this, Home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Signup4Activity.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SessionDTO> call, Throwable t) {
                Log.d("toz", t.toString());
                Toast.makeText(Signup4Activity.this, "CA MARCHE PAS", Toast.LENGTH_SHORT).show();
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
            signup(Signup.getClient().getFileUri(), Signup.getClient().getImageFile());
        }
    }
}
