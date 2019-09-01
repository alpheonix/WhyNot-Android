package com.example.why_not_android.views;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.why_not_android.R;
import com.example.why_not_android.data.SharedPreferences.SharedPref;
import com.example.why_not_android.data.dto.RegisterDTO;
import com.example.why_not_android.data.service.EventService;
import com.example.why_not_android.data.service.providers.NetworkProvider;

import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailEvent extends AppCompatActivity {

    @BindView(R.id.activity_detail_event_name)
    TextView nameTv;
    @BindView(R.id.activity_detail_event_imageView)
    ImageView imageIv;
    @BindView(R.id.activity_detail_event_add)
    TextView addressTv;
    @BindView(R.id.activity_detail_event_price)
    TextView priceTv;
    @BindView(R.id.activity_detail_event_desc)
    TextView descTv;
    @BindView(R.id.activity_detail_event_date)
    TextView dateTv;
    private String eventId;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_event);
        ButterKnife.bind(this);
        sharedPreferences = SharedPref.getInstance(this);

        Bundle extras = getIntent().getExtras();
        String name = extras.getString("eventName");
        String image = extras.getString("eventPic");
        String address = extras.getString("eventAddress");
        Integer price = extras.getInt("eventPrice");
        String desc = extras.getString("eventDesc");
        String date = extras.getString("eventDate");
        eventId = extras.getString("eventid");
        nameTv.setText(name);
        Glide.with(DetailEvent.this).load(image).into(imageIv);
        addressTv.setText(address);
        priceTv.setText(price.toString());
        descTv.setText(desc);
        dateTv.setText(date);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_register, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_register:
                EventService eventService;
                eventService = NetworkProvider.getClient().create(EventService.class);
                RegisterDTO registerDTO = new RegisterDTO(eventId);
                Call<RegisterDTO> sessionDTOCall = eventService.register(SharedPref.getToken(), registerDTO);
                sessionDTOCall.enqueue(new Callback<RegisterDTO>() {
                    @Override
                    public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                        RegisterDTO sessionDTO = response.body();
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(DetailEvent.this, EventList.class);
                            startActivity(intent);
                        } else if (response.body() == null) {
                            try {
                                JSONObject errorJSON = new JSONObject(response.errorBody().string());
                                Toast.makeText(DetailEvent.this, errorJSON.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.d("toz", e.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterDTO> call, Throwable t) {
                        Log.d("toz", t.toString());
                    }
                });
                return true;
            case R.id.action_unregister:
                EventService unregisterService;
                unregisterService = NetworkProvider.getClient().create(EventService.class);
                RegisterDTO unregisterDTO = new RegisterDTO(eventId);
                Call<RegisterDTO> unsessionDTOCall = unregisterService.unregister(SharedPref.getToken(), unregisterDTO);
                unsessionDTOCall.enqueue(new Callback<RegisterDTO>() {
                    @Override
                    public void onResponse(Call<RegisterDTO> call, Response<RegisterDTO> response) {
                        RegisterDTO sessionDTO = response.body();
                        if (response.isSuccessful()) {
                            Intent intent = new Intent(DetailEvent.this, EventList.class);
                            startActivity(intent);
                            finish();
                            finish();
                            finish();
                            finish();
                            finish();
                        } else if (response.body() == null) {
                            try {
                                JSONObject errorJSON = new JSONObject(response.errorBody().string());
                                Toast.makeText(DetailEvent.this, errorJSON.getString("error"), Toast.LENGTH_SHORT).show();
                            } catch (Exception e) {
                                Log.d("toz", e.toString());
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<RegisterDTO> call, Throwable t) {
                        Log.d("toz", t.toString());
                    }
                });
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
