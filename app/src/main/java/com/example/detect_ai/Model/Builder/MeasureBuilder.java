package com.example.detect_ai.Model.Builder;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public abstract class MeasureBuilder {
    protected Measure measure;

    public abstract MeasureBuilder setBloodOxygen(double bloodOxygen);
    public abstract MeasureBuilder setPulse(double pulse);
    public abstract MeasureBuilder setECG(ArrayList<Double> ECG);
    public abstract MeasureBuilder setTimestamp(Timestamp timestamp);
    public abstract Measure build();
}
