package com.tradecalc.lernjava;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;
import com.tradecalc.lernjava.databinding.ActivityMainBinding;

public class MainActivity extends AppCompatActivity {


    private ActivityMainBinding bunding;
    //Задаём переменые для наших обектов

    private EditText editText_cost_unit_goods,editText_cost_implementation;
    private Button button_rezult;
    private ImageButton imageButton_bt_valutes;
    private TextView textView_expenses,textView_income,textView_profit,textView_benefit_ratio;


    private int priceinztrati;
    private int priceincaunt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(bunding.getRoot());

        //Ложим в переменые наши обекты по id +


        textView_expenses = findViewById(R.id.textView_expenses);
        textView_income = findViewById(R.id.textView_income);
        textView_profit = findViewById(R.id.textView_profit);
        textView_benefit_ratio = findViewById(R.id.textView_benefit_ratio);

        editText_cost_unit_goods = findViewById(R.id.editText_cost_unit_goods);
        editText_cost_implementation = findViewById(R.id.editText_cost_implementation);

        button_rezult = findViewById(R.id.button_rezult);

        imageButton_bt_valutes = bunding.imageButtonBtValutes;



        imageButton_bt_valutes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MainActivity2.class);
                startActivity(intent);
            }
        });

        bunding.imageButtonBtHistori.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,MainActivity3.class);
                startActivity(intent);
            }
        });
        button_rezult.setOnClickListener(new View.OnClickListener() { //Кнопка чтобы узнать результат
            @Override
            public void onClick(View v) {
                if (editText_cost_unit_goods.getText().toString().isEmpty() || editText_cost_implementation.getText().toString().isEmpty()){
                    showInfo(getString(R.string.exeption_date_input));
                }else {
                    //Обнуление старых просчётов +

                    textView_expenses.setText(getString(R.string.zaplatil));
                    textView_income.setText(getString(R.string.plus_steam));
                    textView_profit.setText(getString(R.string.zist_pribil));
                    textView_benefit_ratio.setText(getString(R.string.kpd_vigodi));



                    //Вычесления +
                    float price_site = Float.parseFloat(editText_cost_unit_goods.getText().toString());
                    float steam_auto = Float.parseFloat(editText_cost_implementation.getText().toString());
                    float moni_in_steam;



                    //Комисия на продажу +
                    if (!bunding.editTextComssSale.getText().toString().isEmpty()){
                        if (Float.parseFloat(bunding.editTextComssSale.getText().toString()) != 0) {
                            moni_in_steam = steam_auto - ((steam_auto / 100) * Float.parseFloat(bunding.editTextComssSale.getText().toString()));
                        } else {
                            moni_in_steam = steam_auto;
                        }
                    }
                    else{
                        moni_in_steam = steam_auto;
                    }

                    float pribil = moni_in_steam - price_site;


                    if (!bunding.editTextTextCount.getText().toString().isEmpty()){
                        if (Float.parseFloat(bunding.editTextTextCount.getText().toString()) != 0) {
                            float count = Float.parseFloat(bunding.editTextTextCount.getText().toString());
                            priceinztrati = Math.round(price_site*count);
                            bunding.textViewProfitIetem.setText(getString(R.string.profit_one_piece)+" "+String.valueOf(Math.round(pribil))+" "+ getString(R.string.currency));
                            textView_expenses.setText(textView_expenses.getText().toString() + " " + String.valueOf(priceinztrati) +" "+ getString(R.string.currency));
                            textView_income.setText(textView_income.getText().toString() + " " + String.valueOf(Math.round(moni_in_steam*count)) +" "+ getString(R.string.currency));
                            priceincaunt = Math.round(pribil*count);
                            textView_profit.setText(textView_profit.getText().toString() + " " + String.valueOf(priceincaunt) +" "+ getString(R.string.currency));
                        }
                    }
                    else {
                        //Вывод пользователю +
                        priceinztrati = 0;
                        priceincaunt = 0;
                        bunding.textViewProfitIetem.setText(getString(R.string.profit_one_piece)+" "+String.valueOf(Math.round(pribil))+" "+ getString(R.string.currency));
                        textView_expenses.setText(textView_expenses.getText().toString() + " " + String.valueOf(Math.round(price_site)) +" "+ getString(R.string.currency));
                        textView_income.setText(textView_income.getText().toString() + " " + String.valueOf(Math.round(moni_in_steam)) +" "+ getString(R.string.currency));
                        textView_profit.setText(textView_profit.getText().toString() + " " + String.valueOf(Math.round(pribil)) +" "+ getString(R.string.currency));
                    }

                    //Проверка выгодно или нет и отоброжение пользователю в соответстие с выгодой +
                    if (Math.round(((pribil / price_site) * 100)) >= 10 || priceincaunt>=5000 || pribil>=1000) {
                        textView_benefit_ratio.setTextColor(getColor(R.color.vigodno_verdict));
                        textView_benefit_ratio.setText(textView_benefit_ratio.getText().toString() + " " + String.valueOf(Math.round((pribil / price_site) * 100)) + " %");
                    } else {
                        textView_benefit_ratio.setTextColor(getColor(R.color.no_vigodno_verdict));
                        textView_benefit_ratio.setText(textView_benefit_ratio.getText().toString() + " " + String.valueOf(Math.round((pribil / price_site) * 100)) + " %");
                    }
                }

            }
        });

    }

    //Функция для вовода крохотоного кружка +
    public void showInfo(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }


}