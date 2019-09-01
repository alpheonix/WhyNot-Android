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
import android.widget.ImageView;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Modify;
import com.example.why_not_android.data.MyPermissions;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.SessionDTO;
import com.example.why_not_android.data.service.SessionService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import java.io.ByteArrayOutputStream;
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

public class ModifyPhoto extends AppCompatActivity {

    private int EXTERNAL_STORAGE_PERMISSION = 1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    @BindView(R.id.modifyPhotoImgView)
    ImageView imageView;

    private MyPermissions myPermissions;
    private String imgPath = null;
    private Bitmap bitmap;
    private Uri fileUri = null;
    private File imageFile = null;
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify_photo);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);

        myPermissions = MyPermissions.getInstance(this);

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

    @OnClick(R.id.modifyPhotoValidate)
    void modifyBtn() {
        Modify.getClient().setFileUri(fileUri);
        Modify.getClient().setImageFile(imageFile);

        modify(Modify.getClient().getFileUri(), Modify.getClient().getImageFile());
    }

    private void modify(Uri fileUri, File imageFile){
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

            String token = sharedPreferences.getString("token", "");

            // finally, execute the request
            Call<SessionDTO> call = sessionService.modifyPhoto(
                    token,
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
                        Intent intent = new Intent(ModifyPhoto.this, Home.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(ModifyPhoto.this, "Erreur lors de l'enregistrement", Toast.LENGTH_SHORT).show();

                    }

                }

                @Override
                public void onFailure(Call<SessionDTO> call, Throwable t) {
                    Log.d("toz", t.toString());
                    Toast.makeText(ModifyPhoto.this, "CA MARCHE PAS", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }


