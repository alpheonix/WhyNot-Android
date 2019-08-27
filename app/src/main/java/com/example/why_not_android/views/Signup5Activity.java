package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
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

public class Signup5Activity extends AppCompatActivity {

    @BindView(R.id.signup5twitter)
    EditText twitter;
    @BindView(R.id.signup5insta)
    EditText insta;
    @BindView(R.id.signup5facebook)
    EditText facebook;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup5);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);

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
        RequestBody twitter =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getTwitter());
        RequestBody insta =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getInsta());
        RequestBody facebook =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Signup.getClient().getFacebook());

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
                twitter,
                insta,
                facebook,
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
                    Intent intent = new Intent(Signup5Activity.this, Home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(Signup5Activity.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SessionDTO> call, Throwable t) {
                Log.d("toz", t.toString());
                Toast.makeText(Signup5Activity.this, "CA MARCHE PAS", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @OnClick(R.id.signup5Button)
    void submit() {
        String twitterString = twitter.getText().toString();
        String instaString = insta.getText().toString();
        String facebookString = facebook.getText().toString();

        if (twitterString.isEmpty()) {
            twitterString = "";
        }
        if (instaString.isEmpty()) {
            instaString = "";
        }
        if(facebookString.isEmpty()){
            instaString = "";
        }

            Signup.getClient().setTwitter(twitterString);
            Signup.getClient().setInsta(instaString);
            Signup.getClient().setFacebook(facebookString);
            signup(Signup.getClient().getFileUri(), Signup.getClient().getImageFile());


    }
}
