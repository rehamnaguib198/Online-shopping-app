package com.example.arabtech.onlineshoppingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.zip.Inflater;

public class CustomAdapter extends BaseAdapter {
    private Context context;
    private ArrayList<Product> products;
    private LayoutInflater inflater;
    private TextView name;
    private TextView time;
    private TextView description;
    private ImageView proPic;
    private ImageView post;
    private Button showItem;

    public CustomAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    @Override
    public int getCount() {
        return products.size();
    }

    @Override
    public Object getItem(int i) {
        return products.get(i);
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
        name=view.findViewById(R.id.name);
        time=view.findViewById(R.id.time);
        description=view.findViewById(R.id.description);
        proPic=view.findViewById(R.id.imgView_proPic);
        post=view.findViewById(R.id.imgView_postPic);
        showItem = view.findViewById(R.id.showButton);
        final Stores stores = Stores.getInstance();

        name.setText(stores.getCurrent().getName());
        time.setText(products.get(i).getTime());
        description.setText(products.get(i).getDescription());
        if (products.get(i).getImg1().equals("noImage")) {
            post.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(context,products.get(i).getImg1(),post);
        }
        if (stores.getCurrent().getLogo().equals("noImage")) {
            proPic.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(context, stores.getCurrent().getLogo(), proPic);
        }

        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ListView list = (ListView) parentRow.getParent();
                int index = list.getPositionForView(parentRow);
                stores.getCurrent().selectProduct(index);
                stores.getCurrent().getSelected().show(context);

                ViewManager viewManager = ViewManager.getInstance();
                viewManager.getStore().changeFragment(new ShowProduct());
            }
        });

        /*Store store=new Store(view);
        store.name.setText(models.get(i).getName());
        store.description.setText(models.get(i).getDescription());
        store.time.setText(models.get(i).getTime());
        //PicassoClient.downloadImg(context,models.get(i).getProPic(),store.proPic);
        //PicassoClient.downloadImg(context,models.get(i).getPost(),store.post);*/
        return view;
    }
}
