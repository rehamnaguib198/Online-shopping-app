package com.example.arabtech.onlineshoppingapp;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class Profile extends Fragment {

    private ListView storesList;
    private FirebaseClient firebaseClient;
    private String DB_URL;
    private Stores stores;

    public Profile() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        storesList = view.findViewById(R.id.storesListView);
        DB_URL= "https://onlineshopping-2857f.firebaseio.com/Stores";
        stores = Stores.getInstance();
        stores.getNames().clear();
        firebaseClient = new FirebaseClient(getContext(), DB_URL, storesList);
        firebaseClient.storesUpdate();
        storesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String name = (String)storesList.getItemAtPosition(position);
                Stores stores = Stores.getInstance();
                stores.setCurrentFlag(true);
                stores.setCurrent(name);
                DB_URL = "https://onlineshopping-2857f.firebaseio.com/Stores/" + name + "/Details";
                firebaseClient = new FirebaseClient(getContext(), DB_URL);
                firebaseClient.getStoreInfo();
                setFragment(new Store());
            }
        });
        return view;

    }

    public void setFragment(android.support.v4.app.Fragment fragment) {
        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
        fragmentTransaction.replace(R.id.mainFrame, fragment);
        fragmentTransaction.commit();
    }
}
