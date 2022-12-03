package com.example.learnenglishvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

public class SignUpctivity extends AppCompatActivity {

    private EditText edtEmailSignUp, edtPassSignUp, edtAgainPass;
    private TextView tvDangNhap;
    private Button btnSignUp;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private String userID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_upctivity);

        edtEmailSignUp = findViewById(R.id.edtEmailSignUp);
        edtPassSignUp = findViewById(R.id.edtPassSignUp);
        edtAgainPass = findViewById(R.id.edtAgainPass);
        tvDangNhap = findViewById(R.id.tvDangNhap);
        btnSignUp = findViewById(R.id.btnSignUp);

        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createUser();
            }
        });

        tvDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignUpctivity.this, SignInActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }

    private void createUser(){
        String email = edtEmailSignUp.getText().toString();
        String pass = edtPassSignUp.getText().toString();
        String passAgain = edtAgainPass.getText().toString();


        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty() && !passAgain.isEmpty()){
                if (pass.equals(passAgain)){
                    mAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()){

                                userID = mAuth.getCurrentUser().getUid();
                                DocumentReference documentReference = mStore.collection("users").document(userID);
                                Map<String, Object> user = new HashMap<>();
                                user.put("SDT", "0xxxxxxxx");
                                user.put("AnhDaiDien", "https://firebasestorage.googleapis.com/v0/b/learnenglishvocab-6ef61.appspot.com/o/user_default.png?alt=media&token=98c08dbc-c1b6-4d86-a74b-f2944c2cce2a");
                                user.put("Diem", 0);
                                user.put("HoTen", "Full name");
                                user.put("NgaySinh", "01/01/2002");

                                documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {
                                        Toast.makeText(SignUpctivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                    }
                                });


                                Intent intent = new Intent(SignUpctivity.this, SignInActivity.class);
                                startActivity(intent);
                            }else {
                                Toast.makeText(SignUpctivity.this, "Tạo tài khoản thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    edtAgainPass.setError("Mật khẩu không khớp nhau");
                }

            }else {
                if (pass.isEmpty()){
                    edtPassSignUp.setError("Không được để trống.");
                }else{
                    edtAgainPass.setError("Không được để trống.");
                }
            }
        }else {
            if (email.isEmpty()){
                edtEmailSignUp.setError("Không được để trống.");
            }else {
                edtEmailSignUp.setError("Nhập đúng địa chỉ email.");
            }
        }
    }

}