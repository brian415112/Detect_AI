package com.example.detect_ai.Model.Memento;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Environment;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.detect_ai.Model.Bridge.DottedLine;
import com.example.detect_ai.Model.Bridge.Line;
import com.example.detect_ai.Model.Bridge.Shape;
import com.example.detect_ai.Model.Bridge.SolidLine;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class MyCanvasView extends View {

    Context context;
    private final Paint mBitmapPaint;
    private Bitmap background;
    //畫筆
    private Path mPath;
    private final Paint circlePaint;
    private Paint mPaint;
    private final Path circlePath;
    //暫存使用者手指的X,Y座標
    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;
    Originator originator;
    Caretaker caretaker;

    String type = "Solid Line";
    int color = Color.GREEN;

    public MyCanvasView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        this.context = context;

        originator = new Originator();
        caretaker = new Caretaker();

        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG); //background

        //畫點選畫面時顯示的圈圈
        circlePaint = new Paint();
        circlePath = new Path();
        circlePaint.setAntiAlias(true);
        circlePaint.setColor(Color.BLACK);
        circlePaint.setStyle(Paint.Style.STROKE);
        circlePaint.setStrokeJoin(Paint.Join.MITER);
        circlePaint.setStrokeWidth(4f);

        //畫筆初始化
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        initializationPaint(color, type);
    }

    public void initializationPaint(int color, String type){
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(color);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(12);

        setPaint(type);
    }

    public void setPaint(String type){
        this.type = type;

        if(type.equals("Solid Line")){
            //繪製實線
            Shape solidLine = new Line(mPaint, new SolidLine());
            solidLine.changePaint();
        }else {
            //繪製虛線
            Shape dottedLine = new Line(mPaint ,new DottedLine());
            dottedLine.changePaint();
        }
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        //初始化空畫布
    }

    @SuppressLint("DrawAllocation")
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if( background == null){
            background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        }
        Bitmap res = Bitmap.createScaledBitmap(background
                ,getWidth(),getHeight(),true);
        canvas.drawBitmap(res,0,0,mBitmapPaint);


        Log.e("test3","------------------------\n");
        for (int i= 1; i<=caretaker.getLength();i++){
            canvas.drawPath(caretaker.getMemento(i).getPath(),caretaker.getMemento(i).getPaint());
            Log.e("test1",caretaker.getMemento(i).getPath()+" "+caretaker.getMemento(i).getPaint());
            Log.e("test3", String.valueOf(caretaker.getMemento(i).getPaint().getColor()));
        }

        //依據移動路徑畫線
        canvas.drawPath(mPath, mPaint);
        //畫圓圈圈
        canvas.drawPath(circlePath, circlePaint);
    }

    /**
     * 覆寫:偵測使用者觸碰螢幕的事件
     */
    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX(), y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                touch_start(x, y);
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                break;
        }
        invalidate();
        return true;

    }

    /**
     * 觸碰到螢幕時，取得手指的X,Y座標；並順便設定為線的起點
     */
    private void touch_start(float x, float y) {
        mPath.reset();
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    /**
     * 在螢幕上滑動時，不斷刷新移動路徑
     */
    private void touch_move(float x, float y) {
        //取得目前位置與前一點位置的X距離
        float dx = Math.abs(x - mX);
        //取得目前位置與前一點位置的Y距離
        float dy = Math.abs(y - mY);
        //判斷此兩點距離是否有大於預設的最小值，有才把他畫進去
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //畫貝爾茲曲線
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);
            //更新上一點X座標
            mX = x;
            //更新上一點Y座標
            mY = y;

            //消滅上一點時的小圈圈位置
            circlePath.reset();
            //更新小圈圈的位置
            circlePath.addCircle(mX, mY, 30, Path.Direction.CW);
        }
    }

    /**
     * 當使用者放開時，把位置設為終點
     */
    private void touch_up() {
        mPath.lineTo(mX, mY);
        circlePath.reset();

        originator.setPathAndPaint(mPath, mPaint);
        caretaker.addMemento(originator.save());

        mPaint = new Paint(Paint.DITHER_FLAG);
        mPath = new Path();

        initializationPaint(color,type);
    }

    /**
     * 清除所有畫筆內容
     */
    public void clear() {
        setDrawingCacheEnabled(false);
        caretaker = new Caretaker();
        onSizeChanged(getWidth(), getHeight(), getWidth(), getHeight());
        invalidate();
        setDrawingCacheEnabled(true);
    }

    /**
     * 設置接口從外部設置畫筆顏色
     */
    public void setColor(int color) {
        this.color = color;
        mPaint.setColor(color);
    }

    /**
     * 設置接口從外部設置背景圖片
     */
    public void setBackground(Bitmap bitmap) {
        int width = bitmap.getWidth(); //寬
        int height = bitmap.getHeight(); //長
        double ratio;
        int retX, retY;

        if (width >= height) { //橫
            ratio = (double) getHeight() / height;
            background = Bitmap.createScaledBitmap(bitmap
                    , (int) Math.round(width * ratio), getHeight(), true);
        } else { //直
            ratio = (double) getWidth() / width;
            background = Bitmap.createScaledBitmap(bitmap
                    , getWidth(), (int) Math.round(height * ratio), true);
        }

        Bitmap tempBitmap;

        width = background.getWidth(); //寬
        height = background.getHeight(); //長
        retX = width > height ? (width - getWidth()) / 2 : 0;
        retY = width > height ? 0 : (height - getHeight()) / 2;

        tempBitmap = Bitmap.createBitmap(background, retX, retY, getWidth(), getHeight(), null, false);
        background = tempBitmap;

        invalidate();
    }

    public void saveBitmap(String timestamp) {
        if( background == null){
            background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        }
        Bitmap res = Bitmap.createScaledBitmap(background
                ,getWidth(),getHeight(),true);
        Canvas canvas1 = new Canvas(res);

        for (int i= 1; i<=caretaker.getLength();i++){
            canvas1.drawPath(caretaker.getMemento(i).getPath(),caretaker.getMemento(i).getPaint());
        }

        try {
            // 取得外部儲存裝置路徑
            String path = Environment.getExternalStorageDirectory().toString();
            // 開啟檔案
            File file = new File(path, timestamp+".jpeg");
            // 開啟檔案串流
            FileOutputStream out = new FileOutputStream(file);
            // 將 Bitmap壓縮成指定格式的圖片並寫入檔案串流
            res.compress(Bitmap.CompressFormat.JPEG, 90, out);
            // 刷新並關閉檔案串流
            out.flush();
            out.close();

            Toast.makeText(context, "Save successfully", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void uploadImage(String timestamp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();

        if( background == null){
            background = Bitmap.createBitmap(getWidth(), getHeight(), Bitmap.Config.ARGB_8888);
        }
        Bitmap res = Bitmap.createScaledBitmap(background
                ,getWidth(),getHeight(),true);
        Canvas canvas1 = new Canvas(res);

        for (int i= 1; i<=caretaker.getLength();i++){
            canvas1.drawPath(caretaker.getMemento(i).getPath(),caretaker.getMemento(i).getPaint());
        }

        res.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] data = baos.toByteArray();

        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReferenceFromUrl("gs://ai-detect-24afc.appspot.com");
        StorageReference imagesRef = storageRef.child("images/"+timestamp+".jpeg");

        UploadTask uploadTask = imagesRef.putBytes(data);
        Toast.makeText(context, "Uploading", Toast.LENGTH_SHORT).show();

        uploadTask.addOnFailureListener(exception -> {
            Toast.makeText(context, "Upload failed", Toast.LENGTH_SHORT).show();
            // Handle unsuccessful uploads
        }).addOnSuccessListener(taskSnapshot -> Toast.makeText(context, "Upload successfully", Toast.LENGTH_SHORT).show());
    }

    public void Undo(){
        if (caretaker.remove()){
            if(caretaker.getLength() <= 0){
                Toast.makeText(context,"Unable to restore",Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(context,"Unable to restore",Toast.LENGTH_SHORT).show();
        }
        invalidate();
    }
}