package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.net.Uri;
import android.widget.Toast;

import java.util.UUID;

public class Product {

    private Shop shop;
    private String description;
    private String notes;
    private String offers;
    private String time;
    private String img1, img2, img3, img4;
    private String color;
    private String size;
    private String id;
    private String price;
    private String category;
    private Uri uri1;
    private Uri uri2;
    private Uri uri3;
    private Uri uri4;

    public Shop getShop() {
        return shop;
    }

    public void setShop(Shop shop) {
        this.shop = shop;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOffers() {
        return offers;
    }

    public void setOffers(String offers) {
        this.offers = offers;
    }

    public String getImg1() {
        return img1;
    }

    public void setImg1(String img1) {
        this.img1 = img1;
    }

    public String getImg2() {
        return img2;
    }

    public void setImg2(String img2) {
        this.img2 = img2;
    }

    public String getImg3() {
        return img3;
    }

    public void setImg3(String img3) {
        this.img3 = img3;
    }

    public String getImg4() {
        return img4;
    }

    public void setImg4(String img4) {
        this.img4 = img4;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getSize() {
        return size;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Uri getUri1() {
        return uri1;
    }

    public void setUri1(Uri uri1) {
        this.uri1 = uri1;
    }

    public Uri getUri2() {
        return uri2;
    }

    public void setUri2(Uri uri2) {
        this.uri2 = uri2;
    }

    public Uri getUri3() {
        return uri3;
    }

    public void setUri3(Uri uri3) {
        this.uri3 = uri3;
    }

    public Uri getUri4() {
        return uri4;
    }

    public void setUri4(Uri uri4) {
        this.uri4 = uri4;
    }

    public void add(Context c) {
        String DB_URL= "https://onlineshopping-2857f.firebaseio.com/Stores/"+getShop().getName()+"/Products";
        FirebaseClient firebaseClient = new FirebaseClient(c, DB_URL, this);
        firebaseClient.addProduct();
       /* DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
        ref.child("Stores").child("Rojada").child("Products").child("001").child("description").setValue(this.getDescription());*/
    }

    public void show(Context c) {
        Stores stores = Stores.getInstance();
        String DB_URL;
        if (stores.isCurrentFlag()) {
            DB_URL= "https://onlineshopping-2857f.firebaseio.com/Stores/" + stores.getCurrent().getName() + "/Products/" + this.id ;
        } else {
            DB_URL = "https://onlineshopping-2857f.firebaseio.com/Stores/" + getShop().getName() + "/Products/" + this.id ;
        }
        FirebaseManager firebaseManager = FirebaseManager.getInstance();
        FirebaseClient firebaseClient = new FirebaseClient(c, DB_URL, this);
        firebaseManager.setFirebaseClient(firebaseClient);
        firebaseClient.getDetails();
    }

}
