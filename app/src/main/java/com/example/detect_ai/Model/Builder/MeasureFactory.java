package com.example.detect_ai.Model.Builder;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.Timestamp;

import java.util.ArrayList;

public class MeasureFactory extends MeasureBuilder {
    private double bloodOxygen;
    private double pulse;
    private ArrayList<Double> ECG = new ArrayList<>();
    private Timestamp timestamp;

    @Override
    public MeasureBuilder setBloodOxygen(double bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
        return this;
    }

    @Override
    public MeasureBuilder setPulse(double pulse) {
        this.pulse = pulse;
        return this;
    }

    @Override
    public MeasureBuilder setECG(ArrayList<Double> ECG) {
        this.ECG = ECG;
        return this;
    }

    @Override
    public MeasureBuilder setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
        return this;
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    public Measure build(){
        return new Measure(bloodOxygen, pulse, ECG, timestamp);
    }
}
