package com.example.learnenglishvocab;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlarmManager;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;





public class TimerActivity extends AppCompatActivity {
    private TimePicker alarmTimePicker = null;
    private Calendar calendar = null;
    private AlarmManager alarmManager;
    private PendingIntent pendingIntent;
    private Button btnSetAlarm, btnCancelAlarm;
    private ImageView bt_back;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timer);
        createNotificationChannel();

        btnSetAlarm = findViewById(R.id.setAlarmBtn);
        btnCancelAlarm = findViewById(R.id.cancelAlarmBtn);
        alarmTimePicker = findViewById(R.id.timePicker);


        btnSetAlarm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            setAlarm();


        }
    });

        btnCancelAlarm.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {

            cancelAlarm();

        }
    });

        bt_back = (ImageView) findViewById(R.id.btn_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

}
    private void cancelAlarm() {

        Intent intent = new Intent(this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,intent,0);

        if (alarmManager == null){

            alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        }

        alarmManager.cancel(pendingIntent);
        Toast.makeText(this, "Đã dừng nhắc", Toast.LENGTH_SHORT).show();
    }



    private void setAlarm() {

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);

        calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY,alarmTimePicker.getCurrentHour());
        calendar.set(Calendar.MINUTE, alarmTimePicker.getCurrentMinute());

        Intent intent = new Intent(TimerActivity.this,AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(TimerActivity.this,0,intent,0);

        Long time = calendar.getTimeInMillis() - calendar.getTimeInMillis()%60000;
        if (System.currentTimeMillis() > time){
            if (Calendar.AM_PM == 0){
                time = time + 1000*60*60*12;
            } else {
                time = time + 1000*60*60*24;
            }
        }
        alarmManager.setExact(AlarmManager.RTC_WAKEUP,time,pendingIntent);

        Toast.makeText(this, "Đã lên lịch nhắc", Toast.LENGTH_SHORT).show();

    }


    private void createNotificationChannel(){
        CharSequence name = "Đã đến lúc học rồi.";
        String description = "Bắt đầu học từ vựng nào!!";
        int importance = NotificationManager.IMPORTANCE_HIGH;
        NotificationChannel channel = new NotificationChannel("foxandroid",name,importance);
        channel.setDescription(description);

        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.createNotificationChannel(channel);
    }

}