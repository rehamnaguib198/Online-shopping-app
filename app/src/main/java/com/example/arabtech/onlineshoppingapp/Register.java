package com.example.arabtech.onlineshoppingapp;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

import static util.Constants.ERROR_DIALOG_REQUEST;
import static util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Register extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passEditText;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.pass);
    }

    public void setLocation(View view) {
        Intent intent = new Intent(Register.this, MapsActivity.class);
        Bundle b = new Bundle();
        b.putString("name", nameEditText.getText().toString());
        intent.putExtras(b);
        startActivity(intent);


    }
    public void registerUser(View view){
        mAuth.createUserWithEmailAndPassword(emailEditText.getText().toString(), passEditText.getText().toString())
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            FirebaseUser user = mAuth.getCurrentUser();
                            HashMap<String,String> userMap=new HashMap();
                            userMap.put("name",nameEditText.getText().toString());
                            userMap.put("email",emailEditText.getText().toString());
                            userMap.put("password",passEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).setValue(userMap);
                            startActivity(new Intent(Register.this, MainActivity.class));
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
