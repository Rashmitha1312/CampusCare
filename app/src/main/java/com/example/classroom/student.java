package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class student extends AppCompatActivity {
    DatabaseReference databaseReference;
    EditText editTextFacultyID;
   Button buttonVerify,backbtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        databaseReference = FirebaseDatabase.getInstance().getReference().child("users");

        editTextFacultyID = findViewById(R.id.fid);
        buttonVerify = findViewById(R.id.button13);
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(student.this,mainpage.class);
                startActivity(intent);
            }
        });
        buttonVerify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                verifyFacultyID();
            }
        });

    }
    private void verifyFacultyID() {
        String facultyID = editTextFacultyID.getText().toString().trim();

        if (TextUtils.isEmpty(facultyID)) {
            // Faculty ID is empty
            showToast("Please enter a Faculty ID");
            return;
        }

        databaseReference.orderByChild("faculty").equalTo(facultyID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {

                    showToast("Faculty ID is verified!");
                    Intent intent = new Intent(student.this,studentsignup.class);
                    startActivity(intent);
                }
                else
                {

                    Toast.makeText(student.this,"Please enter the correct faculty id",Toast.LENGTH_SHORT).show();

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

                showToast("Error verifying faculty ID: " + databaseError.getMessage());
            }
        });
    }

    private void showToast(String message) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }
}

