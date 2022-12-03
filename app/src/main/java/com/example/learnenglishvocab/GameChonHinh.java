package com.example.learnenglishvocab;

import static android.content.ContentValues.TAG;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.learnenglishvocab.Adapter.Game1Adapter;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameChonHinh extends AppCompatActivity implements InterfaceClickCard{

    private RecyclerView rcvGame1;
    private TextView tvTuvung;
    private ImageButton btnPhatAm, btnClose;
    private ArrayList<Game1> arrFirebase, arrGame, arrTuVung;
    private Dialog dialog;
    private MediaPlayer clicksound;
    private Game1Adapter adapter;
    private int viTriTu = 0;
    private TextToSpeech textToSpeech;
    private String idNhom;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gamechonhinh);

        tvTuvung = findViewById(R.id.txtTuvung);
        rcvGame1 = findViewById(R.id.rcv_game1);
        btnPhatAm = findViewById(R.id.imgBtnPhatAm);
        btnClose = findViewById(R.id.btn_close_game1);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 2);
        rcvGame1.setLayoutManager(gridLayoutManager);


        arrFirebase = new ArrayList<>();
        arrGame = new ArrayList<>();
        arrTuVung = new ArrayList<>();


        Intent intent = getIntent();
        idNhom = intent.getStringExtra("IDNhom");


       FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("TuVung")
                .whereEqualTo("IDNhomTu",idNhom)
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
                            }
                        }

                        arrTuVung.addAll(arrFirebase);
                        arrGame = thuchien(arrGame);

                        adapter = new Game1Adapter(arrGame, GameChonHinh.this);

                        adapter = new Game1Adapter(arrGame, new InterfaceClickCard() {
                            @Override
                            public void onItemClickCard(Game1 game1) {

                                if(game1.getTuvung().equals(tvTuvung.getText().toString())){
                                    if(viTriTu == arrFirebase.size()-1){
                                        startSound(R.raw.traloidung);
                                        showCustomDialog(true);
                                        TextView txtChucmung = dialog.findViewById(R.id.txtThongbaodialog);
                                        txtChucmung.setText("Hoàn thành");
                                        Button btn = dialog.findViewById(R.id.btnDlgG2Dung);
                                        btn.setText("OK");
                                        btn.setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                finish();
                                            }
                                        });
                                    }
                                   else{
                                        startSound(R.raw.traloidung);
                                        showCustomDialog(true);
                                    }

                                }
                                else{
                                    showCustomDialog(false);
                                    startSound(R.raw.traloisai);
                                }

                            }
                        });
                        rcvGame1.setAdapter(adapter);
                    }
                });



        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i== TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(GameChonHinh.this,"Language not supported",Toast.LENGTH_SHORT).show();
                    }
                }else {
                    Log.e("Speak", "Initialization failed");
                }
            }
        });

        btnPhatAm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speak();
            }
        });

        btnClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CardView cvGuildGame1 = findViewById(R.id.cvhdg2);
        cvGuildGame1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GameChonHinh.this,HuongDanActivity.class);
                startActivity(intent1);
            }
        });


    }

    private void startSound(int resources){
        clicksound = MediaPlayer.create(GameChonHinh.this, resources);
        clicksound.start();
        try {
            if (clicksound.isPlaying()) {
                clicksound.stop();
                clicksound.release();
//                        clicksound = null;
                clicksound = MediaPlayer.create(GameChonHinh.this, resources);
            } clicksound.start();
        } catch(Exception e) { e.printStackTrace(); }
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
        arrTuVung.removeAll(arrTuVung);
        arrTuVung.addAll(arrFirebase);
        rcvGame1.setAdapter(adapter);
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
                        Lammoircv();
                        arrGame =thuchien(arrGame);

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




    @Override
    public void onItemClickCard(Game1 game1) {

    }
    private void Speak(){
        String text = arrFirebase.get(viTriTu).getTuvung();
        textToSpeech.setPitch(1);
//        textToSpeech.setSpeechRate(1);

        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
    @Override
    protected void onDestroy() {
        if (textToSpeech != null) {
            textToSpeech.stop();
            textToSpeech.shutdown();
        }
        super.onDestroy();
    }
}