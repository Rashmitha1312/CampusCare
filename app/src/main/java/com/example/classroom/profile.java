package com.example.classroom;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class profile extends AppCompatActivity {

    ImageView profile;
    TextView profileName;
    TextView profileEmail;
    private static final int REQUEST_IMAGE = 1;
    private Uri selectedImageUri;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        profileName = findViewById(R.id.profile_name);
        profileEmail = findViewById(R.id.profile_email);
        profile = findViewById(R.id.profile_image);

        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        if (user != null) {
//            String userId = user.getUid();
//            boolean isFaculty = isFacultyUser(userId);
//            String collectionPath = isFaculty ? "users" : "students";
//
//            db.collection(collectionPath)
//                    .document(userId)
//                    .get()
//                    .addOnCompleteListener(task -> {
//                        if (task.isSuccessful()) {
//                            DocumentSnapshot document = task.getResult();
//                            if (document != null && document.exists()) {
//                                String email = document.getString("email");
//                                String name = document.getString("name");
//
//                                // Log the retrieved email and name to check if they are correct
//                                Log.d("ProfileActivity", "Retrieved email: " + email);
//                                Log.d("ProfileActivity", "Retrieved name: " + name);
//
//                                profileEmail.setText(email);
//                                profileName.setText(name);
//                            } else {
//                                Log.d("ProfileActivity", "Document does not exist");
//                            }
//                        } else {
//                            // Handle any errors that occurred during the retrieval
//                            Log.d("ProfileActivity", "Error getting user document: " + task.getException());
//                        }
//                    });
//        } else {
//            Log.d("ProfileActivity", "User is not authenticated or signed in");
//            // User is not authenticated or signed in, handle the situation accordingly
//        }
        if (user != null) {
            String userId = user.getUid();

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users")
                    .document(userId)
                    .get()
                    .addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            DocumentSnapshot document = task.getResult();
                            if (document != null && document.exists()) {
                                String email = document.getString("email");
                                String name = document.getString("name");
                                String userType = document.getString("userType");

                                profileEmail.setText(email);
                                profileName.setText(name);

                                if (userType.equals("faculty")) {
                                    // Handle faculty specific UI or actions if needed
                                } else if (userType.equals("student")) {
                                    // Handle student specific UI or actions if needed
                                }
                            }
                        } else {
                            // Handle any errors that occurred during the retrieval
                        }
                    });
        }
    }
    private void openFileChooser() {
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        intent.addCategory(Intent.CATEGORY_OPENABLE);

        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(Intent.createChooser(intent, "Select Image"), REQUEST_IMAGE);
        }
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_IMAGE && resultCode == RESULT_OK && data != null) {
            selectedImageUri = data.getData();
            profile.setImageURI(selectedImageUri);
        }
    }

    private boolean isFacultyUser(String userId) {
        return userId.startsWith("FA_");
    }
}
