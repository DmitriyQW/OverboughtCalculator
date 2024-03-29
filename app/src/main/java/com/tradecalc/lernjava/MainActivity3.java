package com.tradecalc.lernjava;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.tradecalc.lernjava.databinding.ActivityMain3Binding;

public class MainActivity3 extends AppCompatActivity {
    private ActivityMain3Binding bunding3;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        super.onCreate(savedInstanceState);
        bunding3 = ActivityMain3Binding.inflate(getLayoutInflater());
        setContentView(bunding3.getRoot());
    }
}