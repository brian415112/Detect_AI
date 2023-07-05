package com.example.detect_ai.Controller.Detect;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RadioGroup;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detect_ai.Model.ChainOfResponsibility.ImageHandler;
import com.example.detect_ai.Model.Memento.MyCanvasView;
import com.example.detect_ai.Model.ChainOfResponsibility.bmpFileImageHandler;
import com.example.detect_ai.Model.ChainOfResponsibility.gifFileImageHandler;
import com.example.detect_ai.Model.ChainOfResponsibility.jpegFileImageHandler;
import com.example.detect_ai.Model.ChainOfResponsibility.pngFileImageHandler;
import com.example.detect_ai.R;

public class Diary_After_Measurement extends AppCompatActivity {
    Button btClear, btSet, btSave, btUpload, btUndo;
    MyCanvasView canvasView;
    RadioGroup radioGroup, radioGroup2;

    Context context;

    @SuppressLint("NonConstantResourceId")
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_canvas);
        context = this;

        Bundle bundle = this.getIntent().getExtras();
        String timestamp = bundle.getString("timestamp");

        FindViewById();

        btClear.setOnClickListener(v-> canvasView.clear());
        btSave.setOnClickListener(v-> canvasView.saveBitmap(timestamp));
        btUndo.setOnClickListener(v -> canvasView.Undo());
        btSet.setOnClickListener(view -> {
        /*在Activity Action裡面有一個“ACTION_GET_CONTENT”字串常量，
        該常量讓使用者選擇特定型別的資料，並返回該資料的URI.我們利用該常量，
        然後設定型別為“image/*”，就可獲得Android手機內的所有image。*/
            Intent intent = new Intent();

            /* 開啟Pictures畫面Type設定為image */
            intent.setType("image/*");

            /* 使用Intent.ACTION_GET_CONTENT這個Action */
            intent.setAction(Intent.ACTION_GET_CONTENT);

            /* 取得相片後返回原畫面 */
            startActivityForResult(intent, 1);
        });

        radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.radioGroupButton0:
                    canvasView.setPaint("Solid Line");
                    break;
                case R.id.radioGroupButton1:
                    canvasView.setPaint("Dotted Line");
                    break;
                default:
                    break;
            }
        });

        radioGroup2.setOnCheckedChangeListener((group, checkedId) -> {
            switch (checkedId){
                case R.id.radioGroupButton_red:
                    canvasView.setColor(Color.RED);
                    break;
                case R.id.radioGroupButton_blue:
                    canvasView.setColor(Color.BLUE);
                    break;
                case R.id.radioGroupButton_green:
                    canvasView.setColor(Color.GREEN);
                    break;
                default:
                    break;
            }
        });

        btUpload.setOnClickListener(v -> canvasView.uploadImage(timestamp));

        String[] permissions = new String[1];
        permissions[0]=Manifest.permission.WRITE_EXTERNAL_STORAGE;
        requestPermissions(permissions,1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            Uri uri = data.getData();
            ContentResolver cr = this.getContentResolver();

            ImageHandler gifFileImageHandler = new gifFileImageHandler(canvasView, uri, cr, context);
            ImageHandler jpegFileImageHandler = new jpegFileImageHandler(canvasView, uri, cr, context);
            ImageHandler pngFileImageHandler = new pngFileImageHandler(canvasView, uri, cr, context);
            ImageHandler bmpFileImageHandler = new bmpFileImageHandler(canvasView, uri, cr, context);

            gifFileImageHandler.setNextHandler(jpegFileImageHandler);
            jpegFileImageHandler.setNextHandler(pngFileImageHandler);
            pngFileImageHandler.setNextHandler(bmpFileImageHandler);

            gifFileImageHandler.process();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public void FindViewById(){
        canvasView = findViewById(R.id.myCanvasView);
        btSet = findViewById(R.id.button_Set);
        btSave = findViewById(R.id.button_Save);
        btClear = findViewById(R.id.button_Clear);
        btUpload = findViewById(R.id.button_Upload);
        btUndo = findViewById(R.id.button_Undo);
        radioGroup = findViewById(R.id.radioGroup1);
        radioGroup2 = findViewById(R.id.radioGroup2);
    }
}