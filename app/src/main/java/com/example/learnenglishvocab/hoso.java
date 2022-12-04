package com.example.learnenglishvocab;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class hoso extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TextView tvten,tvdiem,tvngaysinh,tvsdt,tvemail,txt_ten;
    private ImageView imgAvatar, imgAvtUpdate, imgback;
    private Button btnDeleteAccount;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, userMail;
    Uri uri;
    private SharedPreferences sharedPreferences;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoso);

        imgAvatar=this.findViewById(R.id.img_avt);
        tvdiem=this.findViewById(R.id.textView_Diem);
        tvngaysinh=this.findViewById(R.id.textView_nsinh);
        tvten=this.findViewById(R.id.textView_Ten);
        tvemail = this.findViewById(R.id.tv_emailUser);
        tvsdt=this.findViewById(R.id.textView_SDT);
        txt_ten=findViewById(R.id.tv_tenUser);
        imgAvtUpdate= findViewById(R.id.imgAvtUpdate);
        btnDeleteAccount= findViewById(R.id.btnDeleteAccount);
        imgback= findViewById(R.id.imgback);

        fAuth=FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();
        userMail = fAuth.getCurrentUser().getEmail();






//        DocumentReference documentReference=fStore.collection("users").document(userId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

//                //imgAvatar.setImageResource(Integer.parseInt(documentSnapshot.getString("AnhDaiDien")));
//                System.out.println(error.toString());
//            }
//        });
        tvemail.setText(userMail.trim());
        initView();

        imgback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        btnDeleteAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder alertDiaLog = new AlertDialog.Builder(view.getContext());
                alertDiaLog.setTitle("Thông báo");
                alertDiaLog.setMessage("Bạn có muốn xóa tài khoản ?");
                alertDiaLog.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        FirebaseUser user = fAuth.getCurrentUser();

                        String userID = fAuth.getCurrentUser().getUid();

                        fStore.collection("users").document(userID)
                                .delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void unused) {

                                    }
                                });

                        user.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(hoso.this, "Xóa tài khoản thành công!", Toast.LENGTH_SHORT).show();

                                sharedPreferences= getSharedPreferences("dataLogin", MODE_PRIVATE);
                                SharedPreferences.Editor editor = sharedPreferences.edit();
                                editor.remove("email");
                                editor.remove("pass");
                                editor.remove("checked");
                                editor.commit();

                                startActivity(new Intent(hoso.this, HeyActivity.class));
                                finish();
                            }
                        }).addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(hoso.this, e.toString(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, e.toString());
                            }
                        });
                    }
                });
                alertDiaLog.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                alertDiaLog.show();
            }
        });

        imgAvtUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(hoso.this)
                        .start();


            }
        });



    }

    private void initView() {
        fStore.getInstance()
                .collection("users")
                .document(userId)
                .get()
                .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                    @Override
                    public void onSuccess(DocumentSnapshot documentSnapshot) {
                        tvten.setText(""+documentSnapshot.getString("HoTen"));
                        txt_ten.setText(""+documentSnapshot.getString("HoTen"));
                        tvdiem.setText(""+documentSnapshot.getLong("Diem"));
                        tvsdt.setText(documentSnapshot.getString("SDT"));
                        tvngaysinh.setText(documentSnapshot.getString("NgaySinh"));
                        //Set hinhanh
                        Glide
                                .with(hoso.this)
                                .load(documentSnapshot.getString("AnhDaiDien"))
                                .apply(new RequestOptions()
                                        .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                                        .fitCenter()
                                        .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal))
                                .into(imgAvatar);
                    }
                });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        uri = data.getData();
        imgAvatar.setImageURI(uri);

        setToFireStorage(uri);

    }

    private void setToFireStorage(Uri uri) {
        StorageReference storageReference= FirebaseStorage.getInstance().getReference().child("ImageFolder");
        final StorageReference ImageReference=storageReference.child("112233");
        ImageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                ImageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {

                        updateUserProfile(uri);


                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(hoso.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(hoso.this, e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }


    public void showPopup(View v){
        PopupMenu popup=new PopupMenu(this,v);
        popup.setOnMenuItemClickListener(this);
        popup.inflate(R.menu.menu_chinhsuahoso);
        popup.show();
    }

    @Override
    public boolean onMenuItemClick(MenuItem menuItem) {
        switch (menuItem.getItemId()){
            case R.id.itemchinhsuataikhoan:
                openChinhsuataikhoanDialog(Gravity.CENTER);
                return true;
            case R.id.itemchinhsuattcn:
                openChinhsuattcnDialog(Gravity.CENTER);
                return true;
            default:
                return false;
        }

    }

    //dialog
    private  void openChinhsuataikhoanDialog(int gravity){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_chinhsuataikhoan);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM==gravity) {
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
        EditText edtmatkhaucu=dialog.findViewById(R.id.edt_matkhaucu);
        EditText edtmatkhaumoi=dialog.findViewById(R.id.edt_matkhaumoi);
        EditText edtxacnhanmatkhau=dialog.findViewById(R.id.edt_xacnhanmatkhau);
        AppCompatButton btnluuchinhsuatk=dialog.findViewById(R.id.btn_luuchinhsuatk);
        ImageView imgclosechinhsuatk=dialog.findViewById(R.id.img_closeDialogChinhsuatk);

        imgclosechinhsuatk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        btnluuchinhsuatk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Toast.makeText(hoso.this, "đã lưu thay đổi", Toast.LENGTH_SHORT).show();

                FirebaseUser user;
                user = FirebaseAuth.getInstance().getCurrentUser();
                final String email = user.getEmail();
                String oldpass = edtmatkhaucu.getText().toString();

                final String newPass  = edtmatkhaumoi.getText().toString();
                if(newPass.equals(edtxacnhanmatkhau.getText().toString())){
                    AuthCredential credential = EmailAuthProvider.getCredential(email,oldpass);

                    user.reauthenticate(credential).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if(task.isSuccessful()){
                                user.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
                                    @Override
                                    public void onComplete(@NonNull Task<Void> task) {
                                        if(!task.isSuccessful()){
                                            Toast.makeText(hoso.this, "Cập nhật mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                        }else {
                                            Toast.makeText(hoso.this, "Cập nhật mật khẩu thành công", Toast.LENGTH_SHORT).show();

                                            sharedPreferences= getSharedPreferences("dataLogin", MODE_PRIVATE);
                                            SharedPreferences.Editor editor = sharedPreferences.edit();
                                            editor.remove("pass");
                                            editor.commit();

                                            dialog.dismiss();
                                        }
                                    }
                                });
                            }else {
                                edtmatkhaucu.setError("Mật khẩu không đúng!");
                                Toast.makeText(hoso.this, task.getException().getLocalizedMessage(), Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else{
                    edtxacnhanmatkhau.setError("Mật khẩu xác nhập không khớp!");
                }


            }
        });
        dialog.show();
    }
    private  void openChinhsuattcnDialog(int gravity){
        final Dialog dialog=new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.layout_dialog_chinhsuattcn);
        Window window=dialog.getWindow();
        if(window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttributes=window.getAttributes();
        windowAttributes.gravity=gravity;
        window.setAttributes(windowAttributes);

        if(Gravity.BOTTOM==gravity) {
            dialog.setCancelable(true);
        }else{
            dialog.setCancelable(false);
        }
        EditText edtten=dialog.findViewById(R.id.edt_ten);
        EditText edtngaysinh=dialog.findViewById(R.id.edt_ngaysinh);
        EditText edtemail=dialog.findViewById(R.id.edt_email);
        AppCompatButton btnluuchinhsuattcn=dialog.findViewById(R.id.btn_luuchinhsuattcn);
        ImageView imgclosechinhsuattcn=dialog.findViewById(R.id.img_closeDialogChinhsuattcn);

        imgclosechinhsuattcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnluuchinhsuattcn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String ten = edtten.getText().toString();
                String ngaySinh = edtngaysinh.getText().toString();
                String sdt = edtemail.getText().toString();

                updateUserProfile(ten, ngaySinh, sdt);
                initView();
                dialog.dismiss();

            }
        });
        dialog.show();
    }
    
    private void updateUserProfile(String ten, String ngaySinh, String sdt){
        String userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("HoTen", ten);
        user.put("NgaySinh", ngaySinh);
        user.put("SDT", sdt);

        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(hoso.this, "Cập nhập thông tin thành công!", Toast.LENGTH_SHORT).show();
            }
        });

    }

    private void updateUserProfile(Uri uri){
        String userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);
        Map<String, Object> user = new HashMap<>();
        user.put("AnhDaiDien", uri.toString());

        documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(hoso.this, "Cập nhập ảnh thành công!", Toast.LENGTH_SHORT).show();
            }
        });

    }


    
}