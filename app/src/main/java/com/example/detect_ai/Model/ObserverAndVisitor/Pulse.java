package com.example.detect_ai.Model.ObserverAndVisitor;

import android.annotation.SuppressLint;
import android.widget.TextView;

import com.example.detect_ai.Model.Builder.Measure;

public class Pulse implements Observer{
    private final TextView textView;
    private int pulse;

    public Pulse(TextView textView){
        this.textView = textView;
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    /**
     * Observer(measureData)的更新通知
     */
    @SuppressLint("SetTextI18n")
    @Override
    public void update(Measure measure) {
        pulse = (int)measure.getPulse();
    }

    public int getPulse() {
        return pulse;
    }

    public TextView getTextView(){
        return textView;
    }
}