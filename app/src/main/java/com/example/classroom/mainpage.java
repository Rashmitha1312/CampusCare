package com.example.classroom;

import android.content.Intent;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class mainpage extends AppCompatActivity {
Button classroom,department,washroom,seminarhall,laboratory,restroom,backbtn;
    private BottomNavigationView bottomNavigationView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mainpage);
        classroom=(Button)findViewById(R.id.button4);
        department=(Button)findViewById(R.id.button9);
        washroom=(Button)findViewById(R.id.button5);
        seminarhall=(Button)findViewById(R.id.button6);
        laboratory=(Button)findViewById(R.id.button8);
        restroom=(Button)findViewById(R.id.button7);

        classroom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),classroompage.class);
                startActivity(intent);
                finish();
            }

        });
        department.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),department.class);
                startActivity(intent);
                finish();
            }

        });
        seminarhall.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),seminarhall.class);
                startActivity(intent);
                finish();
            }

        });
        washroom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),washroom.class);
                startActivity(intent);
                finish();
            }

        });
        laboratory.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),laboratory.class);
                startActivity(intent);
                finish();
            }

        });
        restroom.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent = new Intent(getApplicationContext(),restroom.class);
                startActivity(intent);
                finish();
            }

        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(mainpage.this,mainpage2.class);
                startActivity(intent);
            }
        });
    }

}