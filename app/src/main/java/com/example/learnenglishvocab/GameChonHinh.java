package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;

import com.example.learnenglishvocab.Adapter.Game1Adapter;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameChonHinh extends AppCompatActivity {

    private RecyclerView rcvGame1;
    private Button btQuayLai, btTiepTheo,btKiemtra;
    private ArrayList<Game1> arrFirebase, arrGame;
    private Dialog dialog;
    private int viTriTu = 0;

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

        arrFirebase = new ArrayList<>();
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TuVung")
//               .whereLessThan("")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotslist = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:snapshotslist){

                            if (snapshot.getString("TuVung").length()>0 && snapshot.getString("NghiaViet").length()>0 && snapshot.getString("HinhAnh").length()>0){
                                arrFirebase.add(new Game1(snapshot.getString("TuVung").toString(),snapshot.getString("NghiaViet").toString(),snapshot.getString("HinhAnh").toString()));
                            }
                        }
                    }
                });
    }
    private void thuchien (){
        arrGame.add(arrFirebase.get(viTriTu));
        arrFirebase.remove(arrGame);
        xaoTronList(arrFirebase);
        arrGame.add(arrFirebase.get(1));
        arrGame.add(arrFirebase.get(2));
        arrGame.add(arrFirebase.get(3));
        xaoTronList(arrGame);


    }
    private ArrayList<Game1> xaoTronList(ArrayList<Game1> ar) {
        Random rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            Game1 a = ar.get(index);
            ar.set(index,ar.get(i));
            ar.set(i,a);
        }
        return ar;
    }

    private void Lammoircv() {

    }

    private void showCustomDialog(boolean win){
        dialog = new Dialog(GameChonHinh.this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCancelable(false);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        if (window==null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();
        windowAttribute.gravity = Gravity.CENTER;
        window.setAttributes(windowAttribute);

        if (win){
            dialog.setContentView(R.layout.dialog_game2_dung);
            Button btnnext = dialog.findViewById(R.id.btnDlgG2Dung);
            btnnext.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if (viTriTu < arrFirebase.size()){
                        viTriTu++;
                        arrGame.removeAll(arrGame);
                        Lammoircv();
                        dialog.dismiss();
                    }
                }
            });
        }
        else{
            dialog.setContentView(R.layout.dialog_game2_sai);

            Button btntry = dialog.findViewById(R.id.btnDlgG2Sai);
            btntry.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    dialog.dismiss();
                }
            });
        }


        dialog.show();
    }

    private List<Game1> getGame1List() {
        List<Game1> list = new ArrayList<>();
        return list;
    }
}