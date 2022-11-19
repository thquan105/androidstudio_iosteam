package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.Button;

import com.example.learnenglishvocab.Adapter.Game1Adapter;

import java.util.ArrayList;
import java.util.List;

public class GameChonHinh extends AppCompatActivity {

    private RecyclerView rcvGame1;
    private Button btQuayLai, btTiepTheo,btKiemtra;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamechonhinh);

        btQuayLai = findViewById(R.id.btnQuayLai);
        btTiepTheo = findViewById(R.id.btnTiepTheo);
        btKiemtra = findViewById(R.id.btnKiemtra);

        rcvGame1 = findViewById(R.id.rcv_game1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvGame1.setLayoutManager(gridLayoutManager);

        Game1Adapter adapter = new Game1Adapter(getGame1List());
        rcvGame1.setAdapter(adapter);
    }

    private List<Game1> getGame1List() {
        List<Game1> list = new ArrayList<>();
        list.add(new Game1(R.drawable.cafe,"cafe"));
        list.add(new Game1(R.drawable.chainuoc,"nuoc"));
        list.add(new Game1(R.drawable.hopsua,"sua"));
        list.add(new Game1(R.drawable.ruouvang,"ruou vang"));
        return list;
    }
}