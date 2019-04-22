package com.example.arabtech.onlineshoppingapp;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;

public class StoreView extends AppCompatActivity {
    private ListView listView;
    private FirebaseListAdapter<Model> adapter;
    private TextView name;
    private TextView time;
    private TextView description;
    private ImageView proPic;
    private ImageView post;
    private String DB_URL;
    private Stores stores;
    private FirebaseClient firebaseClient;

    private MyStore store;
    private FirebaseAuth mAuth=FirebaseAuth.getInstance();;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_view);
        ViewManager viewManager = ViewManager.getInstance();
        viewManager.setActivity(this);
        store = new MyStore();
        setFragment(store);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater=getMenuInflater();
        inflater.inflate(R.menu.store_menu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId()==R.id.logout){
            mAuth.signOut();
            finish();
        }
        else if(item.getItemId()==R.id.addProduct){
            Intent intent = new Intent(StoreView.this, AddProduct.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        mAuth.signOut();
    }

    private void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
}
