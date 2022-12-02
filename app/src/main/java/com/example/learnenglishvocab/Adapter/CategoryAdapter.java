package com.example.learnenglishvocab.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.learnenglishvocab.GameChonHinh;
import com.example.learnenglishvocab.GameXepChu;
import com.example.learnenglishvocab.Models.Category;
import com.example.learnenglishvocab.R;
import com.google.firebase.firestore.AggregateQuery;
import com.google.firebase.firestore.AggregateQuerySnapshot;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.myViewHoder> {
    Context context;
    ArrayList<Category> dsCategory;
    int a;

    public CategoryAdapter(Context context, ArrayList<Category> dsCategory, int a) {
        this.context = context;
        this.dsCategory = dsCategory;
        this.a = a;
    }

    @NonNull
    @Override
    public myViewHoder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (a == 1) {
            View view = LayoutInflater.from(context).inflate(R.layout.item_category, parent, false);
            return new myViewHoder(view);
        } else {
            View view = LayoutInflater.from(context).inflate(R.layout.item_category2, parent, false);
            return new myViewHoder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull myViewHoder holder, int position) {
        Category category = dsCategory.get(position);

        holder.name.setText(category.getTenNhomTu());
        //Set hinhanh
        Glide
                .with(holder.img.getContext())
                .load(category.getHinhanh())
                .apply(new RequestOptions()
                    .placeholder(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark)
                    .fitCenter()
                    .error(com.google.android.gms.base.R.drawable.common_google_signin_btn_icon_dark_normal))
                .into(holder.img);
        //Dem so tu cua nhom tu
        FirebaseFirestore mydb;
        mydb = FirebaseFirestore.getInstance();
        AggregateQuery countQuery = mydb.collection("TuVung")
                .whereEqualTo("IDNhomTu",category.getIDNhomTu()).count();
        countQuery.get(AggregateSource.SERVER).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                AggregateQuerySnapshot snapshot = task.getResult();
                System.out.println("Count: " + snapshot.getCount());
                holder.numWord.setText(""+snapshot.getCount());
            } else {
                System.out.println( "Count failed: "+ task.getException());
            }
        });
        //Click Nhom tu
        if (a == 1) {
            holder.cv_game_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Truyen IDNhomTu sang game 1
                    Intent intent = new Intent(view.getContext(), GameChonHinh.class);
                    intent.putExtra("IDNhom", category.getIDNhomTu());
                    view.getContext().startActivity(intent);
                }
            });
        } else {
            holder.cv_game_2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //Truyen IDNhomTu sang game 2
                    Intent intent = new Intent(view.getContext(), GameXepChu.class);
                    intent.putExtra("IDNhom", category.getIDNhomTu());
                    view.getContext().startActivity(intent);
                }
            });
        }
    }


    @Override
    public int getItemCount() {
        return dsCategory.size();
    }

    class myViewHoder extends RecyclerView.ViewHolder{
        ImageView img;
        TextView name, numWord;
        CardView cv_game_1, cv_game_2;

        public myViewHoder(@NonNull View itemView) {
            super(itemView);

            img = (ImageView) itemView.findViewById(R.id.img_avatar_cate);
            name = (TextView) itemView.findViewById(R.id.tv_Categary);
            numWord = (TextView) itemView.findViewById(R.id.tv_sotu);
            cv_game_1 = (CardView) itemView.findViewById(R.id.game_card_1);
            cv_game_2 = (CardView) itemView.findViewById(R.id.game_card_2);
        }
    }
}
