package com.example.learnenglishvocab.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.learnenglishvocab.Game1;
import com.example.learnenglishvocab.R;

import java.util.List;

public class Game1Adapter extends RecyclerView.Adapter<Game1Adapter.Game1ViewHolder> {

    private List<Game1> game1List;

    public Game1Adapter(List<Game1> game1List){
        this.game1List = game1List;
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
            return;
        }

    }

    @Override
    public int getItemCount() {
        if(game1List != null){
            return game1List.size();
        }
        return 0;
    }

    public class Game1ViewHolder extends RecyclerView.ViewHolder{

        private ImageView imgGame;
        private TextView tvNghiaTuVung;
        public Game1ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGame = itemView.findViewById(R.id.img_game);
            tvNghiaTuVung = itemView.findViewById(R.id.tv_img);
        }
    }
}
