package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.jar.Attributes;

public class Forum extends AppCompatActivity {


        String name;
        String email;
        String streetName;
        String experience;
        String result;
        String result2;
        String result3;
        String result4;

        EditText nameInput;
        EditText emailInput;
        EditText streetNameInput;
        EditText experienceInput;
    EditText resultInput;
    EditText resultInput2;
    EditText resultInput3;
    EditText resultInput4;
        Button submitBTN;






        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_forum);

            nameInput = (EditText) findViewById(R.id.nameInput);
            emailInput = (EditText) findViewById(R.id.emailInput);
            streetNameInput = (EditText) findViewById(R.id.streetnameInput);
            experienceInput = (EditText) findViewById(R.id.experienceInput);
            resultInput = (EditText) findViewById(R.id.resultInput);
            resultInput2 = (EditText) findViewById(R.id.resultInput2);
            resultInput3 = (EditText) findViewById(R.id.resultInput3);
            resultInput4 = (EditText) findViewById(R.id.resultInput4);
            submitBTN = (Button) findViewById(R.id.submitBTN);
            submitBTN.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    name = nameInput.getText().toString();
                    email = emailInput.getText().toString();
                    streetName = streetNameInput.getText().toString();
                    experience = experienceInput.getText().toString();
                    result = resultInput.getText().toString();
                    result2 = resultInput2.getText().toString();
                    result3 = resultInput3.getText().toString();
                    result4 = resultInput4.getText().toString();

                   // showToast(name);
                   // showToast(email);
                    //showToast(streetName);
                   // showToast(experience);
                    resultInput.setText("Name: "+name );
                    resultInput2.setText("Email: "+email);
                    resultInput3.setText("Street Name: "+streetName );
                    resultInput4.setText("Experience: "+experience);

                }
            });


        }

       // private void showToast(String text){
         //   Toast.makeText(Forum.this, text, Toast.LENGTH_SHORT).show();
      //  }

    }
// reference  https://www.youtube.com/watch?v=V0AETAjxqLI


