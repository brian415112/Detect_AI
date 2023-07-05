package com.example.detect_ai.Model.Decorator;

import android.content.Context;
import android.os.Build;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Symptoms implements People {
    Context context;
    People people;

    public Symptoms(People people, Context context){
        this.people = people;
        this.context = context;
    }

    @Override
    public void MessageReminder() {
        people.MessageReminder();
    }
}
