package com.example.scrollingapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        TextView textView = (TextView) findViewById(R.id.textView);
        String para = "This course surveys the specificities of the development of" +
                " native applications for Android devices." +
                " The software engineering " +
                "of application development including user-centered design," +
                " testing" +
                " and quality assurance will be emphasized. " +
                "Students will learn how" +
                " to design and develop applications for the Android platform." +
                " The " +
                "following topics will be covered:" +
                " intents, content providers, " +
                "notifications, rich and responsible layouts, " +
                "location based " +
                "facilities, recycleviews, databases," +
                " and network / web access. " +
                "Kotlin will be covered. " +
                "A project is integrated in the course.";
        textView.setText(para);
        textView.setMovementMethod(new ScrollingMovementMethod());
    }}
