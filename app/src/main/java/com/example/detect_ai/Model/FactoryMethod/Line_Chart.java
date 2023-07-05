package com.example.detect_ai.Model.FactoryMethod;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;

import java.util.ArrayList;

public interface Line_Chart {
    void draw(LineChart lineChart);
    void setDataSource(ArrayList<Entry> values);
    void lineDataSet();
    void Set_X_Axis_Style();
    void Set_Y_Axis_Style();
    void SetChartStyle();
}
