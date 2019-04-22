package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class Stores {
    private static Stores instance = null;
    private ArrayList<String> names;
    private  ArrayList<Product> allProducts;
    private ArrayList<Product> filtered;
    private Shop current;
    private boolean currentFlag;
    private boolean filters;
    private Product selected;

    private Stores() {
        names = new ArrayList<String>();
        currentFlag = false;
        allProducts = new ArrayList<Product>();
    }

    public static synchronized Stores getInstance() {
        if (instance == null) {
            instance = new Stores();
        }
        return instance;
    }

    public ArrayList<Product> getAllProducts() {
        return allProducts;
    }

    public void setAllProducts(ArrayList<Product> allProducts) {
        this.allProducts = allProducts;
    }

    public ArrayList<Product> getFiltered() {
        return filtered;
    }

    public void setFiltered(ArrayList<Product> filtered) {
        this.filtered = filtered;
    }

    public boolean isCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(boolean currentFlag) {
        this.currentFlag = currentFlag;
    }

    public boolean isFilters() {
        return filters;
    }

    public void setFilters(boolean filters) {
        this.filters = filters;
    }

    public Shop getCurrent() {
        return current;
    }

    public void setCurrent(Shop current) {
        this.current = current;
    }

    public void setCurrent(String name) {
        this.current = new Shop(name);
    }

    public ArrayList<String> getNames() {
        return names;
    }

    public void setNames(ArrayList<String> names) {
        this.names = names;
    }

    public Product getSelected() {
        return selected;
    }

    public void setSelected(Product selected) {
        this.selected = selected;
    }

    public void selectProduct(int index) {
        if (filters)  {
            selected = filtered.get(index);
        } else {
            selected = allProducts.get(index);
        }

    }
    public void deleteProduct(int index){
        if (filters) {
            selected = filtered.remove(index);
        } else {
            selected = allProducts.remove(index);
        }

    }

    public void updateList(Context c, ListView list) {
        ArrayAdapter adapter = new ArrayAdapter(c,android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    public void updateProducts(Context c, ListView list) {
        CustomAdapter customAdapter;
        if (filters) {
            customAdapter = new CustomAdapter(c, this.filtered);
        } else {
            customAdapter = new CustomAdapter(c, this.allProducts);
        }
        list.setAdapter(customAdapter);
    }

}
