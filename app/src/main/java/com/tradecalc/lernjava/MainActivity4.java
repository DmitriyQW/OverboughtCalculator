package com.tradecalc.lernjava;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;


import com.tradecalc.lernjava.databinding.ActivityMain4Binding;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import java.io.IOException;
import java.util.ArrayList;

public class MainActivity4 extends AppCompatActivity {
    private ActivityMain4Binding bunding4;
    private Document doc = null;
    private MyAsyncTask2 task2;
    private boolean requestBR = false;
    private String urlImage_0 = "https://i.stack.imgur.com/ejAEh.png",urlImage_1,urlImage_2,mainName_0,mainName_1,mainName_2,firstName_0,firstName_1,firstName_2,cost_0,cost_1,cost_2;

    private ArrayList<Stock> stocks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding4 = ActivityMain4Binding.inflate(getLayoutInflater());
        setContentView(bunding4.getRoot());

        bunding4.buttonUpdatingCourse.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isOnline()){
                    if (requestBR == false) {
                        task2 = new MyAsyncTask2();
                        requestBR = true;
                        task2.execute();
                    } else {
                        showInfo("У вас уже самые актуальные данные !");
                    }
                }else {
                    showInfo("У вас нет интернета !");
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
            Elements table = doc.getElementsByClass("Panel__sc-1g68tnu-1 jgYkzv");


            //Первое место в топе элем 0 элементом
            Element select_element_0 = table.get(0);

              //Первое место имена главное и второе
              Elements mainNeam_0_teamp = select_element_0.getElementsByClass("TextResponsive__sc-hroye5-0 jKnrKA");
              Elements firstNeam_0_teamp = select_element_0.getElementsByClass("TextResponsive__sc-hroye5-0 jxebas");
              mainName_0 = mainNeam_0_teamp.get(0).text();
              firstName_0 = firstNeam_0_teamp.get(0).text();


            //Цена на первое место
            Elements costs_0 = select_element_0.getElementsByClass("TextResponsive__sc-hroye5-0 keyTWo");
            cost_0 = costs_0.get(0).text();
            Log.d("Цена акции под первым местом", cost_0);


            Log.d("test",table.html());


            //Второе место в топе элем 1 элементом
            Element select_element_1 = table.get(1);

            //Цена на акцию под вторым местом
            Elements costs_1 = select_element_1.getElementsByClass("TextResponsive__sc-hroye5-0 keyTWo");
            cost_1 = costs_1.get(0).text();
            Log.d("Цена Башнефть", cost_1);

            //Второе место имена главное и второе
            Elements mainNeam_1_teamp = select_element_1.getElementsByClass("TextResponsive__sc-hroye5-0 jKnrKA");
            Elements firstNeam_1_teamp = select_element_1.getElementsByClass("TextResponsive__sc-hroye5-0 jxebas");

            mainName_1 = mainNeam_1_teamp.get(0).text();
            firstName_1 = firstNeam_1_teamp.get(0).text();

            Log.d("test1",mainName_1);
            Log.d("test2",firstName_1);
            //Третье место в топе элем 2 элементом
            Element select_element_2 = table.get(2);

            ///Цена на акцию под третьим местом
            Elements costs_2 = select_element_2.getElementsByClass("TextResponsive__sc-hroye5-0 keyTWo");
            cost_2 = costs_2.get(0).text();
            Log.d("Цена Аэрофлот", cost_2);

            //Третье место имена главное и второе
            Elements mainNeam_2_teamp = select_element_2.getElementsByClass("TextResponsive__sc-hroye5-0 jKnrKA");
            Elements firstNeam_2_teamp = select_element_2.getElementsByClass("TextResponsive__sc-hroye5-0 jxebas");
            mainName_2 = mainNeam_2_teamp.get(0).text();
            firstName_2 = firstNeam_2_teamp.get(0).text();

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

            //Первое место
              bunding4.textViewTopMainName.setText(mainName_0);
              bunding4.textViewTopFirstName.setText(firstName_0);
              bunding4.textViewCostTop.setText(cost_0);

            //Второе место
            bunding4.textViewSecondMainName.setText(mainName_1);
            bunding4.textViewSecondFirstName.setText(firstName_1);
            bunding4.textViewCostSecond.setText(cost_1);

            //Третье место
            bunding4.textViewThirdMainName.setText(mainName_2);
            bunding4.textViewThirdFirstName.setText(firstName_2);
            bunding4.textViewCostThird.setText(cost_2);
        }
    }

    public void showInfo(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
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
}