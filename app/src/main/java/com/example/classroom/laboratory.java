package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;
import java.util.List;

public class laboratory extends AppCompatActivity {
    private CheckBox checkBox1, checkBox2, checkBox3, checkBox4, checkBox5, checkBox6, checkBox7, checkBox8, checkBox9;
    private Button nextbtn,backbtn;
    private boolean isAdmin;
    EditText nameEditText, emailEditText, messageEditText, roomno, section;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laboratory);
        mAuth = FirebaseAuth.getInstance();
        checkBox1 = findViewById(R.id.checkbox1);
        checkBox2 = findViewById(R.id.checkbox2);
        checkBox3 = findViewById(R.id.checkbox3);
        checkBox4 = findViewById(R.id.checkbox4);
        checkBox5 = findViewById(R.id.checkbox5);
        checkBox6 = findViewById(R.id.checkbox6);
        checkBox7 = findViewById(R.id.checkbox7);
        checkBox8 = findViewById(R.id.checkbox8);
        checkBox9 = findViewById(R.id.checkbox9);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        messageEditText = findViewById(R.id.msg);
        roomno = findViewById(R.id.roomno);
        section = findViewById(R.id.section);
        nextbtn = findViewById(R.id.button);

        nextbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                generateMailTemplate();

            }
        });
        backbtn = findViewById(R.id.backbtn);
        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the signup button click event
                Intent intent = new Intent(laboratory.this,mainpage2.class);
                startActivity(intent);
            }
        });
    }

    private void generateMailTemplate() {

        StringBuilder mailTemplate = new StringBuilder();
        String role;
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            // Check if user has the "faculty" custom claim
            String userRole = user.getMetadata().getCreationTimestamp() == user.getMetadata().getLastSignInTimestamp()
                    ? "admin"
                    : "student";
            if (userRole.equals("admin")) {
                // User is an admin (faculty)
                isAdmin = true;
                // Perform admin/faculty-specific operations
            } else {
                // User is not an admin (student)
                isAdmin = false;
                // Perform student-specific operations
            }
        }
        if (isAdmin == true) {
            role = "student";
        } else {
            role = "faculty";
        }
        mailTemplate.append("Dear Sir/Madam,\n");
        mailTemplate.append("I hope this email finds you well. As a " + role + " at Sahyadri college of engineering and management, I am writing to bring your attention to an issue that requires maintenance assistance.");
        mailTemplate.append("These are the issues we are facing\n");
//        ArrayList<String> selectedIssues = new ArrayList<>();
        List<String> selectedIssues = new ArrayList<>();
        if (checkBox1.isChecked()) {
            selectedIssues.add("The projector does not turn on or display properly.");
            mailTemplate.append("The projector does not turn on or display properly.\n");
        }

        if (checkBox2.isChecked()) {
            selectedIssues.add("Inadequate network infrastructure and slow internet connectivity.");
            mailTemplate.append("Inadequate network infrastructure and slow internet connectivity.\n");
        }

        if (checkBox3.isChecked()) {
            selectedIssues.add("Lack of updated software and operating systems.");
            mailTemplate.append("Lack of updated software and operating systems.\n");
        }

        if (checkBox4.isChecked()) {
            selectedIssues.add("The fan is malfunctioning, causing inadequate air circulation or noise.");
            mailTemplate.append(" The fan is malfunctioning, causing inadequate air circulation or noise.\n");
        }

        if (checkBox5.isChecked()) {
            selectedIssues.add("Inadequate space for students to work comfortably.");
            mailTemplate.append("Inadequate space for students to work comfortably.\n");
        }

        if (checkBox6.isChecked()) {
            selectedIssues.add("Insufficient availability of experimental setups and apparatus.");
            mailTemplate.append("Insufficient availability of experimental setups and apparatus.\n");
        }

        if (checkBox7.isChecked()) {
            selectedIssues.add("Inadequate ventilation leading to chemical fumes and odors.");
            mailTemplate.append("Inadequate ventilation leading to chemical fumes and odors.\n");
        }

        if (checkBox8.isChecked()) {
            selectedIssues.add("Non-functional or outdated analytical instruments.");
            mailTemplate.append("Non-functional or outdated analytical instruments.\n");
        }

        if (checkBox9.isChecked()) {
            selectedIssues.add("Inadequate cooling systems leading to overheating of computers.");
            mailTemplate.append("Inadequate cooling systems leading to overheating of computers.\n");
        }
        String message = messageEditText.getText().toString();
        mailTemplate.append("Other issues: ").append(message).append("\n");
        String name = nameEditText.getText().toString();
        String email = emailEditText.getText().toString();
        String sec = section.getText().toString();
        String room = roomno.getText().toString();

        mailTemplate.append("This issue is causing inconvenience and may potentially affect the safety and functionality of the campus. Therefore, I kindly request your prompt attention and resolution of the problem.\n" +
                "\n" +
                "I understand that the maintenance department is responsible for handling various requests, but I urge you to consider the importance and urgency of this matter. Timely action will contribute to the well-being and smooth functioning of our campus.\n" +
                "\n" +
                "Please keep me updated on the progress of the repairs and provide an estimated timeline for resolution. If there are any additional details or information required from me, please let me know, and I will be glad to assist.\n" +
                "\n" +
                "Thank you for your attention to this matter.\n\n" +
                "Sincerely,\n\n");
        mailTemplate.append("\n");
        mailTemplate.append("Name: ").append(name).append("\n");
        mailTemplate.append("Email: ").append(email).append("\n");
        mailTemplate.append("Section: ").append(sec).append("\n");
        mailTemplate.append("Room number: ").append(room).append("\n");

        String template = mailTemplate.toString();
        Intent intent = new Intent(laboratory.this, depsendreq.class);
        intent.putExtra("template", template);
        intent.putExtra("name", name);
        intent.putExtra("email", email);
        intent.putExtra("section", sec);
        intent.putExtra("classroom number", room);
//        intent.putStringArrayListExtra("selectedIssues", selectedIssues);
        intent.putStringArrayListExtra("selectedIssues", new ArrayList<>(selectedIssues));

        startActivity(intent);

    }
}