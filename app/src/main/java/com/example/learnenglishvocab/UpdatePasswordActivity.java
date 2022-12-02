package com.example.learnenglishvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class UpdatePasswordActivity extends AppCompatActivity {

    Button btnConfirm;
    EditText edtEmailUd, edtPassUd, edtNewPassUd;
    FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_password);

        edtEmailUd = findViewById(R.id.edtEmailUd);
        edtPassUd = findViewById(R.id.edtPassUd);
        edtNewPassUd = findViewById(R.id.edtNewPassUd);
        btnConfirm = findViewById(R.id.btnConfirm);


        auth = FirebaseAuth.getInstance();





        btnConfirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseUser user;
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = user.getEmail();
                String oldpass = edtPassUd.getText().toString();

                final String newPass  = edtNewPassUd.getText().toString();
                AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

                user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(!task.isSuccessful()){
                                        Toast.makeText(UpdatePasswordActivity.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(UpdatePasswordActivity.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                        }else {
                            Toast.makeText(UpdatePasswordActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                        }
                    }
                });
        }


        });





    }
}