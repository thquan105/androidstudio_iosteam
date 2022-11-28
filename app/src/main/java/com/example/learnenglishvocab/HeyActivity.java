package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class HeyActivity extends AppCompatActivity {

    private Button btnDangKyHey, btnDangNhapHey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hey);

        btnDangKyHey = findViewById(R.id.btnDangKyHey);
        btnDangNhapHey = findViewById(R.id.btnDangNhapHey);

        btnDangKyHey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(HeyActivity.this, SignUpctivity.class);
                startActivity(intentSignUp);
            }
        });


        btnDangNhapHey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(HeyActivity.this, SignInActivity.class);
                startActivity(intentSignIn);
            }
        });

    }
}