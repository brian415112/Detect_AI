package com.example.detect_ai.Model.Command;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.detect_ai.Model.Proxy.Image;
import com.example.detect_ai.Model.Proxy.ProxyImage;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class DB_Receiver {
    String timestamp;
    Context context;
    ImageView imageView;
    StorageReference storageReference;

    public DB_Receiver(String timestamp, Context context, ImageView imageView){
        this.timestamp = timestamp;
        this.context = context;
        this.imageView = imageView;
        storageReference = FirebaseStorage.getInstance().getReference();
    }

    public void Open(){
        Image image = new ProxyImage(timestamp, context, imageView);
        image.display();
    }

    public void Delete(){

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();

        // Create a reference to the file to delete
        StorageReference desertRef = storageReference.child("images/" + timestamp + ".jpeg");

        // Delete the file
        desertRef.delete().addOnSuccessListener(aVoid ->
                Toast.makeText(context,"Successfully deleted",Toast.LENGTH_SHORT).show())
                .addOnFailureListener(exception ->
                        Toast.makeText(context,"Failed to delete",Toast.LENGTH_SHORT).show());
    }

    public void Save(){

        storageReference.child("images/" + timestamp + ".jpeg").getBytes(Long.MAX_VALUE)
                .addOnSuccessListener(bytes -> {
                    Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);

                    try {
                        // 取得外部儲存裝置路徑
                        String path = Environment.getExternalStorageDirectory().toString();
                        // 開啟檔案
                        File file = new File(path, timestamp+".jpeg");
                        // 開啟檔案串流
                        FileOutputStream out = new FileOutputStream(file);
                        // 將 Bitmap壓縮成指定格式的圖片並寫入檔案串流
                        bmp.compress(Bitmap.CompressFormat.JPEG, 90, out);
                        // 刷新並關閉檔案串流
                        out.flush();
                        out.close();

                        Toast.makeText(context, "Saved successfully", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

        }).addOnFailureListener(exception ->
                Toast.makeText(context,"Save failed",Toast.LENGTH_SHORT).show());
    }
}
