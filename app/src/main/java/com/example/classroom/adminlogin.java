package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class adminlogin extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroom-313db-default-rtdb.firebaseio.com/");
    TextView linkpage;
    EditText passwd,facid;
    Button login,backbtn;
    FirebaseAuth mAuth;

    ProgressBar progressBar;
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser != null){
            Intent intent=new Intent(getApplicationContext(),mainpage2.class);
            startActivity(intent);
            finish();
        }
    }
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adminlogin);
        mAuth=FirebaseAuth.getInstance();
        login=(Button)findViewById(R.id.login);
        passwd=findViewById(R.id.pass);
        facid=findViewById(R.id.usn1);
        progressBar=findViewById(R.id.progressBar2);

        login.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View view) {
                                         progressBar.setVisibility(View.VISIBLE);
                                         String password,faculty;
                                         faculty=String.valueOf(facid.getText());
                                         password=String.valueOf(passwd.getText());

                                         if(TextUtils.isEmpty(faculty)){
                                             progressBar.setVisibility(View.GONE);
                                             Toast.makeText(adminlogin.this,"Please enter your id!",Toast.LENGTH_SHORT).show();
                                         }
                                         if(TextUtils.isEmpty(password)){
                                             progressBar.setVisibility(View.GONE);
                                             Toast.makeText(adminlogin.this,"Please enter your password!",Toast.LENGTH_SHORT).show();
                                         }
                                         else{
                                             databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                                                 @Override
                                                 public void onDataChange(@NonNull DataSnapshot snapshot) {
                                                     if(snapshot.hasChild(faculty)){
                                                         String getPassword=snapshot.child(faculty).child("password").getValue(String.class);
                                                         if(getPassword.equals(password)){
                                                             Toast.makeText(adminlogin.this,"Sucessfully Logged In!",Toast.LENGTH_SHORT).show();
//                                                             progressBar.setVisibility(View.GONE);


                                                             Intent intent=new Intent(adminlogin.this,mainpage2.class);
                                                             intent.putExtra("faculty", faculty);
                                                             startActivity(intent);
                                                         }
                                                         else{progressBar.setVisibility(View.GONE);
                                                             Toast.makeText(adminlogin.this,"Please enter the correct password!",Toast.LENGTH_SHORT).show();
                                                         }
                                                     }
                                                     else{progressBar.setVisibility(View.GONE);
                                                         Toast.makeText(adminlogin.this,"Please enter the correct Faculty id and password!",Toast.LENGTH_SHORT).show();
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
                Intent intent = new Intent(adminlogin.this,signuppage.class);
                startActivity(intent);
            }
        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(adminlogin.this,admins.class);
                startActivity(intent);
            }
        });
    }
}
