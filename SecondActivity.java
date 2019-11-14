package com.example.safestreets;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.firebase.auth.FirebaseAuth;

public class SecondActivity extends AppCompatActivity {

    private Button SafeStreetMap;
    private Button RouteMaker;
    private Button Forum;
    private Button Settings;
    private TextView Info1;
    private FirebaseAuth firebaseAuth;
    private Button logout;

    //https://youtu.be/M0bYvXlhgSI as guide
    private static final String TAG = "SecondActivity";
    private static final int ERROR_DIALOG_REQUEST = 9001;



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

        RouteMaker=(Button) findViewById(R.id.btnRouteMaker);
        RouteMaker.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openRouteMaker();
            }
        });

        if(isMapServiceOk()){
            init();
        }
    }

    private void init(){
        Button btnOpenSafeStreetMap = (Button) findViewById(R.id.btnSafeStreetMap);
        btnOpenSafeStreetMap.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, SafeStreetMap.class);
                startActivity(intent);
            }
        });

        Button btnOpenRouteMaker = (Button) findViewById(R.id.btnRouteMaker);
        btnOpenRouteMaker.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SecondActivity.this, RouteMaker.class);
                startActivity(intent);
            }
        });
    }
    public boolean isMapServiceOk(){
        Log.d(TAG, "SecondActivity: version check of google services");

        int available = GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(SecondActivity.this);

        if(available == ConnectionResult.SUCCESS){
            //user jas correct version, map requests can be made
            Log.d(TAG, "SecondActivity: Google play services is working");
            return true;
        }
        else if(GoogleApiAvailability.getInstance().isUserResolvableError(available)){
            //there was an error that can be resolved.
            Log.d(TAG, "isMapServiceOk: an error occurred, potential versioning fault");
            Dialog dialog =GoogleApiAvailability.getInstance().getErrorDialog(SecondActivity.this, available, ERROR_DIALOG_REQUEST);
            dialog.show();

        }
        else{
            Toast.makeText(this, "Map requests cannot be made", Toast.LENGTH_SHORT).show();
        }
        return false;
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


