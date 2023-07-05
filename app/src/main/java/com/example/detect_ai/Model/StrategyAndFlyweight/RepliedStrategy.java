package com.example.detect_ai.Model.StrategyAndFlyweight;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.widget.TextView;

public class RepliedStrategy implements ReplyResultStrategy{
    String TimestampColor;

    @SuppressLint({"SetTextI18n", "ResourceAsColor"})
    @Override
    public void execute(TextView textView, TextView textView1, String string) {
        textView1.setTextColor(Color.parseColor("#000000"));
        textView1.setText("Replied");

        textView.setTextColor(Color.parseColor(TimestampColor));
        textView.setText(string);
    }

    @Override
    public void setTimestampColor(String color) {
        TimestampColor = color;
    }
}
