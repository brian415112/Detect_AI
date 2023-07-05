package com.example.detect_ai.Model.ObserverAndVisitor;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.TextView;

import com.example.detect_ai.Model.FactoryMethod.LineChartFactory;
import com.example.detect_ai.Model.FactoryMethod.MyLineChartFactory;

public class SpecialVisitor implements Visitor{
    @Override
    public void visit(ECG ecg) {
        LineChartFactory myLineChartFactory = new MyLineChartFactory();
        myLineChartFactory.drawLineChart(ecg.getLineChart(), ecg.getMeasure_ECG(), "Special");
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void visit(Pulse pulse) {
        TextView textView = pulse.getTextView();
        textView.setText(pulse.getPulse() + " (times/min)");

        boolean pulse_judgment = pulse.getPulse()<85 && pulse.getPulse()>55;

        if(pulse_judgment){
            textView.setTextColor(Color.GREEN);
        }else {
            textView.setTextColor(Color.RED);
        }

    }

    @SuppressLint("SetTextI18n")
    @Override
    public void visit(BloodOxygen bloodOxygen) {
        TextView textView = bloodOxygen.getTextView();
        bloodOxygen.getTextView().setText(bloodOxygen.getBloodOxygen() + " %");

        boolean bloodOxygen_judgment = bloodOxygen.getBloodOxygen()>95;

        if(bloodOxygen_judgment){
            textView.setTextColor(Color.GREEN);
        }else {
            textView.setTextColor(Color.RED);
        }

    }
}
