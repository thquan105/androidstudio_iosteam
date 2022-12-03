package com.example.learnenglishvocab;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class FeedbackActivity extends AppCompatActivity {
    private EditText et_ten, et_nd;
    private Button bt_send;
    private FirebaseFirestore db;
    private FirebaseAuth fAuth;
    private ImageView bt_back;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);

        et_ten = findViewById(R.id.ed_tenPhanHoi);
        et_nd = findViewById(R.id.ed_ndPhanHoi);
        bt_send = findViewById(R.id.btn_send);
        bt_back = (ImageView) findViewById(R.id.btn_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        db = FirebaseFirestore.getInstance();
        fAuth= FirebaseAuth.getInstance();

        String email = fAuth.getCurrentUser().getEmail();

        bt_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (et_ten.getText().toString().trim().equals("") || et_nd.getText().toString().trim().equals("")) {
                    Toast.makeText(FeedbackActivity.this, "Nhập đầy đủ thông tin.", Toast.LENGTH_SHORT).show();
                } else {
                    // Create a new user with a first, middle, and last name
                    Map<String, Object> feedback = new HashMap<>();
                    feedback.put("Ten", et_ten.getText().toString().trim());
                    feedback.put("NoiDung", et_nd.getText().toString().trim());
                    feedback.put("Email", email.toString());

                    // Add a new document with a generated ID
                    db.collection("FeedBack")
                            .add(feedback)
                            .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                @Override
                                public void onSuccess(DocumentReference documentReference) {
                                    Log.d(TAG, "DocumentSnapshot added with ID: " + documentReference.getId());
                                    Toast.makeText(FeedbackActivity.this, "Cảm ơn bạn đã phản hồi.", Toast.LENGTH_SHORT).show();
                                    et_ten.setText("");
                                    et_nd.setText("");
                                }
                            })
                            .addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Log.w(TAG, "Error adding document", e);
                                }
                            });
                }
            }
        });

    }
}