package com.example.learnenglishvocab;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.app.ProgressDialog;
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
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

public class GameXepChu extends AppCompatActivity implements InterfaceClickChu {

    private RecyclerView rcvChu,rcvAnswer;
    private ArrayList<String> arrChu, arrAnswer,arrDung, arrGoiY, arrXaoGoiY,arrCheckTuGoiY;
    private TuKhoaAdapter chuAdapter,dapAnAdapter;
    private CardView cvRefresh, cvGoiY, cvSpeak;
    private Dialog dialog;
    private String tuTA,tuTV, idNhom;
    private int viTriTu = 0;
    private GridLayoutManager gridLayoutManagerchu,gridLayoutManagerAnswer;
    private TextView txtTuTV,txtGoiY;
    private MediaPlayer clicksound;
    private ArrayList<TuVungXepchu> arrTuVung;
    private ProgressDialog progressDialog;
    private TextToSpeech textToSpeech;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_xep_chu);

        arrTuVung = new ArrayList<>();
//        arrTuVung.add(new TuVungXepchu("  ","  "));
//        arrTuVung.add(new TuVungXepchu("Bakery","Bánh mì"));
//        arrTuVung.add(new TuVungXepchu("Country","Đất nước"));
//        arrTuVung.add(new TuVungXepchu("Friendship","Tình bạn"));
//        arrTuVung.add(new TuVungXepchu("Solution","Giải pháp"));


        Intent intent = getIntent();
        idNhom = intent.getStringExtra("IDNhom");

        progressDialog = new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Loading...");
        progressDialog.show();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("TuVung")
                    .whereEqualTo("IDNhomTu",idNhom)
                .get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> snapshotslist = queryDocumentSnapshots.getDocuments();
                        for(DocumentSnapshot snapshot:snapshotslist){
                            String tuvungthem = snapshot.getString("TuVung").replaceAll(" ","");
                            String nghiaTVthem = snapshot.getString("NghiaViet");
                            if (snapshot.getString("TuVung").length()>0 && snapshot.getString("NghiaViet").length()>0){
                                    arrTuVung.add(new TuVungXepchu(tuvungthem,nghiaTVthem));
                            }
    //                            System.out.println(snapshot.getString("TuVung").length() + "  |  " + snapshot.getString("NghiaViet").length());
                        }
                        if (progressDialog.isShowing()){
                            progressDialog.dismiss();
                        }
                        try {
                            Khoitao();
                        }catch (Exception e){
                            finish();
                            Toast.makeText(GameXepChu.this,"Chưa có dữ liệu",Toast.LENGTH_SHORT).show();
                        }
                    }
                });



        CardView cvBack = findViewById(R.id.cvback);
        cvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        CardView cvGuildGame2 = findViewById(R.id.cvhdg2);
        cvGuildGame2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(GameXepChu.this,GuildGameXepChu.class);
                startActivity(intent1);
            }
        });

        cvSpeak = findViewById(R.id.cvSpeak);
        cvSpeak.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Speak();
            }
        });
    }


    //KHỞI TẠO
    private void Khoitao(){
        rcvChu = findViewById(R.id.lvtukhoa);
        rcvChu.setNestedScrollingEnabled(false);
        rcvAnswer = findViewById(R.id.rcvdapan);
        txtTuTV = findViewById(R.id.tvNghiaG2);
        cvRefresh = findViewById(R.id.cvrefresh);
        txtGoiY = findViewById(R.id.txtGoiy);
        txtGoiY.setVisibility(View.GONE);
        cvGoiY = findViewById(R.id.cvtip);

        tuTV = arrTuVung.get(viTriTu).getNghia();
        tuTA = arrTuVung.get(viTriTu).getTuvung();
        tuTV = tuTV.substring(0, 1).toUpperCase() + tuTV.substring(1);
        txtTuTV.setText(tuTV);



        String[] strSplit = null;
        strSplit = tuTA.toUpperCase().split("");

        arrChu = new ArrayList<>();
        arrDung = new ArrayList<>();
        arrAnswer = new ArrayList<>();
        arrGoiY = new ArrayList<>();


        for(String i: strSplit){
            if (i.equals("")==false)
                arrDung.add(i);
        }

        txtGoiY.setText("");

        //GỢI Ý TỪ
        for (String s : arrDung){
            arrGoiY.add("_");
        }

        arrXaoGoiY = new ArrayList<>();

        arrXaoGoiY.addAll(arrDung);

        arrCheckTuGoiY = new ArrayList<>();
        arrCheckTuGoiY.addAll(arrDung);
        XaoTronChu(arrXaoGoiY);
        cvGoiY.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                txtGoiY.setText("");
                int sotudagoiy = 0;
                int vitrigoiy = 0;

                for(String s: arrGoiY){
                    if (!s.equals("_")){
                        sotudagoiy++;
                    }
                }
                if (sotudagoiy==arrGoiY.size()-1){
                    cvGoiY.setEnabled(false);
                }
                for (int i = 0;i<arrCheckTuGoiY.size();i++){
                    if (arrXaoGoiY.get(sotudagoiy).equals(arrCheckTuGoiY.get(i))){
                        vitrigoiy = i;
                        arrCheckTuGoiY.set(i, "*");
                        i=arrCheckTuGoiY.size();
//                        for (String s: arrCheckTuGoiY)
//                            System.out.println(s);
                    }
                }
                arrGoiY.set(vitrigoiy,arrDung.get(vitrigoiy));
                for (String s:arrGoiY) {
                    txtGoiY.setText(txtGoiY.getText()+s);
                }

                txtGoiY.setVisibility(View.VISIBLE);

            }
        });


        //phát âm
        textToSpeech = new TextToSpeech(this, new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int i) {
                if (i== TextToSpeech.SUCCESS){
                    int result = textToSpeech.setLanguage(Locale.ENGLISH);
                    if (result == TextToSpeech.LANG_MISSING_DATA
                            || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                        Toast.makeText(GameXepChu.this,"Language not supported",Toast.LENGTH_SHORT).show();
                    } else {
//                        mButtonSpeak.setEnabled(true);
                    }
                }else {
                    Log.e("Speak", "Initialization failed");
                }
            }
        });



        arrChu.addAll(arrDung);
        XaoTronChu(arrChu);


        //Làm mới rcv
        cvRefresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Lammoircv();
            }
        });

        chuAdapter = new TuKhoaAdapter(arrChu,this);
        dapAnAdapter = new TuKhoaAdapter(arrAnswer,this);

        setGridlayout();

        chuAdapter = new TuKhoaAdapter(arrChu, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String chu) {
                arrAnswer.add(chu);
                arrChu.remove(chu);

                setGridlayout();

                chuAdapter.notifyDataSetChanged();
                int cotAnsmax = arrAnswer.size();
                if (cotAnsmax>6)
                    cotAnsmax = 6;
                gridLayoutManagerAnswer = new GridLayoutManager(GameXepChu.this,cotAnsmax);
                rcvAnswer.setLayoutManager(gridLayoutManagerAnswer);
                dapAnAdapter.notifyDataSetChanged();
                if(arrAnswer.size()==arrDung.size()){
                    int check = 0;
                    for (int i = 0; i < arrAnswer.size();i++){
                        if(arrAnswer.get(i).equals(arrDung.get(i))==true){
                            check++;
                        }
                    }
                    if(check==arrDung.size()){
                        if (viTriTu == arrTuVung.size()-1){
                            startSound(R.raw.traloidung);
                            showCustomDialog(true);
                            TextView txtChucmung = dialog.findViewById(R.id.txtThongbaodialog);
                            txtChucmung.setText("Hoàn thành");
                            Button btn = dialog.findViewById(R.id.btnDlgG2Dung);
                            btn.setText("Thoát");
                            btn.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View view) {
                                    finish();
                                }
                            });
                        }
                        else {
                            showCustomDialog(true);
                            startSound(R.raw.traloidung);
                        }
                    }
                    else{
                        showCustomDialog(false);
                        startSound(R.raw.traloisai);
                        arrChu.removeAll(arrChu);
                        arrChu.addAll(arrDung);
                        XaoTronChu(arrChu);
                        Lammoircv();
                    }
                }

