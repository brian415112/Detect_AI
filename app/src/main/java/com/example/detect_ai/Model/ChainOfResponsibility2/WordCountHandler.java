package com.example.detect_ai.Model.ChainOfResponsibility2;

import android.content.Context;

public interface WordCountHandler {
    void setNextHandler(WordCountHandler wordCountHandler);
    void process(Context context, int word_count);
    String getHandlerName();
}