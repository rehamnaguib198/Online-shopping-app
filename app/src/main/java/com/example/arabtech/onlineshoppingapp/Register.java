package com.example.arabtech.onlineshoppingapp;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.location.LocationManager;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.net.URL;
import java.util.HashMap;
import java.util.UUID;

import static util.Constants.ERROR_DIALOG_REQUEST;
import static util.Constants.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;
import static util.Constants.PERMISSIONS_REQUEST_ENABLE_GPS;

public class Register extends AppCompatActivity {
    private EditText nameEditText;
    private EditText emailEditText;
    private EditText passEditText;
    private ImageView imageView;
    String imageName= UUID.randomUUID().toString()+".jpg";
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;
    private Uri selectedImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        nameEditText = findViewById(R.id.name);
        emailEditText = findViewById(R.id.email);
        passEditText = findViewById(R.id.pass);
        imageView=findViewById(R.id.imageView);
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 1) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                getPhoto();
            }
        }

    }

    public void getPhoto() {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    public void importImage(View view) {
        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        } else {
            getPhoto();
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        selectedImage = data.getData();
        if (requestCode == 1 && resultCode == RESULT_OK && selectedImage != null) {
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                imageView.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
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
                            FirebaseDatabase.getInstance().getReference().child("Stores").child(nameEditText.getText().toString()).child("Details").setValue(0);
                            FirebaseDatabase.getInstance().getReference().child("Stores").child(nameEditText.getText().toString()).child("Products").setValue(0);
                            String DB_URL = "https://onlineshopping-2857f.firebaseio.com/Stores/" + nameEditText.getText().toString();
                            FirebaseClient client=new FirebaseClient(getApplicationContext(),DB_URL);
                            client.uploadImage(0,selectedImage);
                            FirebaseUser user = mAuth.getCurrentUser();
                            HashMap<String,String> userMap=new HashMap();
                            userMap.put("name",nameEditText.getText().toString());
                            userMap.put("email",emailEditText.getText().toString());
                            userMap.put("password",passEditText.getText().toString());
                            FirebaseDatabase.getInstance().getReference().child("users").child(task.getResult().getUser().getUid()).setValue(userMap);
                            Intent intent = new Intent(Register.this, StoreView.class);
                            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                            startActivity(intent);
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
