package com.example.detect_ai.Model.StrategyAndFlyweight;

import android.widget.TextView;

public class ReplyContext {
    ReplyResultStrategy replyResultStrategy;

    public void print(TextView textView, TextView textView1, String string){
        if(replyResultStrategy == null){
            replyResultStrategy = StrategyFactory.getStrategy(false, "#000000");
        }
        replyResultStrategy.execute(textView, textView1, string);
    }

    public void choiceStrategy(boolean b, String color){
        replyResultStrategy = StrategyFactory.getStrategy(b, color);
    }
}
