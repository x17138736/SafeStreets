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

        Contacts = (Button) findViewById(R.id.btnContactsPage);
        TermsOfService = (Button) findViewById(R.id.btnToS);
        SignOut = (Button) findViewById(R.id.btnSignOut);

        Contacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openContacts();
            }
        });
        TermsOfService.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openTermsOfService();
            }
        });
        SignOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openMainActivity();
            }
        });
    }

    public void openContacts() {
        Intent intent = new Intent(this, Contacts.class);
        startActivity(intent);
    }
    public void openTermsOfService() {
        Intent intent = new Intent(this, TermsOfService.class);
        startActivity(intent);
    }
    public void openMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }





}







