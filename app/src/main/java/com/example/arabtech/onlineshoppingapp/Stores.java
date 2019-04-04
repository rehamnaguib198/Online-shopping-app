package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class Stores {
    private static Stores instance = null;
    private ArrayList<String> names;
    private Shop current;
    private boolean currentFlag;

    private Stores() {
        names = new ArrayList<String>();
        currentFlag = false;
    }

    public static synchronized Stores getInstance() {
        if (instance == null) {
            instance = new Stores();
        }
        return instance;
    }

    public boolean isCurrentFlag() {
        return currentFlag;
    }

    public void setCurrentFlag(boolean currentFlag) {
        this.currentFlag = currentFlag;
    }

    public Shop getCurrent() {
        return current;
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

    public void updateList(Context c, ListView list) {
        ArrayAdapter adapter = new ArrayAdapter(c,android.R.layout.simple_list_item_1, names);
        list.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
}
