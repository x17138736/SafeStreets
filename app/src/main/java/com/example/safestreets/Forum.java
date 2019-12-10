package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Person;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.StringTokenizer;
import java.util.jar.Attributes;

public class Forum extends AppCompatActivity {


    EditText nameInput, emailInput, streetnameInput, experienceInput;
    Button submitBTN, btnSave;
    TextView tvData;

    ArrayList<ForumInputs> people;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);

        nameInput = findViewById(R.id.nameInput);
        emailInput = findViewById(R.id.emailInput);
        streetnameInput = findViewById(R.id.streetnameInput);
         experienceInput= findViewById(R.id.experienceInput);
         submitBTN= findViewById(R.id.submitBTN);
         btnSave= findViewById(R.id.btnSave);
         tvData= findViewById(R.id.tvData);


         people = new ArrayList<ForumInputs>();


         loadData();

         submitBTN.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 if (nameInput.getText().toString().isEmpty() ||
                    emailInput.getText().toString().isEmpty() ||
                    streetnameInput.getText().toString().isEmpty() ||
                 experienceInput.getText().toString().isEmpty())
                 {
                     Toast.makeText(Forum.this, "Please enter details in all fields", Toast.LENGTH_SHORT).show();
                 }
                 else{
                     ForumInputs forumInputss = new ForumInputs(nameInput.getText().toString().trim(),
                             emailInput.getText().toString().trim(), streetnameInput.getText().toString().trim(),
                             experienceInput.getText().toString().trim());
                     people.add(forumInputss);

                     setTextToTextView();
                 }
             }
         });
         btnSave.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {

                 try{
                     FileOutputStream file = openFileOutput("Data.txt", MODE_PRIVATE); //connection to the file
                     OutputStreamWriter outputFile = new OutputStreamWriter(file);//writes to the file

                     for(int i =0; i<people.size();i++){
                         outputFile.write(people.get(i).getName()+","+ people.get(i).getEmail() + ","+
                         people.get(i).getStreetname() +","+people.get(i).getExperience()+"\n");
                     }

                     outputFile.flush(); //make sure all is written to the file
                     outputFile.close(); //then close it

                     Toast.makeText(Forum.this, "Inputs written to file!", Toast.LENGTH_SHORT).show();
                 }
                 catch (IOException e)
                 {
                     Toast.makeText(Forum.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                 }
             }
         });


    }

    private void loadData() {

        people.clear();

        File file = getApplicationContext().getFileStreamPath("Data.txt"); //checking if the file exits

        String lineFromFile;

        if(file.exists()) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(openFileInput("Data.txt")));// the open stream reader will allow us to open the data file


                while((lineFromFile = reader.readLine()) != null)                                                    // reading the line from the data file, then saves into the Line fromFile, making sure its not null
                {
                    StringTokenizer tokens = new StringTokenizer(lineFromFile,",");                          // helps us get tokens from a string

                    ForumInputs forumInputss = new ForumInputs(tokens.nextToken(), tokens.nextToken(),
                            tokens.nextToken(),tokens.nextToken());
                    people.add(forumInputss);
                }

                reader.close();
                setTextToTextView();
        }
            catch (IOException e){

                Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();

            }
        }
        else{

            Toast.makeText(this, "No data to show that is in the database", Toast.LENGTH_SHORT).show();
        }

    }

    private void setTextToTextView() {


        String text ="";

        for(int i =0; i < people.size(); i++){
            text = text + people.get(i).getName()+"\n"+people.get(i).getEmail() +"\n" +
                    people.get(i).getStreetname()+"\n"+people.get(i).getExperience() +"\n";
        }
        tvData.setText(text);

    }
}
// reference https://www.youtube.com/watch?v=bMribUyNPBo  https://www.youtube.com/watch?v=V0AETAjxqLI


