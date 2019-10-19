package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    private Button Contacts;

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


    }


}
