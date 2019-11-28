package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Forum extends AppCompatActivity {


        String name;
        String email;
        String streetName;
        String experience;

        EditText nameInput;
        EditText emailInput;
        EditText streetNameInput;
        EditText experienceInput;

        Button submitBTN;






        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forum);

            nameInput = (EditText) findViewById(R.id.nameInput);
            emailInput = (EditText) findViewById(R.id.emailInput);
            streetNameInput = (EditText) findViewById(R.id.streetnameInput);
            experienceInput = (EditText) findViewById(R.id.experienceInput);

            submitBTN = (Button) findViewById(R.id.submitBTN);
            submitBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = nameInput.getText().toString();
                    email = emailInput.getText().toString();
                    streetName = streetNameInput.getText().toString();
                    experience = experienceInput.getText().toString();


                    showToast(name);
                    showToast(email);
                    showToast(streetName);
                    showToast(experience);



                }
            });


        }

        private void showToast(String text){
            Toast.makeText(Forum.this, text, Toast.LENGTH_SHORT).show();
        }

    }
// reference  https://www.youtube.com/watch?v=V0AETAjxqLI


