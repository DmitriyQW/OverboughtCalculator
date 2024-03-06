package com.profitwizard.overboughtcalculator;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.profitwizard.overboughtcalculator.databinding.ActivityMain2Binding;


public class MainActivity2 extends AppCompatActivity {

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
                Double rezconvertDolar = Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / 91.33;
                String rezultDolar = String.format("%.2f",rezconvertDolar) + " $" ;
                bunding2.textViewRezultCalcule.setText(rezultDolar);
            }else if (onclicEvro == true) {
                Double rezconvertEvro = Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / 98.72;
                String rezultEvro = String.format("%.2f",rezconvertEvro) + " €" ;
                bunding2.textViewRezultCalcule.setText(rezultEvro);
            }else if (onclicYany == true) {
                Double rezconvertYany = Float.parseFloat(bunding2.editTextTextInConvert.getText().toString()) / 12.63;
                String rezultYany = String.format("%.2f",rezconvertYany) + " ¥";
                bunding2.textViewRezultCalcule.setText(rezultYany);
            }

        }
    });

}
    public void showInfo(String text){
        Toast.makeText(this,text,Toast.LENGTH_SHORT).show();
    }
}