//                Toast.makeText(GameXepChu.this,chu,Toast.LENGTH_SHORT).show();
            }

        });

        dapAnAdapter = new TuKhoaAdapter(arrAnswer, new InterfaceClickChu() {
            @Override
            public void onItemClickChu(String tukhoa) {
//                Toast.makeText(GameXepChu.this,tukhoa,Toast.LENGTH_SHORT).show();
                arrChu.add(tukhoa);
                arrAnswer.remove(tukhoa);
                setGridlayout();
                chuAdapter.notifyDataSetChanged();
                dapAnAdapter.notifyDataSetChanged();
            }
        });

        rcvAnswer.setAdapter(dapAnAdapter);
        rcvChu.setAdapter(chuAdapter);
    }

    //PHÁT ÂM TỪ VỰNG
    private void Speak(){
        String text = arrTuVung.get(viTriTu).getTuvung();
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

    private void Lammoircv() {
        arrAnswer.removeAll(arrAnswer);
        dapAnAdapter.notifyDataSetChanged();
        arrChu.removeAll(arrChu);
        arrChu.addAll(arrDung);
        setGridlayout();
        XaoTronChu(arrChu);
        rcvChu.setAdapter(chuAdapter);
        rcvAnswer.setAdapter(dapAnAdapter);
    }

    @Override
    public void onItemClickChu(String tukhoa) {
    }

    private ArrayList<String> XaoTronChu(ArrayList<String> ar) {
        ArrayList<String> arrCheck = new ArrayList<>();
        arrCheck.addAll(ar);
        Random rnd = new Random();
        for (int i = ar.size() - 1; i > 0; i--) {
            int index = rnd.nextInt(i + 1);
            String a = ar.get(index);
            ar.set(index,ar.get(i));
            ar.set(i,a);
        }
        int demTrung = 0;
        for (int i=0;i<ar.size();i++){
            if (arrCheck.get(i).equals(ar.get(i))){
                demTrung++;
            }
        }
        if (demTrung>(arrCheck.size()/2)){
            return XaoTronChu(ar);
        }
        return ar;
    }

    private void showCustomDialog(boolean win){
        dialog = new Dialog(GameXepChu.this);
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
                    if (viTriTu < arrTuVung.size()){
                        viTriTu++;
                        tuTV = arrTuVung.get(viTriTu).getNghia();
                        tuTV = tuTV.substring(0, 1).toUpperCase() + tuTV.substring(1);
                        txtTuTV.setText(tuTV);
                        arrDung.removeAll(arrDung);
                        TachChu(arrTuVung.get(viTriTu).getTuvung(),arrDung);
                        Lammoircv();
                        refreshGoiY();
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

    private ArrayList<String> TachChu(String s,ArrayList<String> ar){
        ar.removeAll(ar);
        String[] strSplit = null;
        strSplit = s.toUpperCase().split("");

        for(String i: strSplit){
            if (i.equals("")==false)
                ar.add(i);
        }
        return ar;
    }

    private void setGridlayout(){
        int cotChuMax = arrChu.size();
        if (cotChuMax>7)
            cotChuMax = 7;
        if(cotChuMax!=0) {
            gridLayoutManagerchu = new GridLayoutManager(this, cotChuMax);
            rcvChu.setLayoutManager(gridLayoutManagerchu);
        }
    }
    private void startSound(int resources){
        clicksound = MediaPlayer.create(GameXepChu.this, resources);
        clicksound.start();
        try {
            if (clicksound.isPlaying()) {
                clicksound.stop();
                clicksound.release();
//                        clicksound = null;
                clicksound = MediaPlayer.create(GameXepChu.this, resources);
            } clicksound.start();
        } catch(Exception e) { e.printStackTrace(); }
    }
    private void refreshGoiY(){
        txtGoiY.setText("");
        cvGoiY.setEnabled(true);
        arrGoiY.removeAll(arrGoiY);
        for (String s : arrDung){
            arrGoiY.add("_");
        }
        arrXaoGoiY.removeAll(arrXaoGoiY);
        arrXaoGoiY.addAll(arrDung);
        XaoTronChu(arrXaoGoiY);
        arrCheckTuGoiY.removeAll(arrCheckTuGoiY);
        arrCheckTuGoiY.addAll(arrDung);
    }
}

