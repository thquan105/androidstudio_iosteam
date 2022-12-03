package com.example.learnenglishvocab;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnenglishvocab.Adapter.Game1Adapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameChonHinh extends AppCompatActivity implements InterfaceClickCard{

    private RecyclerView rcvGame1;
    private Button btQuayLai, btTiepTheo,btKiemtra;
    private TextView tvTuvung;
    private ArrayList<Game1> arrFirebase, arrGame, arrTuVung;
    private Dialog dialog;
    private Game1Adapter adapter;
    private int viTriTu = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamechonhinh);

        btQuayLai = findViewById(R.id.btnQuayLai);
        btTiepTheo = findViewById(R.id.btnTiepTheo);
        btKiemtra = findViewById(R.id.btnKiemtra);
        tvTuvung = findViewById(R.id.txtTuvung);
        rcvGame1 = findViewById(R.id.rcv_game1);

        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvGame1.setLayoutManager(gridLayoutManager);


        arrFirebase = new ArrayList<>();
        arrGame = new ArrayList<>();
        arrTuVung = new ArrayList<>();
//        arrFirebase.add(new Game1("Rabiz","Thỏ","https://firebasestorage.googleapis.com/v0/b/learnenglishvocab-6ef61.appspot.com/o/Image%20Category%2Fimg_ca_animal.jpg?alt=media&token=5271a019-e753-45d7-8f90-44f507f0bc50"));
//        arrFirebase.add(new Game1("Rabiz","Thỏ","https://firebasestorage.googleapis.com/v0/b/learnenglishvocab-6ef61.appspot.com/o/Image%20Category%2Fimg_ca_animal.jpg?alt=media&token=5271a019-e753-45d7-8f90-44f507f0bc50"));
//        arrFirebase.add(new Game1("Rabiz","Thỏ","https://firebasestorage.googleapis.com/v0/b/learnenglishvocab-6ef61.appspot.com/o/Image%20Category%2Fimg_ca_animal.jpg?alt=media&token=5271a019-e753-45d7-8f90-44f507f0bc50"));
//        arrFirebase.add(new Game1("Rabiz","Thỏ","https://firebasestorage.googleapis.com/v0/b/learnenglishvocab-6ef61.appspot.com/o/Image%20Category%2Fimg_ca_animal.jpg?alt=media&token=5271a019-e753-45d7-8f90-44f507f0bc50"));
//

       FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TuVung")
//               .whereLessThan("")
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotslist = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:snapshotslist){
                            String tuvung= snapshot.getString("TuVung");
                            String Nghia = snapshot.getString("NghiaViet");
                            String HinhAnh = snapshot.getString("HinhAnh");
                            if (snapshot.getString("TuVung").length()>0 && snapshot.getString("NghiaViet").length()>0 && snapshot.getString("HinhAnh").length()>0){

                                arrFirebase.add(new Game1(tuvung,Nghia,HinhAnh));
//                                if(arrFirebase.size()==0)
//                                    Toast.makeText(GameChonHinh.this, "Thuaaaa", Toast.LENGTH_SHORT).show();
//                                else
//                                    Toast.makeText(GameChonHinh.this, "Ngon"+ arrFirebase.size(), Toast.LENGTH_SHORT).show();
                            }
                        }

                        arrTuVung = arrFirebase;
                        arrGame = thuchien(arrGame);

                        adapter = new Game1Adapter(arrGame, GameChonHinh.this);
                        rcvGame1.setAdapter(adapter);
                        adapter = new Game1Adapter(arrGame, new InterfaceClickCard() {
                            @Override
                            public void onItemClickCard(Game1 game1) {
                                Toast.makeText(GameChonHinh.this, game1.getNghiatuvung(), Toast.LENGTH_SHORT).show();
                                Log.d(TAG, "Day ne loi o day ne");
                                showCustomDialog(true);
                    }
                });
            }
        });
    }

    //arrGame: list Đáp ÁN
    //arrFirebase: all 10 tu
    //arrTuVung: tuong duong arrFire dung de remove

    private ArrayList<Game1> thuchien (ArrayList<Game1> arr){
        // Lấy từ thứ 0
        arr.add(arrFirebase.get(viTriTu));
        //
        tvTuvung.setText(arrFirebase.get(viTriTu).getTuvung());
        //xoá từ
        arrTuVung.removeAll(arr);

        xaoTronList(arrTuVung);
        arr.add(arrTuVung.get(1));
        arr.add(arrTuVung.get(2));
        arr.add(arrTuVung.get(3));
        xaoTronList(arr);
        return arr;
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
        arrGame.removeAll(arrGame);
        adapter.notifyDataSetChanged();

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

    @Override
    public void onItemClickCard(Game1 game1) {

    }
}