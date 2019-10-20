package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    private Button Contacts;
    private Button TermsOfService;
    private Button SignOut;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Contacts = findViewById(R.id.btnContactsPage);
        Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContacts();
            }
        });


    }

    public void openContacts() {
        Intent intent = new Intent(this, Contacts.class);
        startActivity(intent);


        TermsOfService = findViewById(R.id.btnTermsOfService);
        TermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermsOfService();
            }
        });

    }

    public void openTermsOfService() {
        Intent intent = new Intent(this, TermsOfService.class);
        startActivity(intent);


        SignOut = findViewById(R.id.btnSignOut);
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();

            }
        });


    }

    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);

    }
}