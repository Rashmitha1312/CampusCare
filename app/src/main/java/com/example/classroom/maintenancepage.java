package com.example.classroom;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class maintenancepage extends AppCompatActivity {
    private RecyclerView recyclerView;
    private MyAdapter adapter;
    private List<Map<String, Object>> dataList;
    private Button deleteButton,sendBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maintenancepage);
        // Initialize RecyclerView and dataList
        recyclerView = findViewById(R.id.recycle);
        dataList = new ArrayList<>();

        // Set up adapter and layout manager
        adapter = new MyAdapter(this, dataList);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Set up delete button click listener
        deleteButton = findViewById(R.id.delete);
        sendBtn = findViewById(R.id.send);
        deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSelectedItems();
            }
        });

        // Retrieve data from Firebase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        database.getReference("depissues").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Clear existing data
                dataList.clear();

                // Iterate through the data and add it to the list
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Map<String, Object> data = (Map<String, Object>) snapshot.getValue();
                    dataList.add(data);
                }

                // Notify the adapter that the data set has changed
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle any errors that occur during the data retrieval
            }
        });
    }

    private void deleteSelectedItems() {
        // Iterate through the list and delete selected items
        List<Map<String, Object>> selectedItems = adapter.getSelectedItems();
        for (Map<String, Object> item : selectedItems) {
            String userEmail = item.get("email").toString();
            String uname = item.get("name").toString();
            StringBuilder mailTemplate = new StringBuilder();
//            sendEmailNotification(userEmail);
            String subject = "Maintenance Request Update";
           mailTemplate.append("\"Dear"+uname+",\n" +
                    "\n" +
                    "Thank you for bringing the maintenance issue to our attention. We have addressed the problem and taken the necessary steps to resolve it. If you have any further concerns or require additional assistance, please don't hesitate to reach out to us.\n" +
                    "\n" +
                    "Best regards,\n" +
                    "Maintenance Department\n" +
                    "Sahyadri college of Engineering and Management\"");
            String template = mailTemplate.toString();
            generateMailTemplate(userEmail,uname,template);
            // Delete the item from Firebase (replace "your_data_path_in_firebase" with the actual path)
            FirebaseDatabase.getInstance().getReference("depissues").child(item.get("name").toString()).removeValue();
//            sendEmailNotification(userEmail);
        }

        // Clear the selected items list
        adapter.clearSelectedItems();
    }
    private void generateMailTemplate(String userEmail,String UserName,String template){
        String email=userEmail;
        String name=UserName;
        String template1=template;
        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email1=email;
                String subject="Maintenance request update";
                String message=template1;
                String[] adresses=email1.split(",");


                Intent intent=new Intent(Intent.ACTION_SENDTO);
                intent.setData(Uri.parse("mailto:"));
                intent.putExtra(Intent.EXTRA_EMAIL,adresses);
                intent.putExtra(Intent.EXTRA_SUBJECT,subject);
                intent.putExtra(Intent.EXTRA_TEXT,message);
                if(intent.resolveActivity(getPackageManager())!=null) {
                    startActivity(intent);
                }
                else{
                    Toast.makeText(maintenancepage.this,"No app is installed",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }



}