package com.example.detect_ai.Model.StrategyAndFlyweight;

import java.util.HashMap;

public class StrategyFactory {
    private static final HashMap<Boolean, ReplyResultStrategy> strategyMap = new HashMap<>();

    public static ReplyResultStrategy getStrategy(Boolean b, String color) {

        ReplyResultStrategy replyResultStrategy;

        if(b){
            replyResultStrategy = strategyMap.get(true);

            if(replyResultStrategy == null)
            {
                replyResultStrategy = new RepliedStrategy();
                strategyMap.put(true, replyResultStrategy);
            }
        }else{
            replyResultStrategy = strategyMap.get(false);

            if(replyResultStrategy == null)
            {
                replyResultStrategy = new NoReplyStrategy();
                strategyMap.put(false, replyResultStrategy);
            }
        }

        replyResultStrategy.setTimestampColor(color);
        return replyResultStrategy;
    }
}
