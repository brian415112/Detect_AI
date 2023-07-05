package com.example.detect_ai.Model.Proxy;

import android.content.Context;
import android.widget.ImageView;

import com.example.detect_ai.R;

public class ProxyImage implements Image{
    private RealImage realImage;
    String timestamp;
    Context context;
    ImageView imageView;

    @Override
    public void display() {
        imageView.setImageResource(R.drawable.loading);

        if(realImage == null){
            realImage = new RealImage(timestamp, context, imageView);
        }
        realImage.display();
    }

    public ProxyImage(String timestamp, Context context, ImageView imageView){
        this.timestamp = timestamp;
        this.context = context;
        this.imageView = imageView;
    }
}
