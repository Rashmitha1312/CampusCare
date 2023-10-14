package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class classsendrequest extends AppCompatActivity {
    TextView dataTextView;
    TextView sendemail;
    Button sendbtn,backbtn;
    DatabaseReference databaseReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_classsendrequest);

        sendemail = findViewById(R.id.textView6);
        dataTextView = findViewById(R.id.textView5);
        sendbtn = findViewById(R.id.button14);
        Intent intent = getIntent();

        String name = intent.getStringExtra("name");
        String section = intent.getStringExtra("section");
        String email = intent.getStringExtra("email");

        List<String> selectedIssues = getIntent().getStringArrayListExtra("selectedIssues");
        String classroomNumber = intent.getStringExtra("classroom number");
        String other = intent.getStringExtra("other issues");

        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Name: ").append(name).append("\n");
        stringBuilder.append("Section: ").append(section).append("\n");
        stringBuilder.append("Email: ").append(email).append("\n");
        stringBuilder.append("Issues: ").append("\n");
        stringBuilder.append("other issues: ").append(other).append("\n");
        String displayText = "Name: " + name + "\n" +
                "Section: " + section + "\n" +
                "Email: " + email + "\n" +
                "Issues: " + selectedIssues.toString() + "\n" +
                "other issues: " + other + "\n" +
                "Classroom Number: " + classroomNumber;


        dataTextView.setText(displayText);

        sendbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                FirebaseDatabase database = FirebaseDatabase.getInstance();
                DatabaseReference depIssuesRef = database.getReference("depissues");

                // Create a new child node under "depissues"
//                DatabaseReference newIssueRef = depIssuesRef.push();
                DatabaseReference userRef = depIssuesRef.child(name);
                // Create a map to hold the data
                Map<String, Object> issueData = new HashMap<>();
                issueData.put("name", name);
                issueData.put("section", section);
                issueData.put("email", email);
                issueData.put("selectedIssues", selectedIssues);
                issueData.put("other issues", other);
                issueData.put("classroomNumber", classroomNumber);

                // Store the data under the new child node
                userRef.setValue(issueData)
                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    // Data successfully stored in Firebase
                                    Toast.makeText(classsendrequest.this,"Request sent",Toast.LENGTH_SHORT).show();
                                    // Show a success message or perform any other desired action
                                } else {
                                    // Failed to store data in Firebase
                                    Toast.makeText(classsendrequest.this,"Failed",Toast.LENGTH_SHORT).show();
                                    // Handle the error appropriately (e.g., show an error message)
                                }
                            }
                        });
            }
        });


        sendemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String emailTemplate = getIntent().getStringExtra("template");
                Intent intent = new Intent(classsendrequest.this, emailtemplate.class);
                intent.putExtra("template", emailTemplate);
                startActivity(intent);

            }
        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(classsendrequest.this,classroompage.class);
                startActivity(intent);
            }
        });
    }
}