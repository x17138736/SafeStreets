package com.example.safestreets;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private EditText Name;
    private EditText Password;
    private TextView Info;
    private Button Login;
    private int counter = 3;
    private TextView userRegistration;
    private FirebaseAuth firebaseAuth;
    private ProgressDialog progressDialog;

    //https://youtu.be/M0bYvXlhgSI as guide
    //private static final String TAG = "MainActivity";
    //private static final int ERROR_DIALOG_REQUEST = 9001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Name = (EditText)findViewById(R.id.etName);
        Password = (EditText)findViewById(R.id.etPassword);
        Info = (TextView)findViewById(R.id.tvInfo);
        Login = (Button)findViewById(R.id.btnLogin);
        userRegistration = (TextView)findViewById(R.id.tvRegister);

        Info.setText("No of attempts remaining: 3");

        firebaseAuth = FirebaseAuth.getInstance();
        progressDialog = new ProgressDialog(this);

        FirebaseUser user = firebaseAuth.getCurrentUser();

        if(user != null) {
            finish();
            startActivity(new Intent(MainActivity.this, SecondActivity.class));
        }

        Login.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View view){
            validate(Name.getText().toString(), Password.getText().toString());
        }
    });
        userRegistration.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
            }
        });
    }
    private void validate (String userName, String userPassword){

        progressDialog.setMessage("Stay cool until you are verified!");
        progressDialog.show();

        firebaseAuth.signInWithEmailAndPassword(userName, userPassword).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()) {
                    progressDialog.dismiss();
                    Toast.makeText(MainActivity.this, "Login Successful", Toast.LENGTH_SHORT).show();
                    startActivity(new Intent(MainActivity.this, RegistrationActivity.class));
                }else{
                    Toast.makeText(MainActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
                    counter--;
                    Info.setText("No of attempts remaining: " + counter);
                    progressDialog.dismiss();
                    if(counter == 0){
                        Login.setEnabled(false);
                    }
                }
            }
        });



    }
}