package com.example.arabtech.onlineshoppingapp;

import android.app.Activity;

public class ViewManager {

    private static ViewManager instance = null;
    private Store store;
    private Activity activity;

    private ViewManager() {

    }

    public static synchronized ViewManager getInstance() {
        if (instance == null) {
            instance = new ViewManager();
        }
        return instance;
    }

    public Activity getActivity() {
        return activity;
    }

    public void setActivity(Activity activity) {
        this.activity = activity;
    }

    public Store getStore() {
        return store;
    }

    public void setStore(Store store) {
        this.store = store;
    }


}
