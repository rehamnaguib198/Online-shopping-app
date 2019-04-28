package com.example.arabtech.onlineshoppingapp;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


/**
 * A simple {@link Fragment} subclass.
 */
public class ShowProduct extends Fragment {

    private TextView storeName;
    private TextView description;
    private TextView size;
    private  TextView color;
    private TextView price;
    private TextView notes;
    private TextView offers;
    private ImageView logo;
    private ImageView img1;
    private ImageView img2;
    private ImageView img3;
    private ImageView img4;
    private Stores stores;
    private Shop current;
    private Product selected;

    public ShowProduct() {
        // Required empty public constructor
    }

    public void setSelected(Product selected) {
        this.selected = selected;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_show_product, container, false);
        storeName = view.findViewById(R.id.storeName);
        description = view.findViewById(R.id.title);
        size = view.findViewById(R.id.size);
        color = view.findViewById(R.id.color);
        price = view.findViewById(R.id.price);
        notes = view.findViewById(R.id.notes);
        offers = view.findViewById(R.id.offers);
        logo = view.findViewById(R.id.imgView_logo);
        img1 = view.findViewById(R.id.image1);
        img2 = view.findViewById(R.id.image2);
        img3 = view.findViewById(R.id.image3);
        img4 = view.findViewById(R.id.image4);

        if (selected != null) {
            current = selected.getShop();
        } else {
            stores = Stores.getInstance();
            if (stores.isCurrentFlag()) {
                current = stores.getCurrent();
                selected = current.getSelected();
            } else {
                current = stores.getSelected().getShop();
                selected = stores.getSelected();
            }
        }
        storeName.setText(current.getName());
        if (current.getLogo().equals("noImage")) {
            logo.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(getContext(), current.getLogo(), logo);
        }

        description.setText(selected.getDescription());
        size.setText(selected.getSize());
        color.setText(selected.getColor());
        price.setText(selected.getPrice());
        offers.setText(selected.getOffers());
        notes.setText(selected.getNotes());
        if (selected.getImg1().equals("noImage")) {
            img1.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(getContext(),selected.getImg1(),img1);
        }

        /*if (selected.getImg2().equals("noImage")) {
            img2.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(getContext(),selected.getImg2(),img2);
        }

        if (selected.getImg3().equals("noImage")) {
            img3.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(getContext(),selected.getImg3(),img3);
        }

        if (selected.getImg4().equals("noImage")) {
            img4.setImageResource(R.drawable.no_image);
        } else {
            PicassoClient.downloadImg(getContext(),selected.getImg4(),img4);
        }*/

        return view;
    }


}
