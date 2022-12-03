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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learnenglishvocab.Game1;
import com.example.learnenglishvocab.GameChonHinh;
import com.example.learnenglishvocab.InterfaceClickCard;
import com.example.learnenglishvocab.R;

import java.util.ArrayList;
import java.util.List;

public class Game1Adapter extends RecyclerView.Adapter<Game1Adapter.Game1ViewHolder> {
    private InterfaceClickCard interfaceClickCard;
    ArrayList<Game1> game1List;
    Context ctx;

    public Game1Adapter(ArrayList<Game1> game1List, InterfaceClickCard interfaceClickCard) {
        this.interfaceClickCard = interfaceClickCard;
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
        if (game1 == null){
            Log.d(TAG,"ABCJUKD");
            return;
        }
        holder.tvNghiaTuVung.setText(game1.getNghiatuvung());

        Glide
                .with(holder.imgGame.getContext())
                .load(game1.getImg())
                .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                .fitCenter()
                .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgGame);

        holder.cardView_item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceClickCard.onItemClickCard(game1);
            }
        });

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
         private CardView cardView_item;

        public Game1ViewHolder(@NonNull View itemView) {
            super(itemView);

            imgGame = itemView.findViewById(R.id.img_game);
            tvNghiaTuVung = itemView.findViewById(R.id.tv_img);
            cardView_item = itemView.findViewById(R.id.Card_Item_1);
        }
    }
}
