package com.example.arabtech.onlineshoppingapp;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseListAdapter;
import com.firebase.ui.database.FirebaseListOptions;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.lang.reflect.Array;


/**
 * A simple {@link Fragment} subclass.
 */
@SuppressLint("ValidFragment")
public class Store extends Fragment {
private ListView listView;
private FirebaseListAdapter<Model> adapter;
     TextView name;
     TextView time;
    TextView description;
     ImageView proPic;
     ImageView post;
    final static  String DB_URL= "https://onlineshopping-2857f.firebaseio.com/";
    FirebaseClient firebaseClient;
    @SuppressLint("ValidFragment")
    public Store(View view) {
         name=view.findViewById(R.id.name);
         time=view.findViewById(R.id.time);
         description=view.findViewById(R.id.description);
         proPic=view.findViewById(R.id.imgView_proPic);
         post=view.findViewById(R.id.imgView_postPic);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_store, container, false);
        listView=(ListView) view.findViewById(R.id.listView);
        firebaseClient= new FirebaseClient(getContext(), DB_URL,listView);
        firebaseClient.refreshdata();
        /*Query query= FirebaseDatabase.getInstance().getReference().child("posts");
        FirebaseListOptions<Model> options=new FirebaseListOptions.Builder<Model>().setLayout(R.layout.row_feed).setQuery(query,Model.class).build();
        adapter=new FirebaseListAdapter<Model>(options) {
            @Override
            protected void populateView(@NonNull View v, @NonNull Model model, int position) {
                Model rowModel=(Model) model;
                name.setText(rowModel.getName().toString());
                time.setText(rowModel.getTime().toString());
                description.setText(rowModel.getDescription().toString());
                Picasso.with(getContext()).load(rowModel.getProPic()).into(proPic);
                Picasso.with(getContext()).load(rowModel.getPost()).into(post);
                Toast.makeText(getContext(),rowModel.getName().toString(),Toast.LENGTH_LONG);
            }
        };

        listView.setAdapter(adapter);*/
        return view;
    }
}
