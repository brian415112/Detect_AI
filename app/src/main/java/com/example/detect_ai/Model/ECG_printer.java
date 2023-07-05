package com.example.detect_ai.Model;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import java.util.ArrayList;

public class ECG_printer extends View {
    private float mWidth = 0,mHeight = 0;   //圖紙大小
    private Paint mLinePaint;   //畫筆
    private Paint mWavePaint;
    private Path mPath;         //畫圖路径
    private final ArrayList<Double> refreshList = new ArrayList<>();    //數據
    private int row;            //背景網格的行數和列數
    private final int mWaveLineColor = Color.parseColor("#EE4000");
    private final int GRID_SMALL_WIDTH = 10;
    private final int GRID_BIG_WIDTH = 50;
    private int xSmallNum,ySmallNum,xBigNum,yBigNum;
    private final int mWaveSmallLineColor = Color.parseColor("#092100");
    private final int mWaveBigLineColor = Color.parseColor("#1b4200");

    public ECG_printer(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ECG_printer(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    public ECG_printer(Context context) {
        super(context);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldW, int oldH) {
        mWidth = w;
        mHeight = h;
        super.onSizeChanged(w, h, oldW, oldH);
    }

    private void init() {
        mLinePaint = new Paint();
        mLinePaint.setStyle(Paint.Style.STROKE);
        int GRID_LINE_WIDTH = 10;
        mLinePaint.setStrokeWidth(GRID_LINE_WIDTH);
        mLinePaint.setAntiAlias(true);

        mWavePaint = new Paint();
        mWavePaint.setStyle(Paint.Style.STROKE);
        mWavePaint.setColor(mWaveLineColor);
        float WAVE_LINE_STROKE_WIDTH = 2;
        mWavePaint.setStrokeWidth(WAVE_LINE_STROKE_WIDTH);
        mWavePaint.setAntiAlias(true);

        mPath = new Path();
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        mWidth = getMeasuredWidth();    //獲取view的寬
        mHeight = getMeasuredHeight();  //獲取view的高

        row = (int) (mWidth/(GRID_SMALL_WIDTH)); //獲取行數

        xSmallNum = (int) (mHeight/GRID_SMALL_WIDTH);
        ySmallNum = (int) (mWidth/GRID_SMALL_WIDTH);

        xBigNum = (int) (mHeight/GRID_BIG_WIDTH);
        yBigNum = (int) (mWidth/GRID_BIG_WIDTH);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        drawGrid(canvas);
        drawWaveLine(canvas);
    }

    private void drawWaveLine(Canvas canvas) {
        if(refreshList.size()<=0){
            return;
        }
        mPath.reset();
        mPath.moveTo(0f,mHeight/2);
        for (int i = 0;i<refreshList.size();i++){
            float nowX = i * GRID_SMALL_WIDTH;
            double dataValue = refreshList.get(i);
            float MAX_VALUE = 2500;
            if(dataValue>0){
                if(dataValue > MAX_VALUE * 0.8){
                    dataValue = MAX_VALUE * 0.8f;
                }
            }else {
                if(dataValue< -MAX_VALUE * 0.8){
                    dataValue = -MAX_VALUE * 0.8f;
                }
            }
            //目前的xy坐標
            float nowY = (float) (mHeight / 2 + dataValue * (mHeight / (MAX_VALUE * 2)));
            System.out.println(nowX +" "+ nowY);
            mPath.lineTo(nowX, nowY);
        }
        canvas.drawPath(mPath, mWavePaint);
        if(refreshList.size()>row){
            refreshList.remove(0);
        }
    }

    private void drawGrid(Canvas canvas){

        int mBackGroundColor = Color.BLACK;
        canvas.drawColor(mBackGroundColor);
        mLinePaint.setColor(mWaveSmallLineColor);
        for(int i = 0;i < xSmallNum + 1;i++){
            canvas.drawLine(0,i*GRID_SMALL_WIDTH,
                    mWidth, i*GRID_SMALL_WIDTH, mLinePaint);
        }
        for(int i = 0;i < ySmallNum+1;i++){
            canvas.drawLine(i*GRID_SMALL_WIDTH,0,
                    i*GRID_SMALL_WIDTH,mHeight, mLinePaint);
        }
        mLinePaint.setColor(mWaveBigLineColor);
        for(int i = 0;i < xBigNum + 1;i++){
            canvas.drawLine(0,i*GRID_BIG_WIDTH,
                    mWidth, i*GRID_BIG_WIDTH, mLinePaint);
        }
        for(int i = 0;i < yBigNum+1;i++){
            canvas.drawLine(i*GRID_BIG_WIDTH,0,
                    i*GRID_BIG_WIDTH,mHeight, mLinePaint);
        }
    }

    public void showLine(double line) {
        refreshList.add(line);
        postInvalidate();
    }
}
