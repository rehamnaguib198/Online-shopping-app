package com.example.arabtech.onlineshoppingapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MyStore extends Store {
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        listView = (ListView) view.findViewById(R.id.listView);
        ViewManager viewManager = ViewManager.getInstance();
        //viewManager.setMyStore(this);
        viewManager.setStore(this);
        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        for (int i = 0; i < firebaseManager.getListener().size(); i++) {
            firebaseManager.getFirebase().get(i).removeEventListener(firebaseManager.getListener().get(i));
        }
        firebaseManager.getFirebase().clear();
        firebaseManager.getListener().clear();
       /* if (stores.isCurrentFlag()) {
            // stores.getCurrent().setSelected(null);
            stores.getCurrent().getProducts().clear();
            DB_URL = "https://onlineshopping-2857f.firebaseio.com/Stores/" + stores.getCurrent().getName() + "/Products";
            firebaseClient= new FirebaseClient(getContext(), DB_URL,listView);
            firebaseClient.productsUpdate();
        } else {
            stores.getAllProducts().clear();
            stores.setSelected(null);
            DB_URL = "https://onlineshopping-2857f.firebaseio.com/Stores";
            firebaseClient= new FirebaseClient(getContext(), DB_URL,listView);
            firebaseClient.allProducts();
        }*/
        String storeName = FirebaseAuth.getInstance().getCurrentUser().getUid();
        final String[] shopName = {""};
        final DatabaseReference ref = FirebaseDatabase.getInstance().getReference().child("users").child(storeName).child("name");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                shopName[0] = dataSnapshot.getValue().toString();
                Shop shop=new Shop(shopName[0]);
                shop.setLogo("noImage"); //to be changed
                shop.setRegistered(true);
                DB_URL = "https://onlineshopping-2857f.firebaseio.com/Stores/" + shopName[0] + "/Products";
                firebaseClient = new FirebaseClient(getContext(), DB_URL, listView,shop);
                firebaseClient.productsUpdate();
            }
            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return view;
    }

    /*public void changeFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }*/
}

