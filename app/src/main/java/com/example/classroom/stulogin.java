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

public class stulogin extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroom-313db-default-rtdb.firebaseio.com/");
    TextView linkpage;
    EditText passwd,usnstu;
    Button login,backbtn;
    FirebaseAuth mAuth;
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),mainpage2.class);
            startActivity(intent);
            finish();
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stulogin);
        mAuth=FirebaseAuth.getInstance();
        login=(Button)findViewById(R.id.login);
        passwd=findViewById(R.id.pass);
        usnstu=findViewById(R.id.usn1);


        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {

                                         String password,student;
                                         student=String.valueOf(usnstu.getText());
                                         password=String.valueOf(passwd.getText());

                                         if(TextUtils.isEmpty(student)){
                                             Toast.makeText(stulogin.this,"Please enter your USN!",Toast.LENGTH_SHORT).show();
                                         }
                                         if(TextUtils.isEmpty(password)){

                                             Toast.makeText(stulogin.this,"Please enter your password!",Toast.LENGTH_SHORT).show();
                                         }
                                         else{
                                             databaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                     if(snapshot.hasChild(student)){
                                                         String getPassword=snapshot.child(student).child("password").getValue(String.class);
                                                         if(getPassword.equals(password)){
                                                             Toast.makeText(stulogin.this,"Sucessfully Logged In!",Toast.LENGTH_SHORT).show();
//                                                             progressBar.setVisibility(View.GONE);


                                                             Intent intent=new Intent(stulogin.this,mainpage2.class);
                                                             intent.putExtra("students", student);
                                                             startActivity(intent);
                                                         }
                                                         else{
                                                             Toast.makeText(stulogin.this,"Please enter the correct password!",Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                     else{
                                                         Toast.makeText(stulogin.this,"Please enter the correct USN and password!",Toast.LENGTH_SHORT).show();
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
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(stulogin.this,admins.class);
                startActivity(intent);
            }
        });



    }
}