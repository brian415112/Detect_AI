package com.example.detect_ai.Model.FactoryMethod;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SimpleLineChart implements Line_Chart{
    ArrayList<Entry> values;
    LineChart lineChart;

    @Override
    public void setDataSource(ArrayList<Entry> values) {
        this.values = values;
    }

    @Override
    public void draw(LineChart lineChart) {
        this.lineChart = lineChart;

        lineDataSet();
        Set_X_Axis_Style();
        Set_Y_Axis_Style();
        SetChartStyle();

    }

    @Override
    public void lineDataSet(){
        LineDataSet set;
        set = new LineDataSet(values, "ECG");
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER); //類型為折線
        set.setColor(Color.GREEN);                  //線的顏色
        set.setLineWidth(1.5f);                     //線寬
        set.setDrawCircles(false);                  //不顯示相應座標點的小圓圈(預設顯示)
        set.setDrawValues(false);                   //不顯示座標點對應Y軸的數字(預設顯示)

        LineData data = new LineData(set);
        lineChart.setData(data);                    //一定要放在最後
        lineChart.invalidate();                     //繪製圖表
    }

    @Override
    public void Set_X_Axis_Style(){
        XAxis xAxis = lineChart.getXAxis();
        xAxis.setEnabled(false);
    }

    @Override
    public void Set_Y_Axis_Style(){
        YAxis rightAxis = lineChart.getAxisRight();//獲取右側的軸線
        rightAxis.setEnabled(false);//不顯示右側Y軸
        YAxis leftAxis = lineChart.getAxisLeft();//獲取左側的軸線
        leftAxis.setEnabled(false);//不顯示左側Y軸
    }

    @Override
    public void SetChartStyle(){
        Description description = lineChart.getDescription();
        description.setEnabled(false);//不顯示Description Label (預設顯示)

        //左下方Legend：圖例數據資料
        Legend legend = lineChart.getLegend();
        legend.setEnabled(false);//不顯示圖例 (預設顯示)
    }
}
