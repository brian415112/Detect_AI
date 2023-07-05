package com.example.detect_ai.Model.StrategyAndFlyweight;

import android.widget.TextView;

public interface ReplyResultStrategy {
    void execute(TextView textView, TextView textView1, String string);
    void setTimestampColor(String color);
}
