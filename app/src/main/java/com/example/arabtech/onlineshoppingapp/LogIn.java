package com.example.arabtech.onlineshoppingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class LogIn extends AppCompatActivity {
EditText email;
EditText password;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
        email=(EditText) findViewById(R.id.emailEditText);
        password=(EditText) findViewById(R.id.passwordEditText);
    }
    public void login(View view){

    }
    public void register(View view){

    }
}
