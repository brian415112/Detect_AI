package com.example.detect_ai.Model.ObserverAndVisitor;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.example.detect_ai.Model.Builder.Measure;
import com.github.mikephil.charting.charts.LineChart;

public class ECG implements Observer{
    private final LineChart lineChart;
    private Double[] measure_ECG;

    public ECG(LineChart lineChart){
        this.lineChart = lineChart;
    }

    public void accept(Visitor v){
        v.visit(this);
    }

    /**
     * Observer(News)的更新通知
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void update(Measure measure) {
        measure_ECG = measure.getECG().toArray(new Double[0]);
    }

    public Double[] getMeasure_ECG() {
        return measure_ECG;
    }

    public LineChart getLineChart(){
        return lineChart;
    }
}
