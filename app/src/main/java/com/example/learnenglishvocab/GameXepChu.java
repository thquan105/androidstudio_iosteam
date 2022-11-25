package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class GameXepChu extends AppCompatActivity implements InterfaceClickChu {

    private RecyclerView rcvListPhim;
    private ArrayList<String> arrChu;
    private TuKhoaAdapter chuAdapter;
    private ImageButton imgbtnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_xep_chu);
        init();
    }

    private void init(){
        rcvListPhim = findViewById(R.id.lvtukhoa);
        imgbtnRefresh = findViewById(R.id.btnRefresh);

        arrChu = new ArrayList<>();
        arrChu.add(new String("T"));
        arrChu.add(new String("E"));
        arrChu.add(new String("L"));
        arrChu.add(new String("L"));

        imgbtnRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                rcvListPhim.setAdapter(chuAdapter);
            }
        });

        chuAdapter = new TuKhoaAdapter(arrChu,this);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,7);
        rcvListPhim.setLayoutManager(gridLayoutManager);
        chuAdapter = new TuKhoaAdapter(arrChu, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String chu) {
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
        rcvListPhim.setAdapter(chuAdapter);
    }
    @Override
    public void onItemClickChu(String tukhoa) {
    }
}