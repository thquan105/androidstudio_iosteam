package com.example.learnenglishvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.Toast;

public class hoso extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_hoso);
        //nut back
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_chinhsuahoso,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.itemchinhsuataikhoan:
                openChinhsuataikhoanDialog(Gravity.CENTER);
                break;
            case R.id.itemchinhsuattcn:
                openChinhsuattcnDialog(Gravity.CENTER);
                break;
        }

        return super.onOptionsItemSelected(item);

    }
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
        EditText edttendangnhap=dialog.findViewById(R.id.edt_tendangnhap);
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