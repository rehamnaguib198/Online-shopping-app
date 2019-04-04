package com.example.arabtech.onlineshoppingapp;

public class ViewManager {

    private static ViewManager instance = null;
    private Store store;

    private ViewManager() {

    }

    public static synchronized ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


}
