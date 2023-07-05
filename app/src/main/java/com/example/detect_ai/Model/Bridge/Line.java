package com.example.detect_ai.Model.Bridge;

import android.graphics.Paint;

public class Line extends Shape{

    public Line(Paint paint, DrawAPI drawAPI) {
        super(paint, drawAPI);
    }

    @Override
    public void changePaint() {
        drawAPI.changePaint(paint);
    }
}