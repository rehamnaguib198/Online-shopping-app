package com.example.arabtech.onlineshoppingapp;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.telecom.Call;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.Toast;
import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.FirebaseDatabase;
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
    private Shop shop=null;


    public  FirebaseClient(Context c, String DB_URL, ListView listView)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        Firebase.setAndroidContext(c);
        firebase=new Firebase(DB_URL);
    }
    public  FirebaseClient(Context c, String DB_URL, ListView listView,Shop shop)
    {
        this.c= c;
        this.DB_URL= DB_URL;
        this.listView= listView;
        this.shop=shop;
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

    public void addProduct() {
        /*firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (d.getKey().equals("ID")) {
                        int x = Integer.valueOf(d.getValue().toString());
                        x++;
                        product.setId(Integer.toString(x));
                        Toast.makeText(c, product.getId(), Toast.LENGTH_LONG).show();
                        firebase.child("ID").setValue(x);
                    }
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
        });*/
        product.setId("1");
        firebase.child(product.getId()).child("description").setValue(product.getDescription());
        firebase.child(product.getId()).child("size").setValue(product.getSize());
        firebase.child(product.getId()).child("color").setValue(product.getColor());
        firebase.child(product.getId()).child("price").setValue(product.getPrice());
        firebase.child(product.getId()).child("notes").setValue(product.getNotes());
        firebase.child(product.getId()).child("offers").setValue(product.getOffers());
        firebase.child(product.getId()).child("category").setValue(product.getCategory());
        firebase.child(product.getId()).child("time").setValue(product.getTime());

        if (product.getUri1() != null) {
            uploadImage(1,product.getUri1());
        } else {
            firebase.child(product.getId()).child("img1").setValue("noImage");
        }
        if (product.getUri2() != null) {
            uploadImage(2,product.getUri2());
        } else {
            firebase.child(product.getId()).child("img2").setValue("noImage");
        }
        if (product.getUri3() != null) {
            uploadImage(3, product.getUri3());
        } else {
            firebase.child(product.getId()).child("img3").setValue("noImage");
        }
        if (product.getUri4() != null) {
            uploadImage(4, product.getUri4());
        } else {
            firebase.child(product.getId()).child("img4").setValue("noImage");
        }
    }

    public void uploadImage(int id, Uri uri) {
        StorageReference storage = FirebaseStorage.getInstance().getReference();
        final StorageReference path = storage.child(uri.getLastPathSegment());

        ViewManager viewManager = ViewManager.getInstance();
        switch (id) {
            case 0:
                if (checkPermissionREAD_EXTERNAL_STORAGE(viewManager.getActivity())) {
                    path.putFile(uri).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                firebase.child("Details").child("logo").setValue(downloadUri.toString());
                            }
                        }
                    });

                }
                break;
            case 1:
                if (checkPermissionREAD_EXTERNAL_STORAGE(viewManager.getActivity())) {
                    path.putFile(product.getUri1()).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                firebase.child(product.getId()).child("img1").setValue(downloadUri.toString());
                            }
                        }
                    });
                }
                break;
            case 2:
                if (checkPermissionREAD_EXTERNAL_STORAGE(viewManager.getActivity())) {
                    path.putFile(product.getUri2()).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                firebase.child(product.getId()).child("img2").setValue(downloadUri.toString());
                            }
                        }
                    });

                }
                break;
            case 3:
                if (checkPermissionREAD_EXTERNAL_STORAGE(viewManager.getActivity())) {
                    path.putFile(product.getUri3()).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                firebase.child(product.getId()).child("img3").setValue(downloadUri.toString());
                            }
                        }
                    });

                }
                break;
            case 4:
                if (checkPermissionREAD_EXTERNAL_STORAGE(viewManager.getActivity())) {
                    path.putFile(product.getUri4()).continueWithTask(new Continuation<UploadTask.TaskSnapshot, Task<Uri>>() {
                        @Override
                        public Task<Uri> then(@NonNull Task<UploadTask.TaskSnapshot> task) throws Exception {
                            if (!task.isSuccessful()) {
                                throw task.getException();
                            }
                            return path.getDownloadUrl();
                        }
                    }).addOnCompleteListener(new OnCompleteListener<Uri>() {
                        @Override
                        public void onComplete(@NonNull Task<Uri> task) {
                            if (task.isSuccessful()) {
                                Uri downloadUri = task.getResult();
                                firebase.child(product.getId()).child("img4").setValue(downloadUri.toString());
                            }
                        }
                    });

                }
                break;
        }
    }

    public void allProducts() {
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Shop shop = new Shop(dataSnapshot.getKey());
                Stores stores = Stores.getInstance();
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    if (data.getKey().equals("Details")) {
                        for (DataSnapshot d : data.getChildren()) {
                            if (d.getKey().equals("logo")) {
                                shop.setLogo(d.getValue().toString());
                            }
                        }
                    } else if (data.getKey().equals("Products")) {
                        for (DataSnapshot d : data.getChildren()) {
                            Product product = new Product();
                            product.setShop(shop);
                            product.setId(d.getKey());
                            product.setDescription(d.getValue(Product.class).getDescription());
                            product.setTime(d.getValue(Product.class).getTime());
                            product.setImg1(d.getValue(Product.class).getImg1());
                            product.setCategory(d.getValue(Product.class).getCategory());
                            product.setPrice(d.getValue(Product.class).getPrice());
                            stores.getAllProducts().add(product);
                        }
                    }
                }
                stores.updateProducts(c, listView);
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
                product.setCategory(dataSnapshot.getValue(Product.class).getCategory());
                product.setPrice(dataSnapshot.getValue(Product.class).getPrice());
                if(shop!=null){
                    shop.getProducts().add(product);
                    shop.updateProducts(c, listView);
                } else{
                    stores.getCurrent().getProducts().add(product);
                    stores.getCurrent().updateProducts(c, listView);
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

    public void getDetails() {
        /*firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    if (d.getKey().equals("size")) {
                        product.setSize(d.getValue(String.class));
                    } else if (d.getKey().equals("color")) {
                        product.setColor(d.getValue(String.class));
                    } else if (d.getKey().equals("price")) {
                        product.setPrice(d.getValue(String.class));
                    } else if (d.getKey().equals("offers")) {
                        product.setOffers(d.getValue(String.class));
                    } else if (d.getKey().equals("notes")) {
                        product.setNotes(d.getValue(String.class));
                    } else if (d.getKey().equals("img2")) {
                        product.setImg2(d.getValue(String.class));
                    } else if (d.getKey().equals("img3")) {
                        product.setImg3(d.getValue(String.class));
                    } else if (d.getKey().equals("img4")) {
                        product.setImg4(d.getValue(String.class));
                    }
                }
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });*/
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                FirebaseManager firebaseManager = FirebaseManager.getInstance();
                firebaseManager.getFirebase().add(firebase);
                firebaseManager.getListener().add(this);
                if (dataSnapshot.getKey().equals("size")) {
                    product.setSize(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("color")) {
                    product.setColor(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("price")) {
                    product.setPrice(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("offers")) {
                    product.setOffers(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("notes")) {
                    product.setNotes(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("img2")) {
                    product.setImg2(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("img3")) {
                    product.setImg3(dataSnapshot.getValue(String.class));
                } else if (dataSnapshot.getKey().equals("img4")) {
                    product.setImg4(dataSnapshot.getValue(String.class));
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

    public static final int MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE = 123;

    public boolean checkPermissionREAD_EXTERNAL_STORAGE(
            final Context context) {
        int currentAPIVersion = Build.VERSION.SDK_INT;
        if (currentAPIVersion >= android.os.Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(context,
                    Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(
                        (Activity) context,
                        Manifest.permission.READ_EXTERNAL_STORAGE)) {
                    showDialog("External storage", context,
                            Manifest.permission.READ_EXTERNAL_STORAGE);

                } else {
                    ActivityCompat
                            .requestPermissions(
                                    (Activity) context,
                                    new String[] { Manifest.permission.READ_EXTERNAL_STORAGE },
                                    MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                }
                return false;
            } else {
                return true;
            }

        } else {
            return true;
        }
    }

    public void showDialog(final String msg, final Context context,
                           final String permission) {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(context);
        alertBuilder.setCancelable(true);
        alertBuilder.setTitle("Permission necessary");
        alertBuilder.setMessage(msg + " permission is necessary");
        alertBuilder.setPositiveButton(android.R.string.yes,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        ActivityCompat.requestPermissions((Activity) context,
                                new String[] { permission },
                                MY_PERMISSIONS_REQUEST_READ_EXTERNAL_STORAGE);
                    }
                });
        AlertDialog alert = alertBuilder.create();
        alert.show();
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
