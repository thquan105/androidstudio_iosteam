package com.example.learnenglishvocab;


import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.core.Tag;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class hoso extends AppCompatActivity implements PopupMenu.OnMenuItemClickListener {
    private TextView tvten,tvdiem,tvngaysinh,tvemail,txt_ten;
    private ImageView imgAvatar;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoso);

        imgAvatar=this.findViewById(R.id.img_avt);
        tvdiem=this.findViewById(R.id.textView_Diem);
        tvngaysinh=this.findViewById(R.id.textView_nsinh);
        tvten=this.findViewById(R.id.textView_Ten);
        tvemail=this.findViewById(R.id.textView_Email);
        txt_ten=findViewById(R.id.tv_tenUser);

        fAuth=FirebaseAuth.getInstance();

        userId = fAuth.getCurrentUser().getUid();

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
                        tvemail.setText(documentSnapshot.getString("Email"));
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



//        DocumentReference documentReference=fStore.collection("users").document(userId);
//        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
//            @Override
//            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {

//                //imgAvatar.setImageResource(Integer.parseInt(documentSnapshot.getString("AnhDaiDien")));
//                System.out.println(error.toString());
//            }
//        });



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
                Toast.makeText(hoso.this, "đã lưu thay đổi", Toast.LENGTH_SHORT).show();
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
                Toast.makeText(hoso.this, "đã lưu thay đổi", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}