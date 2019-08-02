package com.example.why_not_android.views;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;

import com.example.why_not_android.R;
import com.example.why_not_android.data.Models.User;

public class MenuActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        switch (id) {
            case R.id.drawer_menu_home:
                Intent home = new Intent(MenuActivity.this, Home.class);
                startActivity(home);
                break;
            case R.id.drawer_menu_my_profil:

                Intent intent = new Intent(MenuActivity.this, DetailUser.class);

                startActivity(intent);
                break;
            case R.id.drawer_menu_event:
                Intent event = new Intent(MenuActivity.this, EventList.class);
                startActivity(event);
                break;
            case R.id.drawer_menu_my_event:
                Intent myEvent = new Intent(MenuActivity.this, EventList.class);
                myEvent.putExtra("myEvent", true);
                startActivity(myEvent);
                break;
            case R.id.drawer_menu_match:
                Intent matchList = new Intent(MenuActivity.this, MatchListActivity.class);
                startActivity(matchList);
                break;
            case R.id.drawer_menu_logout:
                Intent logout = new Intent(MenuActivity.this, MainActivity.class);
                startActivity(logout);
                break;
            default:
                break;
        }
        
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}


