package com.example.arabtech.onlineshoppingapp;

import android.content.Context;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

public class PicassoClient {
    public static void downloadImg(Context context, String url, ImageView img) {
        if (url != null && url.length() > 0) {
            Picasso.with(context).load(url).placeholder(R.drawable.no_image).into(img);
        }
        else{
            Picasso.with(context).load(R.drawable.kitty).into(img);
        }
    }
}
