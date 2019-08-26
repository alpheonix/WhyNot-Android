package com.example.why_not_android.views;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.Signup;
import com.google.firebase.auth.FirebaseAuth;
//import com.example.why_not_android.data.Models.Signup;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class SignupActivity extends AppCompatActivity {

    @BindView(R.id.signupMailEdit)
    EditText mailEdit;
    @BindView(R.id.signupUsernameEdit)
    EditText usernameEdit;
    @BindView(R.id.signupPasswordEdit)
    EditText passwordEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.signupButton)
    void nextSignupActivity() {
        if (!isEmailValid(mailEdit.getText().toString())) {
            Toast.makeText(this, "Email incorrect", Toast.LENGTH_SHORT).show();
        } else if (!isUsernameValid(usernameEdit.getText().toString()) || usernameEdit.getText().toString().length() < 2) {
            Toast.makeText(this, "Nom d'utilisateur incorrect", Toast.LENGTH_SHORT).show();
        } else if (passwordEdit.getText().toString().length() < 5) {
            Toast.makeText(this, "Password trop court", Toast.LENGTH_SHORT).show();
        } else {
            Signup.getClient().setEmail(mailEdit.getText().toString());
            Signup.getClient().setUsername(usernameEdit.getText().toString());
            Signup.getClient().setPassword(passwordEdit.getText().toString());
            Intent intent = new Intent(SignupActivity.this, Signup2Activity.class);
            startActivity(intent);
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

}
