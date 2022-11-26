package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class GameXepChu extends AppCompatActivity implements InterfaceClickChu {

    private RecyclerView rcvChu,rcvAnswer;
    private ArrayList<String> arrChu, arrAnswer,arrDung;
    private TuKhoaAdapter chuAdapter,dapAnAdapter;
    private ImageButton imgbtnRefresh;
    private String tuTA = "pencil";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_xep_chu);
        init();
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

        imgbtnRefresh = findViewById(R.id.btnRefresh);

        String[] strSplit = null;
        strSplit = tuTA.toUpperCase().split("");

        arrChu = new ArrayList<>();
        arrDung = new ArrayList<>();

        for(String i: strSplit){
            if (i.equals("")==false)
                arrDung.add(i);
        }

        arrChu.addAll(shuffleArray(arrDung));

        arrAnswer = new ArrayList<>();

        //Làm mới rcv
        imgbtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lammoircv();
            }
        });

        chuAdapter = new TuKhoaAdapter(arrChu,this);
        dapAnAdapter = new TuKhoaAdapter(arrAnswer,this);

        GridLayoutManager gridLayoutManagerchu,gridLayoutManagerAnswer;
        gridLayoutManagerchu = new GridLayoutManager(this,7);
        gridLayoutManagerAnswer = new GridLayoutManager(this,7);
        rcvChu.setLayoutManager(gridLayoutManagerchu);
        rcvAnswer.setLayoutManager(gridLayoutManagerAnswer);

        chuAdapter = new TuKhoaAdapter(arrChu, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String chu) {
                arrAnswer.add(chu);
                dapAnAdapter.notifyDataSetChanged();
                if(arrAnswer.size()==arrChu.size()){
                    int check = 0;
                    for (int i = 0; i < arrChu.size();i++){
                        if(arrChu.get(i).equals(arrDung.get(i))==true){
                            check++;
                        }
                    }
                    if(check==arrChu.size()){
                        Toast.makeText(GameXepChu.this,"Thông minh lắm",Toast.LENGTH_SHORT).show();
                    }
                    else{
                        Toast.makeText(GameXepChu.this,"Đồ ngu",Toast.LENGTH_SHORT).show();
                        arrChu.removeAll(arrChu);
                        arrChu.addAll(shuffleArray(arrDung));
                        Lammoircv();
                    }
                }

//                goDetail(phim);
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
    private String[] shuf2fleArray(String[] ar) {
        Random rnd = new Random();
        for (int i = ar.length - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar[index];
            ar[index] = ar[i];
            ar[i] = a;
        }
        return ar;
    }
}