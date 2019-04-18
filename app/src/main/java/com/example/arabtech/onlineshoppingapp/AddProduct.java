package com.example.arabtech.onlineshoppingapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.UUID;

public class AddProduct extends AppCompatActivity {

    private EditText description;
    private EditText size;
    private EditText color;
    private EditText notes;
    private EditText price;
    private EditText offers;

    private Spinner categories;

    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;

    private Button upload1;
    private Button upload2;
    private Button upload3;
    private Button upload4;
    private Button add;

    private Product product;

    private int num;
    private Uri uri1;
    private Uri uri2;
    private Uri uri3;
    private Uri uri4;

    private String category;

    private static final int GALLERY_INENT = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setActivity(this);

        description = findViewById(R.id.add_description);
        size = findViewById(R.id.add_size);
        color = findViewById(R.id.add_color);
        notes = findViewById(R.id.add_notes);
        price = findViewById(R.id.add_price);
        offers = findViewById(R.id.add_offers);

        categories = findViewById(R.id.add_category);
        categories.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = categories.getItemAtPosition(position).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        img1 = findViewById(R.id.add_image1);
        img2 = findViewById(R.id.add_image2);
        img3 = findViewById(R.id.add_image3);
        img4 = findViewById(R.id.add_image4);

        upload1 = findViewById(R.id.upload_img1);
        upload1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INENT);

                num = 1;

                /*try {
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img1.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e){
                    e.printStackTrace();
                }*/
            }
        });
        upload2 = findViewById(R.id.upload_img2);
        upload2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INENT);

                num = 2;

                /*try {
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img2.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        });
        upload3 = findViewById(R.id.upload_img3);
        upload3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INENT);

                num = 3;

                /*try {
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img3.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        });
        upload4 = findViewById(R.id.upload_img4);
        upload4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, GALLERY_INENT);

                num = 4;

                /*try {
                    InputStream imageStream = getContentResolver().openInputStream(uri);
                    Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                    img4.setImageBitmap(selectedImage);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }*/
            }
        });
        add = findViewById(R.id.add_product);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                product = new Product();
                product.setColor(color.getText().toString());
                product.setSize(size.getText().toString());
                product.setNotes(notes.getText().toString());
                product.setOffers(offers.getText().toString());
                product.setDescription(description.getText().toString());
                product.setPrice(price.getText().toString());
                product.setCategory(category);
                product.setUri1(uri1);
                product.setUri2(uri2);
                product.setUri3(uri3);
                product.setUri4(uri4);
                product.setTime(Calendar.getInstance().getTime().toString());
                product.add(getApplicationContext());
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_INENT && resultCode == RESULT_OK) {
            switch (num) {
                case 1:
                    uri1 = data.getData();
                    break;
                case 2:
                    uri2 = data.getData();
                    break;
                case 3:
                    uri3 = data.getData();
                    break;
                case 4:
                    uri4 = data.getData();
                    break;
            }
        }
    }
}
