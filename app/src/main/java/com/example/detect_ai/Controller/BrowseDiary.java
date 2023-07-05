package com.example.detect_ai.Controller;

import android.Manifest;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detect_ai.Model.Command.DB_Receiver;
import com.example.detect_ai.Model.Command.DeleteImageOperation;
import com.example.detect_ai.Model.Command.ImageFileOperation;
import com.example.detect_ai.Model.Command.Invoker;
import com.example.detect_ai.Model.Command.OpenImageOperation;
import com.example.detect_ai.Model.Command.SaveImageOperation;
import com.example.detect_ai.R;

public class BrowseDiary extends AppCompatActivity {

    Button button_save, button_delete;
    ImageView imageView;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_diary);

        FindViewById();

        Intent intent = getIntent();
        String timestamp = intent.getStringExtra("timestamp");

        String[] permissions = new String[1];
        permissions[0]= Manifest.permission.WRITE_EXTERNAL_STORAGE;
        requestPermissions(permissions,1);

        DB_Receiver db_receiver = new DB_Receiver(timestamp, this, imageView);
        ImageFileOperation deleteImageOperation = new DeleteImageOperation(db_receiver);
        ImageFileOperation saveImageOperation = new SaveImageOperation(db_receiver);
        ImageFileOperation openImageOperation = new OpenImageOperation(db_receiver);

        Invoker invoker = new Invoker();
        invoker.setOperation(openImageOperation);
        invoker.notifyReceiver();

        button_delete.setOnClickListener(v -> {
            invoker.setOperation(deleteImageOperation);
            invoker.notifyReceiver();
        });

        button_save.setOnClickListener(v -> {
            invoker.setOperation(saveImageOperation);
            invoker.notifyReceiver();
        });
    }

    private void FindViewById(){
        imageView = findViewById(R.id.imageView);
        button_delete = findViewById(R.id.button_delete);
        button_save = findViewById(R.id.button_saveImage);
    }
}
