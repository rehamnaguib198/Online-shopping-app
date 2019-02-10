package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class FirebaseClient  {

    Context c;
    String DB_URL;
    ListView listView;
    Firebase firebase;
    ArrayList<Model> models= new ArrayList<>();
    CustomAdapter customAdapter;


    public  FirebaseClient(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;


        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    public  void savedata(String name, String post,String time,String description,String proPic)
    {
        Model d= new Model();
        d.setName(name);
        d.setPost(post);
        d.setTime(time);
        d.setProPic(proPic);
        d.setDescription(description);
        firebase.child("posts").push().setValue(d);


    }

    public  void refreshdata()
    {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                getupdates(dataSnapshot);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                getupdates(dataSnapshot);
            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getupdates(DataSnapshot dataSnapshot){

        models.clear();

        for(DataSnapshot ds :dataSnapshot.getChildren()){
            Model d= new Model();
            d.setName(ds.getValue(Model.class).getName());
            d.setPost(ds.getValue(Model.class).getPost());
            d.setDescription(ds.getValue(Model.class).getDescription());
            d.setTime(ds.getValue(Model.class).getTime());
            d.setProPic(ds.getValue(Model.class).getProPic());
            models.add(d);

        }
        if(models.size()>0)
        {
            customAdapter=new CustomAdapter(c, models);
            listView.setAdapter((ListAdapter) customAdapter);
        }else
        {
            Toast.makeText(c, "No data", Toast.LENGTH_SHORT).show();
        }
    }
}
