package com.example.learnenglishvocab;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener{

    //Variables
    static final float END_SCALE = 1f;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    FirebaseAuth mAuth;
    private ImageView btMenu, btSettings;
    private RelativeLayout contentView;
    private SharedPreferences sharedPreferences;
    private CardView bt_cate_game_1, bt_cate_game_2, bt_translation;
    BackgroundMediaPlayer backgroundMediaPlayer;
    private int i = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mAuth = FirebaseAuth.getInstance();
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
        runMediaPlayer();
        navigationDrawer();

    }

    private void runMediaPlayer() {
        sharedPreferences = getSharedPreferences("dataSettings", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("musicCheck", false) == true && i==1) {
            backgroundMediaPlayer.SoundPlayer(this,R.raw.bg_music);
            i++;
        }
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
                startActivity(new Intent(HomeActivity.this, hoso.class));
                break;
            }
            case R.id.notification:
            {
                Toast.makeText(this, "Chức năng sẽ sớm được ra mắt.", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.hengio:
            {
                startActivity(new Intent(HomeActivity.this, TimerActivity.class));
                break;
            }
            case R.id.share:
            {
                Intent intent = new Intent(Intent.ACTION_SEND);
                intent.setType("text/plain");
                intent.putExtra(Intent.EXTRA_SUBJECT, "Hãy cùng trải nghiệm ứng dụng học tiếng Anh này.");
                intent.putExtra(Intent.EXTRA_TEXT,"Tải App ngay tại: \n https://drive.google.com/drive/u/1/folders/1AOyVh_poWyUrdcoYq9QsfTRVIDdiSDH9");
                startActivity(Intent.createChooser(intent,"Chia sẻ qua"));
                break;
            }
            case R.id.rate:
            {
                Toast.makeText(this, "Chức năng sẽ sớm được ra mắt.", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.feedback:
            {
                startActivity(new Intent(HomeActivity.this, FeedbackActivity.class));
                break;
            }
            case R.id.about:
            {
                final Dialog dialog=new Dialog(this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_about_us);
                Window window=dialog.getWindow();
                if(window==null){
                    break;
                }
                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams windowAttributes=window.getAttributes();
                windowAttributes.gravity=Gravity.CENTER;
                window.setAttributes(windowAttributes);

                if(Gravity.BOTTOM==Gravity.CENTER) {
                    dialog.setCancelable(true);
                }else{
                    dialog.setCancelable(false);
                }
                ImageView bt_close = dialog.findViewById(R.id.iv_close);
                bt_close.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {dialog.dismiss();}
                });
                dialog.show();
                break;
            }
            case R.id.logout:
            {
                mAuth.signOut();
                if (backgroundMediaPlayer.mediaPlayer != null){
                    backgroundMediaPlayer.mediaPlayer.stop();
                }
                startActivity( new Intent(HomeActivity.this, HeyActivity.class));
                finish();
                break;
            }
        }
        return false;
    }
}