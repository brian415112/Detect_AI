package com.example.detect_ai.Model.Memento;

import android.graphics.Paint;
import android.graphics.Path;

public class Memento {
    private final Path path;
    private final Paint paint;

    public Memento(Path path,Paint paint){
        this.path = path;
        this.paint = paint;
    }

    public Path getPath(){
        return path;
    }

    public Paint getPaint(){
        return paint;
    }
}
