package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class maintenancesignup extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroom-313db-default-rtdb.firebaseio.com/");
    TextView linkpage;
    EditText name,emailid,uid,department,mobno,passwd;

    Button signup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenancesignup);
        mAuth=FirebaseAuth.getInstance();

        signup=(Button) findViewById(R.id.signup);
        name=findViewById(R.id.uname);
        emailid=findViewById(R.id.email);
        uid=findViewById(R.id.usn);
        mobno=findViewById(R.id.phone);
        passwd=findViewById(R.id.password);
//        department=findViewById(R.id.dname);
        progressBar=findViewById(R.id.progressBar);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String nam, userid, depname, phone, email, pass;

                nam = String.valueOf(name.getText());
                userid = String.valueOf(uid.getText());
//                depname = String.valueOf(department.getText());
                phone = String.valueOf(mobno.getText());
                email = String.valueOf(emailid.getText());
                pass = String.valueOf(passwd.getText());
                if (nam.isEmpty() || userid.isEmpty()  || phone.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(maintenancesignup.this, "Please fill all the details!", Toast.LENGTH_SHORT).show();

                }
                else {
                    databaseReference.child("maintenance").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(userid)) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(maintenancesignup.this, "user id is already taken!Please enter the new userid!", Toast.LENGTH_SHORT).show();

                            } else {
                                databaseReference.child("maintenance").child(userid).child("name").setValue(nam);
                                databaseReference.child("maintenance").child(userid).child("userid").setValue(userid);
//                                databaseReference.child("maintenance").child(userid).child("department").setValue(depname);
                                databaseReference.child("maintenance").child(userid).child("mobile").setValue(phone);
                                databaseReference.child("maintenance").child(userid).child("email").setValue(email);
                                databaseReference.child("maintenance").child(userid).child("password").setValue(pass);

                                Toast.makeText(maintenancesignup.this, "Account got created successfully!",
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(maintenancesignup.this, maintenancepage.class);
                                intent.putExtra("userid", userid);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }
            }
        });
        linkpage = findViewById(R.id.already);
        linkpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(maintenancesignup.this, maintenancelogin.class);
                startActivity(intent);
            }
        });
    }

}