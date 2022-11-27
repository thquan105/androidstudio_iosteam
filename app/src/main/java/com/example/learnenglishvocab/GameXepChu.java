package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

public class GameXepChu extends AppCompatActivity implements InterfaceClickChu {

    private RecyclerView rcvChu,rcvAnswer;
    private ArrayList<String> arrChu, arrAnswer,arrDung;
    private TuKhoaAdapter chuAdapter,dapAnAdapter;
    private CardView imgbtnRefresh;
    private Dialog dialogDung,dialogSai;
    private String tuTA = "pencils";
    final private int maxCotGridlayout = 7;
    private GridLayoutManager gridLayoutManagerchu,gridLayoutManagerAnswer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_xep_chu);
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
        shuffleArray(arrChu);


        //Làm mới rcv
        imgbtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lammoircv();
            }
        });

        chuAdapter = new TuKhoaAdapter(arrChu,this);
        dapAnAdapter = new TuKhoaAdapter(arrAnswer,this);

        int cotChuMax = arrChu.size();
        if (cotChuMax>7)
            cotChuMax = 7;
        gridLayoutManagerchu = new GridLayoutManager(this,cotChuMax);
        rcvChu.setLayoutManager(gridLayoutManagerchu);

        chuAdapter = new TuKhoaAdapter(arrChu, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String chu) {
                arrAnswer.add(chu);
                int cotAnsmax = arrAnswer.size();
                if (cotAnsmax>6)
                    cotAnsmax = 6;
                gridLayoutManagerAnswer = new GridLayoutManager(GameXepChu.this,cotAnsmax);
                rcvAnswer.setLayoutManager(gridLayoutManagerAnswer);
                dapAnAdapter.notifyDataSetChanged();
                if(arrAnswer.size()==arrChu.size()){

                    int check = 0;
                    for (int i = 0; i < arrAnswer.size();i++){
                        if(arrAnswer.get(i).equals(arrDung.get(i))==true){
                            check++;
                        }
                    }
                    if(check==arrDung.size()){
                        dialogDung = new Dialog(GameXepChu.this);
                        dialogDung.setContentView(R.layout.dialog_game2_dung);

                        Button btnnext =dialogDung.findViewById(R.id.btnDlgG2Dung);
                        btnnext.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogDung.dismiss();
                                Lammoircv();
                            }
                        });
                        dialogDung.show();
                    }
                    else{
                        for (String s: arrDung)
                            System.out.println(s);
//                        Toast.makeText(GameXepChu.this,"Đồ ngu",Toast.LENGTH_SHORT).show();
                        dialogSai = new Dialog(GameXepChu.this);
                        dialogSai.setContentView(R.layout.dialog_game2_sai);
                        dialogSai.show();
                        Button btntry =dialogSai.findViewById(R.id.btnDlgG2Sai);
                        btntry.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dialogSai.dismiss();
                            }
                        });
                        arrChu.removeAll(arrChu);
                        arrChu.addAll(shuffleArray(arrDung));
                        Lammoircv();
                    }
                }

//                Toast.makeText(GameXepChu.this,chu,Toast.LENGTH_SHORT).show();
//                for (int i = 0; i < arrChu.size();i++){
//                    if(arrChu.get(i).equals(chu)==true){
//                        arrChu.set(i,"");
//                        chuAdapter.notifyDataSetChanged();
//                        i=arrChu.size();
////                        Toast.makeText(GameXepChu.this,chu,Toast.LENGTH_SHORT).show();
//                    }
//                }
            }
        });

        rcvAnswer.setAdapter(dapAnAdapter);
        rcvChu.setAdapter(chuAdapter);
    }

    private void Lammoircv() {
        arrAnswer.removeAll(arrAnswer);
        dapAnAdapter.notifyDataSetChanged();
        rcvChu.setAdapter(chuAdapter);
        rcvAnswer.setAdapter(dapAnAdapter);
    }

    @Override
    public void onItemClickChu(String tukhoa) {
    }
    private ArrayList<String> shuffleArray(ArrayList<String> ar) {
        Random rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar.get(index);
            ar.set(index,ar.get(i));
            ar.set(i,a);
        }
        return ar;
    }
}