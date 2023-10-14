package com.example.classroom;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class admins extends AppCompatActivity {
Button student,admin,maintenance;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminstu);
        student=(Button)findViewById(R.id.button2);
        student.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(admins.this,stulogin.class));
                finish();

            }

        });
        admin=(Button)findViewById(R.id.button1);
        admin.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(admins.this,adminlogin.class));
                finish();
            }

        });
        maintenance=(Button)findViewById(R.id.button4);
        maintenance.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                startActivity(new Intent(admins.this,maintenancelogin.class));
                finish();
            }

        });

    }
}