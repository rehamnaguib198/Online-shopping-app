package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.widget.ListView;

import java.util.ArrayList;

public class Shop {
   private ArrayList<Product> products;
   private ArrayList<Product> filtered;
   private String name;
   private String logo;
   private Product selected;
   private boolean filters;

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

    /*public void setSelected(Product selected) {
        this.selected = selected;
    }*/

    public void updateProducts(Context c, ListView list) {
        CustomAdapter customAdapter;
        if (filters) {
            customAdapter = new CustomAdapter(c, this.filtered);
        } else {
            customAdapter = new CustomAdapter(c, this.products);
        }
        list.setAdapter(customAdapter);
    }

    public void selectProduct(int index) {
        if (filters) {
            selected = filtered.get(index);
        } else {
            selected = products.get(index);
        }

    }

}
