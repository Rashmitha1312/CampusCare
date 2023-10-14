package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class maintenancelogin extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroom-313db-default-rtdb.firebaseio.com/");
    TextView linkpage;
    EditText passwd,user;
    Button login,backbtn;
    FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),maintenancepage.class);
            startActivity(intent);
            finish();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenancelogin);
        mAuth=FirebaseAuth.getInstance();
        login=(Button)findViewById(R.id.login);
        passwd=findViewById(R.id.pass);
        user=findViewById(R.id.usn1);


        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
//                                         progressBar.setVisibility(View.VISIBLE);
                                         String password,userid;
                                         userid=String.valueOf(user.getText());
                                         password=String.valueOf(passwd.getText());

                                         if(TextUtils.isEmpty(userid)){
//
                                             Toast.makeText(maintenancelogin.this,"Please enter your id!",Toast.LENGTH_SHORT).show();
                                         }
                                         if(TextUtils.isEmpty(password)){
//
                                             Toast.makeText(maintenancelogin.this,"Please enter your password!",Toast.LENGTH_SHORT).show();
                                         }
                                         else{
                                             databaseReference.child("maintenance").addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                     if(snapshot.hasChild(userid)){
                                                         String getPassword=snapshot.child(userid).child("password").getValue(String.class);
                                                         assert getPassword != null;
                                                         if(getPassword.equals(password)){
                                                             Toast.makeText(maintenancelogin.this,"Sucessfully Logged In!",Toast.LENGTH_SHORT).show();
//


                                                             Intent intent=new Intent(maintenancelogin.this,maintenancepage.class);
                                                             intent.putExtra("userid", userid);
                                                             startActivity(intent);
                                                         }
                                                         else{
//
                                                             Toast.makeText(maintenancelogin.this,"Please enter the correct password!",Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                     else{
//
                                                         Toast.makeText(maintenancelogin.this,"Please enter the correct user id and password!",Toast.LENGTH_SHORT).show();
                                                     }

                                                 }

                                                 @Override
                                                 public void onCancelled(@NonNull DatabaseError error) {

                                                 }
                                             });
                                         }


                                     }
                                 }
        );

        linkpage = findViewById(R.id.newuser);
        linkpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(maintenancelogin.this,maintenancesignup.class);
                startActivity(intent);
            }
        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(maintenancelogin.this,admins.class);
                startActivity(intent);
            }
        });
    }
}