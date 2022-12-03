package com.example.learnenglishvocab.Adapter;

import static android.content.ContentValues.TAG;

import android.content.Context;
import android.nfc.Tag;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnenglishvocab.Game1;
import com.example.learnenglishvocab.GameChonHinh;
import com.example.learnenglishvocab.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class Game1Adapter extends RecyclerView.Adapter<Game1Adapter.Game1ViewHolder> {

    private ArrayList<Game1> game1List;
    private Context ctx;

    public Game1Adapter(ArrayList<Game1> game1List, GameChonHinh gameChonHinh){

        this.game1List = game1List;
        this.ctx = ctx;

    }
    @NonNull
    @Override
    public Game1ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game1,parent,false);

        return new Game1ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Game1ViewHolder holder, int position) {
        Game1 game1 = game1List.get(position);
        if (game1 != null){
            Log.d(TAG,"ABCJUKD");
            return;
        }
        Picasso.with(this.ctx).load(game1.getImg()).into(holder.imgGame);
        holder.tvNghiaTuVung.setText(game1.getNghiatuvung());

    }

    @Override
    public int getItemCount() {
        if(game1List != null){
            return game1List.size();
        }
        return 0;
    }

    public class Game1ViewHolder extends RecyclerView.ViewHolder{

         ImageView imgGame;
         TextView tvNghiaTuVung;
        public Game1ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGame = itemView.findViewById(R.id.img_game);
            tvNghiaTuVung = itemView.findViewById(R.id.tv_img);
        }
    }
}
