package com.tradecalc.lernjava;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.tradecalc.lernjava.databinding.ActivityMain2Binding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


public class MainActivity2 extends AppCompatActivity {

    private Document doc = null;
    private Thread secThread;
    private  Runnable runnable;
    private  ActivityMain2Binding bunding2;
    private boolean onclicEvro = false;
    private boolean onclicDolar = false;
    private boolean onclicYany = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding2 = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(bunding2.getRoot());

        init();
        bunding2.imageViewEvroBacgraund.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onclicEvro == false & onclicDolar == false & onclicYany == false){
                bunding2.imageViewEvroBacgraund.setBackground(getDrawable(R.drawable.stily_select_butthon));
                onclicEvro = true;
            }else {
                onclicEvro = false;
                bunding2.imageViewEvroBacgraund.setBackground(getDrawable(R.drawable.style_cnvert_button));
            }
        }
    });

    bunding2.imageViewDolarBacgraund.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onclicDolar == false & onclicEvro == false & onclicYany == false){
                bunding2.imageViewDolarBacgraund.setBackground(getDrawable(R.drawable.stily_select_butthon));
                onclicDolar = true;
            }else {
                onclicDolar = false;
                bunding2.imageViewDolarBacgraund.setBackground(getDrawable(R.drawable.style_cnvert_button));
            }
        }
    });

    bunding2.imageViewYanyBacgraund.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onclicYany == false & onclicEvro == false & onclicDolar == false){
                bunding2.imageViewYanyBacgraund.setBackground(getDrawable(R.drawable.stily_select_butthon));
                onclicYany = true;
            }else {
                onclicYany = false;
                bunding2.imageViewYanyBacgraund.setBackground(getDrawable(R.drawable.style_cnvert_button));
            }
        }
    });

    bunding2.buttonCalcRezult.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (onclicDolar  == false & onclicEvro == false & onclicYany == false){
               showInfo("Выберете валюту ");
            } else if (onclicDolar == true) {
                Double rezconvertDolar = Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / 92.45;
                String rezultDolar = String.format("%.2f",rezconvertDolar) + " $" ;
                bunding2.textViewRezultCalcule.setText(rezultDolar);
            }else if (onclicEvro == true) {
                Double rezconvertEvro = Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / 99.85;
                String rezultEvro = String.format("%.2f",rezconvertEvro) + " €" ;
                bunding2.textViewRezultCalcule.setText(rezultEvro);
            }else if (onclicYany == true) {
                Double rezconvertYany = Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / 13.03;
                String rezultYany = String.format("%.2f",rezconvertYany) + " ¥";
                bunding2.textViewRezultCalcule.setText(rezultYany);
            }

        }
    });

}
    public void showInfo(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }

    private void init(){
        runnable = new Runnable() {
            @Override
            public void run() {
                getweb();
            }
        };
        secThread = new Thread(runnable);
        secThread.start();
    }
    private void getweb(){
        try {
            doc = Jsoup.connect("https://cbr.ru/currency_base/daily/").get();
            Elements tables = doc.getElementsByTag("tbody");
            Element select_table = tables.get(0);
            Elements elements_from_table = select_table.children();
            Log.d("title","Зоголовок : "+doc.title());
            Log.d("text",doc.text());
            Log.d("size",String.valueOf(tables.get(0).text()));
            Log.d("one_elm",String.valueOf(elements_from_table.get(1).text()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
