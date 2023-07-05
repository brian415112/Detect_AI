package com.example.detect_ai.Model.Proxy;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class RealImage implements Image, DBconnect{
    String timestamp;
    Context context;
    ImageView image;
    StorageReference storageReference;

    RealImage(String timestamp, Context context, ImageView imageView){
        this.timestamp = timestamp;
        this.context = context;
        this.image = imageView;
        connectDB();
    }

    @Override
    public void display() {
        storageReference.child("images/" + timestamp + ".jpeg").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    Toast.makeText(context,"Successfully loaded",Toast.LENGTH_SHORT).show();
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
                    image.setImageBitmap(Bitmap.createScaledBitmap(bmp, image.getWidth(), image.getHeight(), false));
                }).addOnFailureListener(exception -> {
                    Toast.makeText(context,"Failed to load, maybe you did not upload the diary.",Toast.LENGTH_LONG).show();
                    ((Activity)context).finish();
        });
    }

    public void connectDB(){
        // Reference to an image file in Cloud Storage
        storageReference = FirebaseStorage.getInstance().getReference();
    }
}
