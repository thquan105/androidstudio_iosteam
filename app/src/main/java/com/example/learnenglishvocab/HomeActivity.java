package com.example.learnenglishvocab;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Variables
    static final float END_SCALE = 1f;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    private ImageView btMenu, btSettings;
    private RelativeLayout contentView;

    private TextView bt_cate_game_1, bt_cate_game_2, bt_translation;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        //Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        btMenu = findViewById(R.id.btn_menu);
        btSettings = (ImageView) findViewById(R.id.btn_settings);
        contentView = findViewById(R.id.content);

        bt_cate_game_1 = findViewById(R.id.btn_Cate_Game_1);
        bt_cate_game_2 = findViewById(R.id.btn_Cate_Game_2);
        bt_translation = findViewById(R.id.btn_translation);

        bt_cate_game_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategaryGame1();
            }
        });
        bt_cate_game_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCategaryGame2();
            }
        });
        bt_translation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openTraslation();
            }
        });
        btSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                opensettings();
            }
        });

        navigationDrawer();

    }

    private void opensettings() {
        Intent intent = new Intent(HomeActivity.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void openTraslation() {
        Intent intent = new Intent(HomeActivity.this, TranslationActivity.class);
        startActivity(intent);
    }

    private void openCategaryGame2() {
        Intent intent = new Intent(HomeActivity.this, Game2Activity.class);
        startActivity(intent);
    }

    private void openCategaryGame1() {
        Intent intent = new Intent(HomeActivity.this, Game1Activity.class);
        startActivity(intent);
    }

    private void navigationDrawer(){
        //Navigation Drawer
        navigationView.bringToFront();
        navigationView.setNavigationItemSelectedListener(this);

        btMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(drawerLayout.isDrawerVisible(GravityCompat.START)) {
                    drawerLayout.closeDrawer(GravityCompat.START);
                }
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });

        animateNaviagtionDrawer();
    }

    private void animateNaviagtionDrawer() {
        //drawerLayout.setScrimColor(getResources().getColor(R.color.colorPrimary));
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {
                // Scale the View based on current slide offset
                final float diffScaledOffset = slideOffset * (1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);
                // Translate the View, accounting for the scaled width
                final float xOffset = drawerView.getWidth() * slideOffset;
                final float xOffsetDiff = contentView.getWidth() * diffScaledOffset / 2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);
            }
        });

    }

    @Override
    public void onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        }
        else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.profile:
            {
                Toast.makeText(HomeActivity.this,"login",Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.notification:
            {

            }
            case R.id.hengio:
            {
                startActivity(new Intent(HomeActivity.this, TimerActivity.class));
            }
            case R.id.share:
            {

            }
            case R.id.rate:
            {

            }
            case R.id.feedback:
            {

            }
            case R.id.about:
            {

            }
            case R.id.logout:
            {

            }
        }
        return false;
    }
}