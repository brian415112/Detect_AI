package com.example.detect_ai.Model.ObserverAndVisitor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.TextView;

import com.example.detect_ai.Model.FactoryMethod.LineChartFactory;
import com.example.detect_ai.Model.FactoryMethod.MyLineChartFactory;

public class SimpleVisitor implements Visitor{
    @Override
    public void visit(ECG ecg) {
        LineChartFactory myLineChartFactory = new MyLineChartFactory();
        myLineChartFactory.drawLineChart(ecg.getLineChart(), ecg.getMeasure_ECG(), "Simple");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void visit(Pulse pulse) {
        TextView textView = pulse.getTextView();
        textView.setText(pulse.getPulse() + " (times/min)");
        textView.setTextColor(Color.BLACK);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void visit(BloodOxygen bloodOxygen) {
        TextView textView = bloodOxygen.getTextView();
        textView.setText(bloodOxygen.getBloodOxygen() + " %");
        textView.setTextColor(Color.BLACK);
    }
}
