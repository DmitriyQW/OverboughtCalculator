package com.tradecalc.lernjava;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.tradecalc.lernjava.databinding.ActivityMain4Binding;
import com.tradecalc.lernjava.databinding.ActivityMainBinding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity4 extends AppCompatActivity {
    private ActivityMain4Binding bunding4;
    private Document doc = null;
    private MyAsyncTask2 task2;
    private boolean requestBR = false;
    private String mtc_cost, bash_neft_cost, aeroflot_cost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding4 = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(bunding4.getRoot());

        bunding4.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (requestBR == false) {
                    task2 = new MyAsyncTask2();
                    requestBR = true;
                    task2.execute();
                } else {
                    showInfo("У вас уже самые актуальные данные !");
                }
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
        finish();
    }

    private void getweb() {
        try {
            doc = Jsoup.connect("https://www.banki.ru/investment/shares/russian_shares/popular/").get();

            //Берём все строчки акций по классу
            Elements table = doc.getElementsByClass("Panel__sc-1g68tnu-1 hDJOiX");

            //Мтс под 0 элементом
            Element select_element_mtc = table.get(0);

            //Цена на акцию МТС
            Elements cost_mtc = select_element_mtc.getElementsByClass("TextResponsive__sc-hroye5-0 keyTWo");
            mtc_cost = cost_mtc.get(0).text();
            Log.d("Цена MTC", cost_mtc.get(0).text());


            //Башнефть под 1 элементом
            Element select_element_bash_neft = table.get(1);

            //Цена на акцию Башнефть
            Elements cost_bash_neft = select_element_bash_neft.getElementsByClass("TextResponsive__sc-hroye5-0 keyTWo");
            bash_neft_cost = cost_bash_neft.get(0).text();
            Log.d("Цена Башнефть", cost_bash_neft.get(0).text());

            //Аэрофлот под 2 элементом
            Element select_element_aeroflot = table.get(2);

            //Цена на акцию Аэрофлот
            Elements cost_aeroflot = select_element_aeroflot.getElementsByClass("TextResponsive__sc-hroye5-0 keyTWo");
            aeroflot_cost = cost_aeroflot.get(0).text();
            Log.d("Цена Аэрофлот", cost_aeroflot.get(0).text());


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    class MyAsyncTask2 extends AsyncTask<Void, Void, Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getweb();
            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
// Изменяем графический интерфейс
            bunding4.textViewCostMtc.setText(mtc_cost);
            bunding4.textViewCostBashNeft.setText(bash_neft_cost);
            bunding4.textViewCostAeroflot.setText(aeroflot_cost);
        }
    }

    public void showInfo(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
}