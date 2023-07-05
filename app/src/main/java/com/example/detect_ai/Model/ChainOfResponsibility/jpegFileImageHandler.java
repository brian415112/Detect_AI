package com.example.detect_ai.Model.ChainOfResponsibility;

import android.content.ContentResolver;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.util.Log;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.example.detect_ai.Model.Memento.MyCanvasView;

import java.io.FileNotFoundException;

public class jpegFileImageHandler implements ImageHandler {
    private ImageHandler imageHandler;
    Uri uri;
    MyCanvasView canvasView;
    String type;
    ContentResolver cr;
    Context context;

    public jpegFileImageHandler(MyCanvasView canvasView, Uri uri, ContentResolver cr, Context context){
        this.canvasView = canvasView;
        this.uri = uri;
        this.cr = cr;
        this.context = context;
        type = getFileExtension(uri);
    }

    @Override
    public void setNextHandler(ImageHandler imageHandler) {
        this.imageHandler = imageHandler;
    }

    @Override
    public void process() {
        if (type.equals("jpeg")|type.equals("jpg")){
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(cr.openInputStream(uri));
                /* 將Bitmap設定到ImageView */
                canvasView.setBackground(bitmap);
            } catch (FileNotFoundException e) {
                Log.e("Exception", e.getMessage(), e);
            }
        }else if (imageHandler != null){
            Log.i("NextHandler", imageHandler.getHandlerName());
            imageHandler.process();
        }else{
            Toast.makeText(context,"File not supported",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public String getHandlerName() {
        return "jpegFileHandler";
    }

    private String getFileExtension(Uri uri) {
        MimeTypeMap mimeTypeMap = MimeTypeMap.getSingleton();
        return mimeTypeMap.getExtensionFromMimeType(cr.getType(uri));
    }
}
