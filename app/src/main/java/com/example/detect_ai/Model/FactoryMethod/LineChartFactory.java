package com.example.detect_ai.Model.FactoryMethod;

import com.example.detect_ai.Model.Iterator.ECGaggregate;
import com.example.detect_ai.Model.Iterator.Iterator;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public abstract class LineChartFactory {
    Line_Chart lineChart;
    ArrayList<Entry> values = new ArrayList<>();

    public void drawLineChart(LineChart l, Double[] ECG, String id){
        lineChart = createLineChart(id);
        setDataSource(ECG);
        lineChart.draw(l);
    }

    public void setDataSource(Double[] ECG){
        ECGaggregate ECGaggregate = new ECGaggregate(ECG);
        Iterator uploadAggregateIterator = ECGaggregate.creteIterator();

        int i = 0;
        while (uploadAggregateIterator.hasNext()){
            double temp = (double) uploadAggregateIterator.next();
            float f = (float) temp;
            values.add(new Entry(i,f));
            i++;
        }

        lineChart.setDataSource(values);
    }

    public abstract Line_Chart createLineChart(String id);
}
