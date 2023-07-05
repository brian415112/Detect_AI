package com.example.detect_ai.Model.Iterator;

public class ECGaggregate implements Aggregate{
    Double[] ecg;

    public ECGaggregate(Double[] ecg){
        this.ecg = ecg;
    }

    @Override
    public Iterator creteIterator() {
        return new ECGaggregateIterator(ecg);
    }
}
