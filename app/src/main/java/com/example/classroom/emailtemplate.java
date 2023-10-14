package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.classroom.databinding.ActivityMainBinding;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class emailtemplate extends AppCompatActivity {
    DatabaseReference databaseReference= FirebaseDatabase.getInstance().getReferenceFromUrl("https://classroom-313db-default-rtdb.firebaseio.com/");
    private TextView templateTextView;
   private Button nextbtn;
    FirebaseAuth mAuth;
    EditText recievermail,sub;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_emailtemplate);
        nextbtn=findViewById(R.id.button12);
            recievermail=findViewById(R.id.reciever1);
             sub=findViewById(R.id.reciever2);
        templateTextView = findViewById(R.id.text);
        mAuth=FirebaseAuth.getInstance();

        // Retrieve the template from the intent
        String template = getIntent().getStringExtra("template");

        // Set the template to the TextView
        templateTextView.setText(template);
        nextbtn.setOnClickListener(new View.OnClickListener(){
             public void onClick(View view){

                 String email=recievermail.getText().toString();
                 String subject=sub.getText().toString();
                 String message=template;
                 String[] adresses=email.split(",");


                 Intent intent=new Intent(Intent.ACTION_SENDTO);
                 intent.setData(Uri.parse("mailto:"));
                 intent.putExtra(Intent.EXTRA_EMAIL,adresses);
                 intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                 intent.putExtra(Intent.EXTRA_TEXT,message);
                 String name = intent.getStringExtra("name");
                 String email1 = intent.getStringExtra("email");
                 String classroom = intent.getStringExtra("classroom");
                 String section = intent.getStringExtra("section");

                 // Retrieve the selected issues
                 ArrayList<String> selectedIssues = intent.getStringArrayListExtra("selectedIssues");
                 if(intent.resolveActivity(getPackageManager())!=null){
                     startActivity(intent);
                     storeDetails(name, email1, classroom, section, selectedIssues);
                 }
                 else{
                     Toast.makeText(emailtemplate.this,"No app is installed",Toast.LENGTH_SHORT).show();
                 }
             }
        });



        }
    private void storeDetails(String name, String email1, String classroom, String section, ArrayList<String> selectedIssues) {
        databaseReference.child("issues").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                    databaseReference.child("issues").child(classroom).child("name").setValue(name);
                    databaseReference.child("issues").child(classroom).child("email").setValue(email1);
//                                databaseReference.child("maintenance").child(userid).child("department").setValue(depname);
                    databaseReference.child("issues").child(classroom).child("section").setValue(section);
                    databaseReference.child("issues").child(classroom).child("email").setValue(classroom);
                    databaseReference.child("issues").child(classroom).child("issues").setValue(selectedIssues);

                    Toast.makeText(emailtemplate.this, "Details sent successfully!",
                            Toast.LENGTH_SHORT).show();

//                    Intent intent = new Intent(emailtemplate.this, emailtemplate.class);
//                    intent.putExtra("issues", selectedIssues);
//                    startActivity(intent);
//                    finish();
                }


            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}