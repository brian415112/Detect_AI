package com.example.detect_ai.Model.Iterator;

public class ECGaggregateIterator implements Iterator{
    private final Double[] ecg;
    private int position;

    public ECGaggregateIterator(Double[] ecg){
        this.ecg = ecg;
        this.position = 0;
    }

    @Override
    public boolean hasNext() {
        return position < ecg.length && ecg[position] != null;
    }

    @Override
    public Object next() {
        Double single_ecg = ecg[position];
        position++;
        return single_ecg;
    }
}
