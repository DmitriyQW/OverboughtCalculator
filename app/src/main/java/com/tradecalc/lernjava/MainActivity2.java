package com.tradecalc.lernjava;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
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

    private final String key_curs_evro = "key_evro",key_curs_dolar = "key_dolar",key_curs_yan = "key_yan";
    private SharedPreferences pref;
    private Document doc = null;
    private  ActivityMain2Binding bunding2;
    private boolean onclicEvro,onclicDolar,onclicYany,requestCB = false;
    private String evro_curse,dolar_curse,yuan_curse;
    private float curse_evor,curse_dolar,curse_yan;

    private String curse_dolar_format,curse_evor_format,curse_yan_format;
    MyAsyncTask task;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding2 = ActivityMain2Binding.inflate(getLayoutInflater());
        setContentView(bunding2.getRoot());
        initpref();

        //Старый курс
        bunding2.textViewTextWarning.setText(getString(R.string.text_warning_start_lod));
        bunding2.textViewTextWarning.setVisibility(View.VISIBLE);

        //Устоновка старого курса
        curse_dolar = pref.getFloat(key_curs_dolar,0);
        curse_evor = pref.getFloat(key_curs_evro,0);
        curse_yan = pref.getFloat(key_curs_yan,0);

        bunding2.textViewDolar.setText(String.valueOf(pref.getFloat(key_curs_dolar,0)));
        bunding2.textViewEvro.setText(String.valueOf(pref.getFloat(key_curs_evro,0)));
        bunding2.textViewYany.setText(String.valueOf(pref.getFloat(key_curs_yan,0)));
        task = new MyAsyncTask();

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
            if (bunding2.editTextTextInConvert.getText().toString().isEmpty() != true){
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
            }else {
                showInfo("Введите колчество валюты для конвентации");
            }
        }
    });
    bunding2.imageButtonActualValute.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View v) {
        if (!isOnline()){
            //Если нет интернета
            bunding2.textViewTextWarning.setText(getString(R.string.text_warning));
            bunding2.textViewTextWarning.setVisibility(View.VISIBLE);

            //Устоновка старого курса
            curse_dolar = pref.getFloat(key_curs_dolar,0);
            curse_evor = pref.getFloat(key_curs_evro,0);
            curse_yan = pref.getFloat(key_curs_yan,0);

            bunding2.textViewDolar.setText(String.valueOf(pref.getFloat(key_curs_dolar,0)));
            bunding2.textViewEvro.setText(String.valueOf(pref.getFloat(key_curs_evro,0)));
            bunding2.textViewYany.setText(String.valueOf(pref.getFloat(key_curs_yan,0)));

        }else {
            if (requestCB==false){
                if (task.getStatus() != AsyncTask.Status.RUNNING){
                    bunding2.textViewTextWarning.setVisibility(View.GONE);
                    requestCB = true;
                    task.execute();
                }
                else {
                    showInfo("Уже выполняется");
                }
            }else {
                showInfo("У вас уже самый актуальный курс");
            }

        }
    }
});
}
    public void showInfo(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
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

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void initpref(){
        pref = getSharedPreferences("Valute",MODE_PRIVATE);
    }
    private boolean isOnline(){
        String cs = Context.CONNECTIVITY_SERVICE;
        ConnectivityManager cm = (ConnectivityManager) getSystemService(cs);
        if (cm.getActiveNetworkInfo() == null){
            return false;
        }else {
            return true;
        }
    }
    class MyAsyncTask extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {
            getweb();
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

            SharedPreferences.Editor edit = pref.edit();
            edit.putFloat(key_curs_dolar,curse_dolar);
            edit.putFloat(key_curs_evro,curse_evor);
            edit.putFloat(key_curs_yan,curse_yan);
            edit.apply();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
            bunding2.textViewDolar.setText(String.valueOf(curse_dolar_format));
            bunding2.textViewEvro.setText(String.valueOf(curse_evor_format));
            bunding2.textViewYany.setText(String.valueOf(curse_yan_format));
            showInfo("Курс влюты обнавлён !");
        }
    }
}

