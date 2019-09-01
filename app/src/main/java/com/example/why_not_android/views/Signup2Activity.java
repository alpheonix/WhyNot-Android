package com.example.why_not_android.views;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
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
import com.example.why_not_android.data.Models.Signup;
import com.example.why_not_android.data.MyPermissions;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class Signup2Activity extends AppCompatActivity {

    private int EXTERNAL_STORAGE_PERMISSION = 1;
    private final int PICK_IMAGE_CAMERA = 1, PICK_IMAGE_GALLERY = 2;

    private DatePickerDialog datePickerDialog;
    @BindView(R.id.signup2Birthdate)
    EditText birthdateEdt;
    @BindView(R.id.signup2radioGroup)
    RadioGroup genderGr;
    @BindView(R.id.photoImgView)
    ImageView imageView;
    private int gender = -1;

    private MyPermissions myPermissions;
    private String imgPath = null;
    private Bitmap bitmap;
    private Uri fileUri = null;
    private File imageFile = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup2);
        ButterKnife.bind(this);
        initDatePicker();
        myPermissions = MyPermissions.getInstance(this);
        genderGr.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.radioButtonHomme) {
                gender = 0;
            } else {
                gender = 1;
            }
        });
    }

    private void initDatePicker() {
        Calendar calendar = Calendar.getInstance();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        calendar.set(2001, 0, 0);

        datePickerDialog = new DatePickerDialog(this, (view, year1, month1, dayOfMonth) -> {
            String format = "dd/MM/yyyy";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(format, Locale.FRANCE);

            calendar.set(Calendar.YEAR, year1);
            calendar.set(Calendar.MONTH, month1);
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            birthdateEdt.setText(simpleDateFormat.format(calendar.getTime()));
        }, (year), month, day);
        datePickerDialog.getDatePicker().setMaxDate(calendar.getTimeInMillis());
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
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

    @OnClick(R.id.photoButton)
    void chooseImage() {
        myPermissions.askMediaPermission();
    }

    @OnClick(R.id.signup2Birthdate)
    void onBirthdateClick() {
        datePickerDialog.show();
    }

    @OnClick(R.id.signup2Button)
    void nextSignupActivity() {
        if (fileUri == null || imageFile == null) {
            Toast.makeText(this, "Selectionner une image", Toast.LENGTH_SHORT).show();
        } else if (birthdateEdt.getText().toString().length() == 0) {
            Toast.makeText(this, "Entrer une date de naissance", Toast.LENGTH_SHORT).show();
        } else if (gender == -1) {
            Toast.makeText(this, "Selectionner un genre", Toast.LENGTH_SHORT).show();
        } else {
            Signup.getClient().setFileUri(fileUri);
            Signup.getClient().setImageFile(imageFile);
            Signup.getClient().setGender(gender);
            Signup.getClient().setBirthdate(birthdateEdt.getText().toString());
            Intent intent = new Intent(this, Signup3Activity.class);
            startActivity(intent);
        }
    }
}

