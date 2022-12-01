package com.example.learnenglishvocab;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;

import com.example.learnenglishvocab.Adapter.CategoryAdapter;
import com.example.learnenglishvocab.Models.Category;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Random;

public class Game1Activity extends AppCompatActivity {
    RecyclerView recyclerView;
    ArrayList<Category> categoryArrayList;
    CategoryAdapter categoryAdapter;
    FirebaseFirestore mydb;
    ProgressDialog progressDialog;
    Random random = new Random();
    boolean a = random.nextBoolean();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game1);

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data...");
        progressDialog.show();

        recyclerView = findViewById(R.id.rcv_game1);
        recyclerView.setHasFixedSize(true);
        if (a) {
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
        } else {
            recyclerView.setLayoutManager(new GridLayoutManager(this, 2));
        }
        categoryArrayList = new ArrayList<Category>();
        EventChangeListener();

        categoryAdapter = new CategoryAdapter(Game1Activity.this, categoryArrayList,a);

        recyclerView.setAdapter(categoryAdapter);
    }

    private void EventChangeListener() {
        mydb = FirebaseFirestore.getInstance();
        mydb.collection("NhomTu")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if (error != null){
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                            Log.e("Firestore Error", error.getMessage());
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){
                            if(dc.getType() == DocumentChange.Type.ADDED){
                                categoryArrayList.add(dc.getDocument().toObject(Category.class));
                            }
                            categoryAdapter.notifyDataSetChanged();
                            if(progressDialog.isShowing())
                                progressDialog.dismiss();
                        }
                    }
                });
    }
}