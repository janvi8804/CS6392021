package com.example.hellotoastapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toast toast=Toast.makeText(getApplicationContext(),"Hello Janvi Ramani",Toast.LENGTH_SHORT);
        toast.setMargin(50,50);
        toast.show();
    }
}