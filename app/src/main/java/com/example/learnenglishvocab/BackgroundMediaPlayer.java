package com.example.learnenglishvocab;

import android.content.Context;
import android.media.MediaPlayer;
import android.widget.Toast;

public class BackgroundMediaPlayer {

    public static MediaPlayer mediaPlayer;
    public static void SoundPlayer(Context ctx, int raw_id){
        mediaPlayer = MediaPlayer.create(ctx, R.raw.bg_music);
        mediaPlayer.setLooping(true); // Set looping
        mediaPlayer.setVolume(100, 100);

        //player.release();
        mediaPlayer.start();
    }

    public static void stopPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }
}
