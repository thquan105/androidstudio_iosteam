package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Application;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Locale;
import java.util.Random;

public class GameXepChu extends AppCompatActivity implements InterfaceClickChu {

    private RecyclerView rcvChu,rcvAnswer;
    private ArrayList<String> arrChu, arrAnswer,arrDung, arrTV,arrTA;
    private TuKhoaAdapter chuAdapter,dapAnAdapter;
    private CardView imgbtnRefresh;
    private Dialog dialog;
    private String tuTA,tuTV;
    private int viTriTu = 0;
    private GridLayoutManager gridLayoutManagerchu,gridLayoutManagerAnswer;
    private TextView txtTuTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_xep_chu);
        arrTV = new ArrayList<String>();
        arrTV.add("Màu trắng");
        arrTV.add("Mùa xuân");
        arrTV.add("Thứ Hai");

        arrTA = new ArrayList<String>();
        arrTA.add("white");
        arrTA.add("spring");
        arrTA.add("monday");

        init();
        CardView cvBack = findViewById(R.id.cvback);
        cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    private void init(){
        rcvChu = findViewById(R.id.lvtukhoa);
        rcvAnswer = findViewById(R.id.rcvdapan);
        txtTuTV = findViewById(R.id.tvNghiaG2);
        tuTV = arrTV.get(viTriTu);
        tuTA = arrTA.get(viTriTu);
        tuTV = tuTV.substring(0, 1).toUpperCase() + tuTV.substring(1);
        txtTuTV.setText(tuTV);
        //Vô hiệu hóa trượt recycleview
        rcvChu.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });
        rcvAnswer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                return true;
            }
        });

        imgbtnRefresh = findViewById(R.id.cvrefresh);

        String[] strSplit = null;
        strSplit = tuTA.toUpperCase().split("");

        arrChu = new ArrayList<>();
        arrDung = new ArrayList<>();
        arrAnswer = new ArrayList<>();

        for(String i: strSplit){
            if (i.equals("")==false)
                arrDung.add(i);
        }

        arrChu.addAll(arrDung);
        xaoTronChu(arrChu);


        //Làm mới rcv
        imgbtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lammoircv();
            }
        });

        chuAdapter = new TuKhoaAdapter(arrChu,this);
        dapAnAdapter = new TuKhoaAdapter(arrAnswer,this);

        setGridlayout();

        chuAdapter = new TuKhoaAdapter(arrChu, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String chu) {
                arrAnswer.add(chu);
                arrChu.remove(chu);

                setGridlayout();

                chuAdapter.notifyDataSetChanged();
                int cotAnsmax = arrAnswer.size();
                if (cotAnsmax>6)
                    cotAnsmax = 6;
                gridLayoutManagerAnswer = new GridLayoutManager(GameXepChu.this,cotAnsmax);
                rcvAnswer.setLayoutManager(gridLayoutManagerAnswer);
                dapAnAdapter.notifyDataSetChanged();
                if(arrAnswer.size()==arrDung.size()){
                    int check = 0;
                    for (int i = 0; i < arrAnswer.size();i++){
                        if(arrAnswer.get(i).equals(arrDung.get(i))==true){
                            check++;
                        }
                    }
                    if(check==arrDung.size()){
                        if (viTriTu == arrTA.size()-1){
//                Toast.makeText(GameXepChu.this,"hoàn thành",Toast.LENGTH_SHORT).show();
                            showCustomDialog(true);
                            TextView txtChucmung = dialog.findViewById(R.id.txtThongbaodialog);
                            txtChucmung.setText("Hoàn thành");
                            Button btn = dialog.findViewById(R.id.btnDlgG2Dung);
                            btn.setText("OK");
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
//                                    finish();
//                                    System.exit(0);
                                }
                            });
                        }
                        else showCustomDialog(true);
                    }
                    else{
                        showCustomDialog(false);
                        arrChu.removeAll(arrChu);
                        arrChu.addAll(arrDung);
                        xaoTronChu(arrChu);
                        Lammoircv();
                    }
                }

//                Toast.makeText(GameXepChu.this,chu,Toast.LENGTH_SHORT).show();
            }

        });

        dapAnAdapter = new TuKhoaAdapter(arrAnswer, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String tukhoa) {
//                Toast.makeText(GameXepChu.this,tukhoa,Toast.LENGTH_SHORT).show();
                arrChu.add(tukhoa);
                arrAnswer.remove(tukhoa);
                setGridlayout();
                chuAdapter.notifyDataSetChanged();
                dapAnAdapter.notifyDataSetChanged();
            }
        });

        rcvAnswer.setAdapter(dapAnAdapter);
        rcvChu.setAdapter(chuAdapter);
    }

    private void Lammoircv() {
        arrAnswer.removeAll(arrAnswer);
        dapAnAdapter.notifyDataSetChanged();
        arrChu.removeAll(arrChu);
        arrChu.addAll(arrDung);
        setGridlayout();
        xaoTronChu(arrChu);
        rcvChu.setAdapter(chuAdapter);
        rcvAnswer.setAdapter(dapAnAdapter);
    }

    @Override
    public void onItemClickChu(String tukhoa) {
    }
    private ArrayList<String> xaoTronChu(ArrayList<String> ar) {
        Random rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar.get(index);
            ar.set(index,ar.get(i));
            ar.set(i,a);
        }
        return ar;
    }

    private void showCustomDialog(boolean win){
        dialog = new Dialog(GameXepChu.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        if (win){
            dialog.setContentView(R.layout.dialog_game2_dung);
            Button btnnext = dialog.findViewById(R.id.btnDlgG2Dung);
            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viTriTu < arrTA.size()){
                        viTriTu++;
                        txtTuTV.setText(arrTV.get(viTriTu));
                        arrDung.removeAll(arrDung);
                        TachChu(arrTA.get(viTriTu),arrDung);
                        Lammoircv();
                        dialog.dismiss();
                    }
                }
            });
        }
        else{
            dialog.setContentView(R.layout.dialog_game2_sai);

            Button btntry = dialog.findViewById(R.id.btnDlgG2Sai);
            btntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }


        dialog.show();
    }

    private ArrayList<String> TachChu(String s,ArrayList<String> ar){
        ar.removeAll(ar);
        String[] strSplit = null;
        strSplit = s.toUpperCase().split("");

        for(String i: strSplit){
            if (i.equals("")==false)
                ar.add(i);
        }
        return ar;
    }

    private void setGridlayout(){
        int cotChuMax = arrChu.size();
        if (cotChuMax>7)
            cotChuMax = 7;
        if(cotChuMax!=0) {
            gridLayoutManagerchu = new GridLayoutManager(this, cotChuMax);
            rcvChu.setLayoutManager(gridLayoutManagerchu);
        }
    }
}