package com.example.arabtech.onlineshoppingapp;

import com.firebase.client.ChildEventListener;
import com.firebase.client.Firebase;

import java.util.ArrayList;


public class FirebaseManager {
    private static FirebaseManager instance = null;
    private ArrayList<ChildEventListener> listener = new ArrayList<ChildEventListener>();
    private ArrayList<Firebase> firebase = new ArrayList<Firebase>();
    private FirebaseClient firebaseClient;

    private FirebaseManager() {

    }
    public static FirebaseManager getInstance() {
        if (instance == null) {
            instance = new FirebaseManager();
        }
        return instance;
    }

    public FirebaseClient getFirebaseClient() {
        return firebaseClient;
    }

    public void setFirebaseClient(FirebaseClient firebaseClient) {
        this.firebaseClient = firebaseClient;
    }

    public ArrayList<Firebase> getFirebase() {
        return firebase;
    }

    public void setFirebase(ArrayList<Firebase> firebase) {
        this.firebase = firebase;
    }

    public ArrayList<ChildEventListener> getListener() {
        return listener;
    }

    public void setListener(ArrayList<ChildEventListener> listener) {
        this.listener = listener;
    }
}
