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

public class studentsignup extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroom-313db-default-rtdb.firebaseio.com/");
    TextView linkpage;
    EditText name,emailid,usn,department,mobno,passwd;

    Button signup;
    FirebaseAuth mAuth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_studentsignup);
        mAuth=FirebaseAuth.getInstance();

        signup=(Button) findViewById(R.id.signup);
        name=findViewById(R.id.uname);
        emailid=findViewById(R.id.email);
        usn=findViewById(R.id.usn);
        mobno=findViewById(R.id.phone);
        passwd=findViewById(R.id.password);
        department=findViewById(R.id.dname);
        progressBar=findViewById(R.id.progressBar);


        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                String nam, usnstu, depname, phone, email, pass;

                nam = String.valueOf(name.getText());
               usnstu = String.valueOf(usn.getText());
                depname = String.valueOf(department.getText());
                phone = String.valueOf(mobno.getText());
                email = String.valueOf(emailid.getText());
                pass = String.valueOf(passwd.getText());
                if (nam.isEmpty() || usnstu.isEmpty() || depname.isEmpty() || phone.isEmpty() || email.isEmpty() || pass.isEmpty()) {
                    progressBar.setVisibility(View.GONE);

                    Toast.makeText(studentsignup.this, "Please fill all the details!", Toast.LENGTH_SHORT).show();

                }
                else {
                    databaseReference.child("students").addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            if (snapshot.hasChild(usnstu)) {
                                progressBar.setVisibility(View.GONE);
                                Toast.makeText(studentsignup.this, "USN is already taken!Please enter the new USN!", Toast.LENGTH_SHORT).show();

                            } else {
                                databaseReference.child("students").child(usnstu).child("name").setValue(nam);
                                databaseReference.child("students").child(usnstu).child("usn").setValue(usnstu);
                                databaseReference.child("students").child(usnstu).child("department").setValue(depname);
                                databaseReference.child("students").child(usnstu).child("mobile").setValue(phone);
                                databaseReference.child("students").child(usnstu).child("email").setValue(email);
                                databaseReference.child("students").child(usnstu).child("password").setValue(pass);

                                Toast.makeText(studentsignup.this, "Account got created successfully!",
                                        Toast.LENGTH_SHORT).show();
                                progressBar.setVisibility(View.GONE);
                                Intent intent = new Intent(studentsignup.this, studentsignup.class);
                                intent.putExtra("student", usnstu);
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

    }
}