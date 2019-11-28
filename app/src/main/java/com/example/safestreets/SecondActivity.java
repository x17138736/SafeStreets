package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private Button SafeStreetMap;
    private Button RouteMaker;
    private Button Forum;
    private Button Settings;
    private TextView Info1;
    private FirebaseAuth firebaseAuth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SafeStreetMap=(Button) findViewById(R.id.btnSafeStreetMap);
        RouteMaker=(Button) findViewById(R.id.btnRouteMaker);
        Forum=(Button) findViewById(R.id.btnForum);
        Settings=(Button) findViewById(R.id.btnSettings);


        SafeStreetMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSafeStreetMap();
            }
        });

        RouteMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRouteMaker();
            }
        });

        Forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForum();
            }
        });

        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });

        firebaseAuth=FirebaseAuth.getInstance();


    }

        private void openSafeStreetMap () {
            Intent intent=new Intent(SecondActivity.this, SafeStreetMap.class);
            startActivity(intent);

        }
        private void openRouteMaker () {
            Intent intent=new Intent(SecondActivity.this, RouteMaker.class);
            startActivity(intent);

        }

        private void openForum () {
            Intent intent=new Intent(SecondActivity.this, Forum.class);
            startActivity(intent);

        }

        private void openSettings() {
            Intent intent=new Intent(SecondActivity.this, Settings.class);
            startActivity(intent);

        }


    }




