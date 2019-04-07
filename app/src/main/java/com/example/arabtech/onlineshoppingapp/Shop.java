package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

public class Shop {
   private ArrayList<Product> products;
   private String name;
   private String logo;
   private Product selected;

    public Shop(String name) {
        products = new ArrayList<Product>();
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public void setProducts(ArrayList<Product> products) {
        this.products = products;
    }

    public Product getSelected() {
        return selected;
    }

    public void updateProducts(Context c, ListView list) {
        CustomAdapter customAdapter = new CustomAdapter(c, this.products);
        list.setAdapter(customAdapter);
    }

    public void selectProduct(int index) {
        selected = products.get(index);
    }

}
