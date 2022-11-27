package com.example.learnenglishvocab;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class TuKhoaAdapter extends RecyclerView.Adapter<TuKhoaAdapter.TuKhoaAdapterViewHolder>{
    private ArrayList<String> TuKhoaData;
    private InterfaceClickChu interfaceClickChu;

    public TuKhoaAdapter(ArrayList<String> chu, InterfaceClickChu interfaceClickChu) {
        this.TuKhoaData = chu;
        this.interfaceClickChu = interfaceClickChu;
    }

    @NonNull
    @Override
    public TuKhoaAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_word,parent,false);
        return new TuKhoaAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TuKhoaAdapterViewHolder holder, int position) {
        holder.bindData(TuKhoaData.get(position));
    }

    @Override
    public int getItemCount() {
        return TuKhoaData.size();
    }


    //CLASS TUKHOAADAPTER VIEWHOLDER
    public class TuKhoaAdapterViewHolder extends RecyclerView.ViewHolder{
        private TextView txtTuVung;
        private String tukhoa;

        public TuKhoaAdapterViewHolder(@NonNull View itemView) {
            super(itemView);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    interfaceClickChu.onItemClickChu(tukhoa);
//                    if (itemView.getVisibility()==View.VISIBLE)
//                        itemView.setVisibility(View.GONE);
                }
            });

            txtTuVung = itemView.findViewById(R.id.tvChu);


        }
        private void bindData(String tu){
            this.tukhoa = tu;
            txtTuVung.setText(tu);
        }
    }
}
