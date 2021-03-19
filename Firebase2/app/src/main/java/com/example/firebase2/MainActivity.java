package com.example.firebase2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    FirebaseDatabase database;
    DatabaseReference myRef;
     RecyclerView mRecyclerView;
     ArrayList<Items> mList= new ArrayList<>();
     FirebaseAdapter adapter=new FirebaseAdapter();

     EditText firstname,lastname;
     Button save;
     @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
         mRecyclerView = findViewById(R.id.rv);
         mRecyclerView.setHasFixedSize(true);
         firstname=(EditText)findViewById(R.id.firstname);
         lastname=(EditText)findViewById(R.id.lastname);
         save=(Button)findViewById(R.id.save);
         database = FirebaseDatabase.getInstance();
         myRef = database.getReference("employee");

         save.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 Items items =new Items(firstname.getText().toString(),lastname.getText().toString());
                 myRef.push().setValue(items);

             }
         });

         myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.exists()) {


                    DataSnapshot contactSnapshot = snapshot;
                    Iterable<DataSnapshot> contactChildren = contactSnapshot.getChildren();
                   mList=new ArrayList<>();
Log.d("Rgdf 0","fsd "+contactChildren.iterator());
                    for (DataSnapshot contact : contactChildren) {
                        Items c = contact.getValue(Items.class);
                        mList.add(c);
                    }
                    adapter.setItem(mList);
                    mRecyclerView.setAdapter(adapter);
//                    String datax = snapshot.getValue().toString();
//                    if (datax != null) {
//
//                       Items items = snapshot.getValue(Items.class);
//
//                    }
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}