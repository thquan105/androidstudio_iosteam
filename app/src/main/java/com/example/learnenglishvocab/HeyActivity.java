package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class HeyActivity extends AppCompatActivity {

    private Button btnDangKyHey, btnDangNhapHey;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hey);

        btnDangKyHey = findViewById(R.id.btnDangKyHey);
        btnDangNhapHey = findViewById(R.id.btnDangNhapHey);
        mAuth = FirebaseAuth.getInstance();




        btnDangKyHey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignUp = new Intent(HeyActivity.this, SignUpctivity.class);
                startActivity(intentSignUp);
                finish();
            }
        });


        btnDangNhapHey.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intentSignIn = new Intent(HeyActivity.this, SignInActivity.class);
                startActivity(intentSignIn);
                finish();
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();
        if (firebaseUser != null){
            startActivity(new Intent(HeyActivity.this, HomeActivity.class));
            finish();
        }
    }
}