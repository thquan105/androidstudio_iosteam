package com.example.learnenglishvocab.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.learnenglishvocab.Game1;
import com.example.learnenglishvocab.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;

public class GameChonHinhAdapter extends FirebaseRecyclerAdapter<Game1,GameChonHinhAdapter.myViewHolder> {

    /**
     * Initialize a {@link RecyclerView.Adapter} that listens to a Firebase query. See
     * {@link FirebaseRecyclerOptions} for configuration options.
     *
     * @param options
     */
    public GameChonHinhAdapter(@NonNull FirebaseRecyclerOptions<Game1> options) {
        super(options);
    }



    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull Game1 model) {
        holder.tvNghiaTuVung.setText(model.getNghiatuvung());
        Glide.with(holder.imgGame.getContext())
                .load(model.getImg())
                .placeholder(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark)
                .circleCrop()
                .error(com.firebase.ui.database.R.drawable.common_google_signin_btn_icon_dark_normal)
                .into(holder.imgGame);
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_game1,parent,false);

        return new myViewHolder(view);
    }



    class myViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgGame;
        private TextView tvNghiaTuVung;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);
            imgGame = itemView.findViewById(R.id.img_game);
            tvNghiaTuVung = itemView.findViewById(R.id.tv_img);
        }
    }
}
