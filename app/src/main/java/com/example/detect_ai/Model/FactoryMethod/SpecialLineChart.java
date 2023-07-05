package com.example.detect_ai.Model.FactoryMethod;

import android.graphics.Color;

import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.util.ArrayList;

public class SpecialLineChart implements Line_Chart{
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
        xAxis.setEnabled(true);

        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);//X軸標籤顯示位置(預設顯示在上方，分為上方內/外側、下方內/外側及上下同時顯示)
        xAxis.setTextColor(Color.GRAY);//X軸標籤顏色
        xAxis.setTextSize(12);//X軸標籤大小

        xAxis.setLabelCount(10);//X軸標籤個數
        xAxis.setSpaceMin(0.5f);//折線起點距離左側Y軸距離
        xAxis.setSpaceMax(0.5f);//折線終點距離右側Y軸距離

        xAxis.setDrawGridLines(false);//不顯示每個座標點對應X軸的線 (預設顯示)
    }

    @Override
    public void Set_Y_Axis_Style(){
        YAxis leftAxis = lineChart.getAxisLeft();//獲取左側的軸線
        leftAxis.setEnabled(true);//顯示左側Y軸

        leftAxis.setLabelCount(4);//Y軸標籤個數
        leftAxis.setTextColor(Color.GRAY);//Y軸標籤顏色
        leftAxis.setTextSize(12);//Y軸標籤大小
        leftAxis.enableGridDashedLine(5f, 5f, 0f);//格線以虛線顯示，可設定虛線長度、間距等
    }

    @Override
    public void SetChartStyle(){
        //左下方Legend：圖例數據資料
        Legend legend = lineChart.getLegend();
        legend.setEnabled(true);//不顯示圖例 (預設顯示)
    }
}
