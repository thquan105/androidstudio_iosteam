package com.example.learnenglishvocab;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.Switch;
import android.widget.Toast;

public class SettingsActivity extends AppCompatActivity {

    private AudioManager audioManager;
    private SeekBar seekBarVolume;
    private Switch darkSwitch, noteSwitch, musicSwitch;
    private ImageView bt_back;
    private SharedPreferences sharedPreferences;
    private boolean dark,note,music;
    private BackgroundMediaPlayer backgroundMediaPlayer;


    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        seekBarVolume = findViewById(R.id.seekBar);
        darkSwitch = findViewById(R.id.switch_chedo);
        noteSwitch = findViewById(R.id.switch_thongbao);
        musicSwitch = findViewById(R.id.switch_amthanh);
        loadSharedPreferences();




        bt_back = (ImageView) findViewById(R.id.btn_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        audioManager = (AudioManager) getSystemService(this.AUDIO_SERVICE);

        darkSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                dark = b;
            }
        });

        noteSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                note = b;
            }
        });

        musicSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if(b){
                    backgroundMediaPlayer.SoundPlayer(SettingsActivity.this,R.raw.bg_music);
                } else {
                    backgroundMediaPlayer.mediaPlayer.stop();
                }
                music = b;
            }
        });





        seekBarVolume.setMax(audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC));

        seekBarVolume.setProgress(audioManager.getStreamVolume(AudioManager.STREAM_MUSIC));

        seekBarVolume.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {
                audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, i, 0);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }


    private void loadSharedPreferences()
    {
        sharedPreferences = getSharedPreferences("dataSettings", MODE_PRIVATE);

        darkSwitch.setChecked(sharedPreferences.getBoolean("darkCheck", false));
        noteSwitch.setChecked(sharedPreferences.getBoolean("noteCheck", false));
        musicSwitch.setChecked(sharedPreferences.getBoolean("musicCheck", false));
        System.out.println("load");
    }
    private void setSharedPreferences(){
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean("darkCheck", dark);
        editor.putBoolean("noteCheck", note);
        editor.putBoolean("musicCheck", music);
        editor.commit();
        System.out.println("set");

    }
    @Override
    protected void onStop() {
        setSharedPreferences();
        super.onStop();
    }

}
