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
    private Button logout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        SafeStreetMap=(Button) findViewById(R.id.btnSafeStreetMap);
        SafeStreetMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSafeStreetMap();
            }
        });
    }

    public void openSafeStreetMap() {
        Intent intent=new Intent(this, SafeStreetMap.class);
        startActivity(intent);


        RouteMaker=(Button) findViewById(R.id.btnRouteMaker);
        RouteMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRouteMaker();
            }
        });
    }

    public void openRouteMaker() {
        Intent intent=new Intent(this, RouteMaker.class);
        startActivity(intent);


        Forum=(Button) findViewById(R.id.btnForum);
        Forum.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openForum();
            }
        });
    }

    public void openForum() {
        Intent intent=new Intent(this, Forum.class);
        startActivity(intent);

        Settings=(Button) findViewById(R.id.btnSettings);
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSettings();
            }
        });
    }

    public void openSettings() {
        Intent intent=new Intent(this, Settings.class);
        startActivity(intent);

        firebaseAuth=FirebaseAuth.getInstance();

        logout=(Button) findViewById(R.id.btnLogout);
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Logout();
            }
        });
    }

    private void Logout() {
        firebaseAuth.signOut();
        finish();
        startActivity(new Intent(SecondActivity.this, MainActivity.class));

        Info1=(TextView) findViewById(R.id.tvInfo1);

    }
}


