package com.example.sharedpreferences;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView rotate;
    Button b_rotate;
    public static final String MyPREFERENCES = "MyPrefs" ;
    public static final String ROTATE = "rotate";
    SharedPreferences sharedpreferences;
    SharedPreferences.Editor editor;
    int num_rotate=0,num_start=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        b_rotate=(Button)findViewById(R.id.b_rotate);
        rotate=(TextView) findViewById(R.id.t_rotate);
        sharedpreferences = getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        editor=sharedpreferences.edit();
        num_rotate =sharedpreferences.getInt(ROTATE,0);

        rotate.setText(String.valueOf(num_rotate));


        b_rotate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               editor.putInt(ROTATE,0);
               editor.apply();
                rotate.setText("0");

            }
        });
    }
    

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
       super.onConfigurationChanged(newConfig);
        num_rotate =sharedpreferences.getInt(ROTATE,0);
        num_rotate++;
        rotate.setText(String.valueOf(num_rotate));
        editor.putInt(ROTATE,num_rotate);
        editor.apply();

    }
}