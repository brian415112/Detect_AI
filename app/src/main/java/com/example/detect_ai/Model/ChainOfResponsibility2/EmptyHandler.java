package com.example.detect_ai.Model.ChainOfResponsibility2;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class EmptyHandler implements WordCountHandler {
    private WordCountHandler wordCountHandler;

    @Override
    public void setNextHandler(WordCountHandler wordCountHandler) {
        this.wordCountHandler = wordCountHandler;
    }

    @Override
    public void process(Context context, int word_count) {
        if(word_count == 0){
            Toast.makeText(context,"Disease Description cannot be empty.", Toast.LENGTH_SHORT).show();
        }else if(wordCountHandler != null){
            Log.i("NextHandler", wordCountHandler.getHandlerName());
            wordCountHandler.process(context, word_count);
        }else{
            Toast.makeText(context,"Word count can't handle.",Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public String getHandlerName() {
        return "EmptyHandler";
    }
}
