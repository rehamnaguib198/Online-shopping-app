package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<Model> models;
    LayoutInflater inflater;
    public CustomAdapter(Context context, ArrayList<Model> models) {
        this.context = context;
        this.models = models;
    }
    @Override
    public int getCount() {
        return models.size();
    }

    @Override
    public Object getItem(int i) {
        return models.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if(inflater==null){
            inflater=(LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }
        if(view==null){
            view=inflater.inflate(R.layout.row_feed,viewGroup,false);
        }
        Store store=new Store(view);
        store.name.setText(models.get(i).getName());
        store.description.setText(models.get(i).getDescription());
        store.time.setText(models.get(i).getTime());
        PicassoClient.downloadImg(context,models.get(i).getProPic(),store.proPic);
        PicassoClient.downloadImg(context,models.get(i).getPost(),store.post);
        return view;
    }
}
