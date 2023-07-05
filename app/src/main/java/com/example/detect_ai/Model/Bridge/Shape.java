package com.example.detect_ai.Model.Bridge;

import android.graphics.Paint;

public abstract class Shape {
    protected DrawAPI drawAPI;
    protected Paint paint;

    protected Shape(Paint paint, DrawAPI drawAPI){
        this.drawAPI = drawAPI;
        this.paint = paint;
    }

    public abstract void changePaint();
}
