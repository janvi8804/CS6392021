package com.example.chaturjanviramaniv3;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    Button button_convert;
    EditText input_data;
    TextView output_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        init();
        onClick();

    }

    private void init() {
        button_convert=(Button) findViewById(R.id.convert);
        input_data=(EditText) findViewById(R.id.input);
        output_data=(TextView) findViewById(R.id.output);
    }

    private void onClick() {
        button_convert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertData();
            }
        });

    }

    private void convertData() {
        String input_string="";
        input_string=input_data.getText().toString();
        if(input_string==""||input_string.equals(""))
        {
            Toast.makeText(MainActivity.this,"Field can`t be empty",Toast.LENGTH_SHORT).show();
        }
        else
        {
            float data_int= Float.parseFloat(input_string);
            float result = (float) (data_int*113);
            Log.d("fsdf  ","gdsv "+result);
            output_data.setText(Float.toString(result) +" dollar");
            input_data.setText("");
        }
    }
}