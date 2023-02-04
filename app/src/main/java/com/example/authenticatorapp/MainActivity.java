package com.example.authenticatorapp;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.ListenerRegistration;

import javax.security.auth.Subject;

public class MainActivity extends AppCompatActivity {

    TextView fullName, email, phone, _user, stream;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userID;
    Button _btn;


    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        phone = findViewById(R.id.profilePhone);
        email = findViewById(R.id.profileEmail);
        stream = findViewById(R.id.Stream);
        fullName = findViewById(R.id.profileName);
        fullName = findViewById(R.id.profileName);
        _btn = findViewById(R.id.LogoutBtn);
        _user = findViewById(R.id.welcomeUser);

//        maths = findViewById(R.id.marks1);
//        eng = findViewById(R.id.marks2);
//        science = findViewById(R.id.marks3);
//        sexed = findViewById(R.id.marks4);
//        percent = findViewById(R.id.grade);

        fStore = FirebaseFirestore.getInstance();
        fAuth = FirebaseAuth.getInstance();

        userID = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userID);

//        resolved logout bug
        ListenerRegistration lg = documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                phone.setText(documentSnapshot.getString("phone"));
                fullName.setText(documentSnapshot.getString("fName"));
                _user.setText("Welcome, "+ documentSnapshot.getString("fName"));
                email.setText(documentSnapshot.getString("email"));
                stream.setText(documentSnapshot.getString("stream"));

//                maths.setText(documentSnapshot.getString("maths"));
//                eng.setText(documentSnapshot.getString("english"));
//                science.setText(documentSnapshot.getString("science"));
//                sexed.setText(documentSnapshot.getString("sexed"));
//                percent.setText(documentSnapshot.getString("percent"));
            }
        });
        _btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lg.remove();
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(getApplicationContext(), Login.class));

                finish();
            }
        });


    }}
