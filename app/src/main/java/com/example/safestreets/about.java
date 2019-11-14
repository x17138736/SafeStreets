package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class about extends AppCompatActivity {
    private Button Settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);




        Settings = (Button) findViewById(R.id.btnBack3);
        Settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                backToSettings();
            }
        });


    }
    public void backToSettings() {
        Intent intent = new Intent(this, Settings.class);
        startActivity(intent);
    }
}

