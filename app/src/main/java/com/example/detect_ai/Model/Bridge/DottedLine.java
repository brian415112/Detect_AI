package com.example.detect_ai.Model.Bridge;

import android.graphics.DashPathEffect;
import android.graphics.Paint;

public class DottedLine implements DrawAPI{

    @Override
    public void changePaint(Paint paint) {
        paint.setPathEffect (new DashPathEffect(new float[ ]{10,40},0) ) ;
    }
}
