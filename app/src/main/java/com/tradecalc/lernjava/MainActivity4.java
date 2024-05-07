package com.tradecalc.lernjava;


import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.tradecalc.lernjava.databinding.ActivityMain4Binding;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class MainActivity4 extends AppCompatActivity {
    private ActivityMain4Binding bunding4;
    private Document doc = null;
    MyAsyncTask2 task2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding4 = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(bunding4.getRoot());

        bunding4.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                task2 = new MyAsyncTask2();
                task2.execute();
            }
        });
    }

    private void getweb(){
        try {
            doc = Jsoup.connect("https://www.banki.ru/investment/shares/russian_shares/popular/").get();

            Elements table = doc.getElementsByClass("Panel__sc-1g68tnu-1 hDJOiX");
            //Element select_element_table = table.get(0);
//            Elements elements_from_table = select_table.children();
          Log.d("elem-1",table.text());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    class MyAsyncTask2 extends AsyncTask<Void,Void,Void> {

        @Override
        protected Void doInBackground(Void... voids) {
            getweb();

            return null;
        }

        @Override
        protected void onPostExecute(Void unused) {
// Изменяем графический интерфейс

        }
    }
}
