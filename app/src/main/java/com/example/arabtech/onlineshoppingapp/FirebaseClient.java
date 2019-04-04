package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class FirebaseClient  {

    private Context c;
    private String DB_URL;
    private ListView listView;
    private Firebase firebase;
    private Product product;


    public  FirebaseClient(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    public FirebaseClient(Context c, String DB_URL, Product product) {
        this.c = c;
        this.product = product;
        this.DB_URL = DB_URL;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    public FirebaseClient(Context c, String DB_URL) {
        this.c= c;
        this.DB_URL = DB_URL;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }

    /* public  void savedata(String name, String post,String time,String description,String proPic)
     {
         Model d= new Model();
         d.setName(name);
         d.setPost(post);
         d.setTime(time);
         d.setProPic(proPic);
         d.setDescription(description);
         firebase.child("posts").push().setValue(d);


     }
 */
    public void addProduct() {
        /*firebase.child(product.getId()).child("description").setValue(product.getDescription());
        firebase.child(product.getId()).child("size").setValue(product.getSize());
        firebase.child(product.getId()).child("color").setValue(product.getColor());
        firebase.child(product.getId()).child("price").setValue(product.getPrice());
        firebase.child(product.getId()).child("notes").setValue(product.getNotes());
        firebase.child(product.getId()).child("offers").setValue(product.getOffers());
        firebase.child(product.getId()).child("category").setValue(product.getCategory());
        firebase.child(product.getId()).child("time").setValue(product.getTime());*/

        if (product.getUri1() != null) {
            uploadImage(product.getUri1());
        } else {
            firebase.child(product.getId()).child("img1").setValue("noImage");
        }
        /*uploadImage(2,product.getUri2());
        uploadImage(3, product.getUri3());
        uploadImage(4, product.getUri4());*/

        /*firebase.child(product.getId()).child("img1").setValue(product.getImg1());
        firebase.child(product.getId()).child("img2").setValue(product.getImg2());
        firebase.child(product.getId()).child("img3").setValue(product.getImg3());
        firebase.child(product.getId()).child("img4").setValue(product.getImg4());*/
    }

    private void uploadImage(Uri uri) {
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        final StorageReference path = storage.child(uri.getLastPathSegment());

        path.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(c, "Uploaded", Toast.LENGTH_LONG).show();
            }
        });
    }

    public void productsUpdate() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Stores stores = Stores.getInstance();
                Product product = new Product();
                product.setId(dataSnapshot.getKey());
                product.setDescription(dataSnapshot.getValue(Product.class).getDescription());
                product.setTime(dataSnapshot.getValue(Product.class).getTime());
                product.setImg1(dataSnapshot.getValue(Product.class).getImg1());
                stores.getCurrent().getProducts().add(product);
                stores.getCurrent().updateProducts(c, listView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    public void getDetails() {
        firebase.addChildEventListener(new ChildEventListener() {
            Stores stores = Stores.getInstance();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("size")) {
                    stores.getCurrent().getSelected().setSize(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("color")) {
                    stores.getCurrent().getSelected().setColor(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("price")) {
                    stores.getCurrent().getSelected().setPrice(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("offers")) {
                    stores.getCurrent().getSelected().setOffers(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("notes")) {
                    stores.getCurrent().getSelected().setNotes(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("img2")) {
                    stores.getCurrent().getSelected().setImg2(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("img3")) {
                    stores.getCurrent().getSelected().setImg3(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("img4")) {
                    stores.getCurrent().getSelected().setImg4(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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

    public void storesUpdate() {
        firebase.addChildEventListener(new ChildEventListener() {
            Stores stores = Stores.getInstance();
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                String name = dataSnapshot.getKey();
                stores.getNames().add(name);
                stores.updateList(c, listView);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {
                String name = dataSnapshot.getKey();
                stores.getNames().remove(name);
                stores.updateList(c, listView);
            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    public void getStoreInfo() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                if (dataSnapshot.getKey().equals("logo")) {
                    Stores stores = Stores.getInstance();
                    stores.getCurrent().setLogo(dataSnapshot.getValue(String.class));
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

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
    /*public  void refreshdata()
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
    }*/
}
