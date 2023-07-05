package com.example.detect_ai.Model.FactoryMethod;

public class MyLineChartFactory extends LineChartFactory {

    @Override
    public Line_Chart createLineChart(String id) {
        if(id.equals("Simple")){
            return new SimpleLineChart();
        }else{
            return new SpecialLineChart();
        }
    }
}
