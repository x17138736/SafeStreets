package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TermsOfService extends AppCompatActivity {
    private Button Settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_terms_of_service);




            Settings = (Button) findViewById(R.id.btnBack2);
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

