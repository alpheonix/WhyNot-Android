package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Modify;
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.MyPermissions;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ModifyUser extends AppCompatActivity {

    private int EXTERNAL_STORAGE_PERMISSION = 1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    @BindView(R.id.modifyMailEdit)
    EditText mailEdit;
    @BindView(R.id.modifyUsernameEdit)
    EditText usernameEdit;
    @BindView(R.id.modifyPhotoImgView)
    ImageView imageView;
    @BindView(R.id.modifyBio)
    EditText bioEdit;
    @BindView(R.id.modifyradioGroup)
    RadioGroup prefRadio;
    @BindView(R.id.modifyActivite)
    RadioGroup activiteRadio;
    @BindView(R.id.modifyradioGroupFilm)
    RadioGroup filmRadio;
    @BindView(R.id.modifyradioGroupMusique)
    RadioGroup musiqueRadio;
    int film = -1;
    int activite = -1;
    int musique = -1;
    int preferences = -1;
    private SharedPreferences sharedPreferences;


    private MyPermissions myPermissions;
    private String imgPath = null;
    private Bitmap bitmap;
    private Uri fileUri = null;
    private File imageFile = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_modify_user);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);

        myPermissions = MyPermissions.getInstance(this);
        prefRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.modify3radioButtonHomme) {
                preferences = 0;
            } else if (checkedId == R.id.modifyradioButtonFemme) {
                preferences = 1;
            } else {
                preferences = 2;
            }
            Log.d("pref",""+preferences);
        });
        filmRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.modifyradioButtonScienceFiction) {
                film = 0;
            } else if (checkedId == R.id.modifyradioButtonAction) {
                film = 1;
            } else if(checkedId == R.id.modifyradioButtonFantasie) {
                film = 2;
            }else{
                film = 3;

            }
        });

        activiteRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.modifyradioButtonCinema) {
                activite = 0;
            } else if (checkedId == R.id.modifyradioButtonSport) {
                activite = 1;
            } else if(checkedId == R.id.modifyradioButtonVoyage) {
                activite = 2;
            }else{
                activite = 3;

            }
        });

        musiqueRadio.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.modifyradioButtonRap) {
                musique = 0;
            } else if (checkedId == R.id.modifyradioButtonRock) {
                musique = 1;
            } else if(checkedId == R.id.modifyradioButtonPop) {
                musique = 2;
            }else{
                musique = 3;

            }
        });


    }

    @OnClick(R.id.modifyButton)
    void modifyBtn() {

        if (!isEmailValid(mailEdit.getText().toString())) {
            Toast.makeText(this, "Email incorrect", Toast.LENGTH_SHORT).show();
        } else if (!isUsernameValid(usernameEdit.getText().toString()) || usernameEdit.getText().toString().length() < 2) {
            Toast.makeText(this, "Nom d'utilisateur incorrect", Toast.LENGTH_SHORT).show();
        }  else if (fileUri == null || imageFile == null) {
            Toast.makeText(this, "Selectionner une image", Toast.LENGTH_SHORT).show();
        }else if (preferences == -1) {
            Toast.makeText(this, "Selectionner une préférence", Toast.LENGTH_SHORT).show();
        } else if (bioEdit.getText().toString().length() == 0) {
            Toast.makeText(this, "Merci d'entrer une bio", Toast.LENGTH_SHORT).show();
        } else if (film == -1) {
            Toast.makeText(this, "Selectionner un genre de film favoris", Toast.LENGTH_SHORT).show();
        } else if (activite == -1) {
            Toast.makeText(this, "Selectionner une activité favorite", Toast.LENGTH_SHORT).show();
        } else if(musique == -1){
            Toast.makeText(this, "Selectionner un genre musicale favoris", Toast.LENGTH_SHORT).show();
        }else{
            Modify.getClient().setEmail(mailEdit.getText().toString());
            Modify.getClient().setUsername(usernameEdit.getText().toString());
            Modify.getClient().setFileUri(fileUri);
            Modify.getClient().setImageFile(imageFile);
            Modify.getClient().setBio(bioEdit.getText().toString());
            Modify.getClient().setPreferences(preferences);
            Modify.getClient().setActivite(activite);
            Modify.getClient().setFilm(film);
            Modify.getClient().setMusique(musique);
            modify(Modify.getClient().getFileUri(), Modify.getClient().getImageFile());
        }

    }


    private boolean isEmailValid(String email) {
        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    private Boolean isUsernameValid(String s) {
        if (s.matches(".*\\d.*")) {
            return false;
        }
        return true;
    }
    public String getRealPathFromURI(Uri contentUri) {
        String[] proj = {MediaStore.Audio.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        int column_index = cursor.getColumnIndexOrThrow(MediaStore.Audio.Media.DATA);
        cursor.moveToFirst();
        return cursor.getString(column_index);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_GALLERY) {
            if(resultCode == RESULT_OK){
                Uri selectedImage = data.getData();
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, bytes);

                    imgPath = getRealPathFromURI(selectedImage);
                    imageFile = new File(imgPath);
                    this.fileUri = selectedImage;
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == EXTERNAL_STORAGE_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                myPermissions.showChoiceDialog();
            }
        }
    }
    @OnClick(R.id.modifyPhotoButton)
    void chooseImage() {
        myPermissions.askMediaPermission();
    }

    private void modify(Uri fileUri, File imageFile) {
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
                        okhttp3.MultipartBody.FORM, Modify.getClient().getEmail());
        RequestBody username =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Modify.getClient().getUsername());
        RequestBody bio =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, Modify.getClient().getBio());
        RequestBody preference =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Modify.getClient().getPreferences()));
        RequestBody film =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Modify.getClient().getFilm()));
        RequestBody activite =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Modify.getClient().getActivite()));
        RequestBody musique =
                RequestBody.create(
                        okhttp3.MultipartBody.FORM, String.valueOf(Modify.getClient().getMusique()));
        String token = sharedPreferences.getString("token", "");

        // finally, execute the request
        Call<SessionDTO> call = sessionService.modify(
                token,
                email,
                username,
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
                    Intent intent = new Intent(ModifyUser.this, Home.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ModifyUser.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();

                }

            }

            @Override
            public void onFailure(Call<SessionDTO> call, Throwable t) {
                Log.d("toz", t.toString());
                Toast.makeText(ModifyUser.this, "CA MARCHE PAS", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
