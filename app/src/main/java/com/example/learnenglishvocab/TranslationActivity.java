package com.example.learnenglishvocab;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceControl;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.button.MaterialButton;
import com.google.mlkit.common.model.DownloadConditions;
import com.google.mlkit.nl.translate.TranslateLanguage;
import com.google.mlkit.nl.translate.Translation;
import com.google.mlkit.nl.translate.Translator;
import com.google.mlkit.nl.translate.TranslatorOptions;

import java.sql.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class TranslationActivity extends AppCompatActivity {

    private EditText sourceLanguageEt;
    private TextView destinationLanguageTv, NgonNgu1, NgonNgu2;
    private Button Xoa;
    private Button  translateBtn, btnChange;
    private TranslatorOptions translatorOptions;
    private Translator translator;
    private ProgressDialog processDialog;
    private ArrayList<ModelLanguage> languageArrayList;
    private ImageView bt_back;

    private static final String TAG="MAIN_TAG";
    private String sourceLanguageCode ="en";
    private String destinationLanguageCode = "vi";
    private String sourceLanguageText = "";

    public TranslationActivity() {
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation);

        sourceLanguageEt = findViewById(R.id.sourceLanguageEt);
        destinationLanguageTv = findViewById(R.id.destinationLanguageTv);
        NgonNgu1 = findViewById(R.id.tvNgonNgu1);
        NgonNgu2 = findViewById(R.id.tvNgonNgu2);
        Xoa = findViewById(R.id.btnXoa);

        translateBtn = (Button) findViewById(R.id.translateBtn);
        btnChange = findViewById(R.id.btnHoanDoi);
        processDialog = new ProgressDialog(this);
        processDialog.setTitle("Please wait");
        processDialog.setCanceledOnTouchOutside(false);

        loadAvailableLanguages();

        bt_back = (ImageView) findViewById(R.id.btn_back);
        bt_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });




        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String hoanvi = new String();
                hoanvi = destinationLanguageCode;
                destinationLanguageCode =sourceLanguageCode;
                sourceLanguageCode = hoanvi;
                if(sourceLanguageCode == "en"){
                    NgonNgu1.setText("English");
                    NgonNgu2.setText("Việt Nam");
                }
                else{
                    NgonNgu1.setText("Việt Nam");
                    NgonNgu2.setText("English");
                }

                sourceLanguageEt.setText(destinationLanguageTv.getText().toString());

            }
        });

        translateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                validataData();
            }
        });

        Xoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sourceLanguageEt.setText("");
            }
        });
    }


    private void validataData() {
        sourceLanguageText = sourceLanguageEt.getText().toString().trim();
        Log.d(TAG, "validataData: sourceLanguage: "+sourceLanguageText);

        if(sourceLanguageText.isEmpty()){
            Toast.makeText(this, "Enter text to translate...", Toast.LENGTH_SHORT).show();
        } else {
            startTranslations();

        }
    }

    private void HoanVi(String a, String b){
        String hoanvi ="";
        hoanvi = a;
        a = b;
        b = hoanvi;
    }
    private void startTranslations() {



        translatorOptions = new TranslatorOptions.Builder().setSourceLanguage(sourceLanguageCode).setTargetLanguage(destinationLanguageCode).build();
        translator = Translation.getClient(translatorOptions);
        DownloadConditions downloadConditions = new DownloadConditions.Builder().requireWifi().build();

        translator.downloadModelIfNeeded(downloadConditions).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Log.d(TAG,"onSuccess: model ready, starting translate...");

                processDialog.setMessage("Translating...");
                translator.translate(sourceLanguageText).addOnSuccessListener(new OnSuccessListener<String>() {
                    @Override
                    public void onSuccess(String translatedText) {

                        Log.d(TAG,"onSuccess: translatedText"+translatedText);
                        destinationLanguageTv.setText(translatedText);
                    }
                })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                processDialog.dismiss();
                                Log.e(TAG, "onFailure",e);
                                Toast.makeText(TranslationActivity.this, "Failed to translated due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        });
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                processDialog.dismiss();
                Log.e(TAG, "onFailure",e);
                Toast.makeText(TranslationActivity.this, "Failed to translated due to"+e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void loadAvailableLanguages() {
        languageArrayList = new ArrayList<>();
        List<String> languageCodeList = TranslateLanguage.getAllLanguages();

        for (String languageCode: languageCodeList){
            String languageTitle = new Locale(languageCode).getDisplayLanguage();
            Log.d(TAG,"loadAvailableLanguages: languageCode: "+languageCode);
            Log.d(TAG,"loadAvailableLanguages: languageTitle: "+languageTitle);

            ModelLanguage modelLanguage = new ModelLanguage(languageCode,languageTitle);
            languageArrayList.add(modelLanguage);
        }

    }
}