package com.example.learnenglishvocab;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.net.Inet4Address;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class SignInActivity extends AppCompatActivity {

    private EditText edtEmail, edtPass;
    private Button btnSignIn;
    private TextView tvDangKy, tvForgotpass;
    private ImageView imgGoogle;
    private FirebaseAuth mAuth;
    private FirebaseFirestore mStore;
    private CheckBox cbRemenber;

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = "GOOGLEAUTH";
    GoogleSignInClient mGoogleSignInClient;


    SharedPreferences sharedPreferences;
    GoogleSignInOptions gso;
    GoogleSignInClient gsc;


    String userID;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        edtEmail = findViewById(R.id.edtEmail);
        edtPass = findViewById(R.id.edtPass);
        tvDangKy = findViewById(R.id.tvDangKy);
        btnSignIn = findViewById(R.id.btnSignIn);
        tvForgotpass = findViewById(R.id.tvForgotpass);
        cbRemenber = findViewById(R.id.cbRemenber);


        mAuth = FirebaseAuth.getInstance();
        mStore = FirebaseFirestore.getInstance();


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);



        sharedPreferences= getSharedPreferences("dataLogin", MODE_PRIVATE);
        // lấy giá trị từ sharePreferences
        edtEmail.setText(sharedPreferences.getString("email",""));
        edtPass.setText(sharedPreferences.getString("pass",""));
        cbRemenber.setChecked(sharedPreferences.getBoolean("checked",false));


        tvDangKy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignInActivity.this, SignUpctivity.class);
                startActivity(intent);
                finish();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginUser();
            }
        });

        loginWithGoogle();


        tvForgotpass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String emailAddress = edtEmail.getText().toString();
                if(emailAddress.isEmpty()){
                    edtEmail.setError("Không được để trống");
                }
                else{
                    mAuth.sendPasswordResetEmail(emailAddress)
                            .addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if (task.isSuccessful()) {
                                        Toast.makeText(SignInActivity.this, "Kiểm tra email", Toast.LENGTH_SHORT).show();
                                    }else {
                                        Toast.makeText(SignInActivity.this, "Gởi email khôi phục thất bại", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                }

            }
        });

    }

    private void loginUser() {
        String email = edtEmail.getText().toString();
        String pass = edtPass.getText().toString();


        if (!email.isEmpty() && Patterns.EMAIL_ADDRESS.matcher(email).matches()){
            if (!pass.isEmpty()){
                mAuth.signInWithEmailAndPassword(email , pass)
                        .addOnSuccessListener(new OnSuccessListener<AuthResult>() {
                            @Override
                            public void onSuccess(AuthResult authResult) {

                                if(cbRemenber.isChecked()){
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.putString("email", email);
                                    editor.putString("pass", pass);
                                    editor.putBoolean("checked", true);
                                    editor.commit();
                                }else{
                                    SharedPreferences.Editor editor = sharedPreferences.edit();
                                    editor.remove("email");
                                    editor.remove("pass");
                                    editor.remove("checked");
                                    editor.commit();
                                }

                                Toast.makeText(SignInActivity.this, "Đăng nhập thành công !!", Toast.LENGTH_SHORT).show();
//                                startActivity(new Intent(SignInActivity.this , HomeActivity.class));
                                startActivity(new Intent(SignInActivity.this , HomeActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignInActivity.this, "Đăng nhập thất bại !!", Toast.LENGTH_SHORT).show();
                            }
                        });
            }else{
                edtPass.setError("Không được để trống");
            }
        }else if(email.isEmpty()){
            edtEmail.setError("Không được để trống");
        }else{
            edtEmail.setError("Nhập đúng định dạng mail");
        }
    }

    private void loginWithGoogle(){
        imgGoogle = findViewById(R.id.imgGoogle);
        gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        gsc = GoogleSignIn.getClient(this, gso);

        imgGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SignIn();

            }
        });
    }

    private void SignIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        // Result returned from launching the Intent from GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                //Log.d(TAG, "firebaseAuthWithGoogle:" + account.getId());
                firebaseAuthWithGoogle(account.getIdToken());
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.w(TAG, "Google sign in failed", e);

            }
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            userID = mAuth.getCurrentUser().getUid();
                            String email = mAuth.getCurrentUser().getEmail();

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
                                    Toast.makeText(SignInActivity.this, "Tạo tài khoản thành công", Toast.LENGTH_SHORT).show();
                                }
                            });

                            Intent i = new Intent(SignInActivity.this,HomeActivity.class);
                            startActivity(i);
                            finish();

                        } else {

                            Toast.makeText(SignInActivity.this,"Đăng nhập thất bại",Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

}