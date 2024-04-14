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

    private String evro_curse,dolar_curse,yuan_curse;
    private float curse_evor,curse_dolar,curse_yan;

    private String curse_dolar_format,curse_evor_format,curse_yan_format;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding2 = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(bunding2.getRoot());


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
                Double rezconvertDolar = (double) (Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / curse_dolar);
                String rezultDolar = String.format("%.2f",rezconvertDolar) + " $" ;
                bunding2.textViewRezultCalcule.setText(rezultDolar);
            }else if (onclicEvro == true) {
                Double rezconvertEvro = (double) (Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / curse_evor);
                String rezultEvro = String.format("%.2f",rezconvertEvro) + " €" ;
                bunding2.textViewRezultCalcule.setText(rezultEvro);
            }else if (onclicYany == true) {
                Double rezconvertYany = (double) (Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / curse_yan);
                String rezultYany = String.format("%.2f",rezconvertYany) + " ¥";
                bunding2.textViewRezultCalcule.setText(rezultYany);
            }

        }
    });
bunding2.imageButtonActualValute.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        init();
            bunding2.textViewDolar.setText(String.valueOf(curse_dolar_format));
            bunding2.textViewEvro.setText(String.valueOf(curse_evor_format));
            bunding2.textViewYany.setText(String.valueOf(curse_yan_format));
            showInfo("Курс влюты обнавлён !");

            if (dolar_curse != null & evro_curse != null & yuan_curse != null){

                dolar_curse = dolar_curse.replace(",",".");
                evro_curse = evro_curse.replace(",",".");
                yuan_curse = yuan_curse.replace(",",".");

                curse_dolar = Float.parseFloat(dolar_curse);
                curse_evor = Float.parseFloat(evro_curse);
                curse_yan = Float.parseFloat(yuan_curse);

                curse_dolar_format = String.format("%.2f",curse_dolar);
                curse_evor_format = String.format("%.2f",curse_evor);
                curse_yan_format = String.format("%.2f",curse_yan);

                curse_dolar = Float.parseFloat(dolar_curse);
                curse_evor = Float.parseFloat(evro_curse);
                curse_yan = Float.parseFloat(yuan_curse);
            }
//            if (doc != null){
//                curse_dolar = Float.parseFloat(dolar_curse);
//                curse_evor = Float.parseFloat(evro_curse);
//                curse_yan = Float.parseFloat(yuan_curse);
//            }
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

            //Долар
            Element dolar = elements_from_table.get(14);
            Elements dolar_children = dolar.children();

            //Евро
            Element evro = elements_from_table.get(15);
            Elements evro_cheldren = evro.children();

            //Юань
            Element yan = elements_from_table.get(23);
            Elements yan_cheldren = yan.children();

            dolar_curse = String.valueOf(dolar_children.get(4).text());
            evro_curse = String.valueOf(evro_cheldren.get(4).text());
            yuan_curse = String.valueOf(yan_cheldren.get(4).text());


//            Log.d("title","Зоголовок : "+doc.title());
//            Log.d("text",doc.text());
//            Log.d("size",String.valueOf(tables.get(0).text()));
//            Log.d("one_elm",String.valueOf(elements_from_table.get(14).text()));

//            Log.d("dolar[0]-nummber",String.valueOf(dolar_children.get(0).text()));
//            Log.d("dolar[1]-neam-valute",String.valueOf(dolar_children.get(1).text()));
//            Log.d("dolar[2]-count_item",String.valueOf(dolar_children.get(2).text()));
//            Log.d("dolar[3]-ru_transteut",String.valueOf(dolar_children.get(3).text()));
//            Log.d("dolar[4]-valute_curse",String.valueOf(dolar_children.get(4).text()));

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
