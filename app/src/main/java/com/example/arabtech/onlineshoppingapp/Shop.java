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
   private boolean registered = false;

    public Shop(String name) {
        products = new ArrayList<Product>();
        this.name = name;
    }

    public ArrayList<Product> getFiltered() {
        return filtered;
    }

    public void setFiltered(ArrayList<Product> filtered) {
        this.filtered = filtered;
    }

    public boolean isFilters() {
        return filters;
    }

    public void setFilters(boolean filters) {
        this.filters = filters;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
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
            if (registered) {
                customAdapter = new CustomAdapter(c, this.products, this);
            } else {
                customAdapter = new CustomAdapter(c, this.products);
            }

        }
        list.setAdapter(customAdapter);
    }

    public void selectProduct(int index) {
        if (filters) {
            selected = filtered.get(index);
        } else {
            selected = products.get(index);
        }
        selected.setShop(this);
    }
    public void deleteProduct(int index){
        if (filters) {
            selected = filtered.remove(index);
        } else {
            selected = products.remove(index);
        }

    }

}
