package com.example.arabtech.onlineshoppingapp;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.UUID;
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
    private Shop shop=null;
    private Button deleteItem;

    public CustomAdapter(Context context, ArrayList<Product> products) {
        this.context = context;
        this.products = products;
    }
    public CustomAdapter(Context context, ArrayList<Product> products,Shop shop){
        this.context = context;
        this.products = products;
        this.shop=shop;
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
            if(shop!=null){
                view=inflater.inflate(R.layout.my_store_row_feed,viewGroup,false);
            } else{
                view=inflater.inflate(R.layout.row_feed,viewGroup,false);
            }
        }
        name=view.findViewById(R.id.name);
        time=view.findViewById(R.id.time);
        description=view.findViewById(R.id.description);
        proPic=view.findViewById(R.id.imgView_proPic);
        post=view.findViewById(R.id.imgView_postPic);
        showItem = view.findViewById(R.id.showButton);
        if(shop!=null){
            deleteItem=view.findViewById(R.id.deleteButton);
            name.setText(shop.getName());
            if (shop.getLogo().equals("noImage")) {
                proPic.setImageResource(R.drawable.no_image);
            } else {
                PicassoClient.downloadImg(context, shop.getLogo(), proPic);
            }
        } else {
            final Stores stores = Stores.getInstance();
            if (stores.isCurrentFlag()) {
                name.setText(stores.getCurrent().getName());
                if (stores.getCurrent().getLogo().equals("noImage")) {
                    proPic.setImageResource(R.drawable.no_image);
                } else {
                    PicassoClient.downloadImg(context, stores.getCurrent().getLogo(), proPic);
                }
            } else {
                name.setText(products.get(i).getShop().getName());
                if (products.get(i).getShop().getLogo().equals("noImage")) {
                    proPic.setImageResource(R.drawable.no_image);
                } else {
                    PicassoClient.downloadImg(context, products.get(i).getShop().getLogo(), proPic);
                }
            }
        }

        time.setText(products.get(i).getTime());
        description.setText(products.get(i).getDescription());
        if (products.get(i).getImg1().equals("noImage")) {
            post.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(context, products.get(i).getImg1(), post);
        }


        showItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                View parentRow = (View) v.getParent();
                ListView list = (ListView) parentRow.getParent();
                int index = list.getPositionForView(parentRow);
                if (shop != null) {
                    shop.selectProduct(index);
                    shop.getSelected().show(context);
                }
                else{
                    final Stores stores = Stores.getInstance();
                    if (stores.isCurrentFlag()) {
                        stores.getCurrent().selectProduct(index);
                        stores.getCurrent().getSelected().show(context);
                    } else {
                        stores.selectProduct(index);
                        stores.getSelected().show(context);
                    }
                }
                ViewManager viewManager = ViewManager.getInstance();
                viewManager.getStore().changeFragment(new ShowProduct());
            }
        });
        if (shop!=null) {
            deleteItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    View parentRow = (View) v.getParent();
                    ListView list = (ListView) parentRow.getParent();
                    int index = list.getPositionForView(parentRow);
                    String myProduct="";
                    if (shop != null) {
                        myProduct=shop.getProducts().get(index).getId();
                        shop.deleteProduct(index);
                    }
                    /*else{
                        final Stores stores = Stores.getInstance();
                        if (stores.isCurrentFlag()) {
                            myProduct=stores.getCurrent().getProducts().get(index).getId();
                            stores.getCurrent().deleteProduct(index);
                        } else {
                            myProduct=stores.getAllProducts().get(index).getId();
                            stores.deleteProduct(index);
                        }
                    }*/

                    FirebaseDatabase.getInstance().getReference().child("Stores").child(name.getText().toString()).child("Products").child(myProduct).removeValue();
                    ViewManager viewManager = ViewManager.getInstance();
                    viewManager.getStore().changeFragment(new ShowProduct());
                }
            });
        }

        return view;
    }
}
