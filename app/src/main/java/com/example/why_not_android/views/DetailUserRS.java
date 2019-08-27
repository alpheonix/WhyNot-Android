package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailUserRS extends AppCompatActivity {
    @BindView(R.id.activity_detail_user_rs_bio)
    TextView biotv;
    @BindView(R.id.activity_detail_user_rs_birthdate)
    TextView birthdatetv;
    @BindView(R.id.activity_detail_user_rs_twitter)
    TextView twittertv;
    @BindView(R.id.activity_detail_user_rs_facebook)
    TextView facebooktv;
    @BindView(R.id.activity_detail_user_rs_insta)
    TextView instatv;
    @BindView(R.id.activity_detail_user_rs_name)
    TextView nametv;
    @BindView(R.id.activity_detail_user_rs_image)
    ImageView imageView;
    String id;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_user_rs);
        sharedPreferences = SharedPref.getInstance(this);
        ButterKnife.bind(this);
        Bundle extras = getIntent().getExtras();
        String name = extras.getString("userName");
        String twitter = extras.getString("userTwitter");
        String insta = extras.getString("userInsta");
        String facebook = extras.getString("userFacebook");
        String bio = extras.getString("userBio");
        String birth = extras.getString("userBirth");
        String image = extras.getString("userPic");
        id = extras.getString("userid");
        biotv.setText(bio);
        birthdatetv.setText(birth);
        nametv.setText(name);
        twittertv.setText("twitter: "+twitter);
        instatv.setText("insta: "+insta);
        facebooktv.setText("facebook: "+facebook);
        Glide.with(DetailUserRS.this).load(image).into(imageView);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_report, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_report:
                Intent intent = new Intent(DetailUserRS.this, Report.class);
                intent.putExtra("userid", id);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


}