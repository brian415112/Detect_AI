package com.example.detect_ai.Model.Memento;

import android.graphics.Paint;
import android.graphics.Path;

public class Originator {
    private Path path;
    private Paint paint;

    public void setPathAndPaint(Path path,Paint paint){
        this.path = path;
        this.paint = paint;
    }

    public Memento save(){
        return new Memento(path, paint);
    }

}
