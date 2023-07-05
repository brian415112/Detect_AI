package com.example.detect_ai.Model.Decorator;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Asymptomatic extends Symptoms{


    public Asymptomatic(People people, Context context) {
        super(people, context);
    }

    public void MessageReminder(){
        setSymptoms();
        people.MessageReminder();
    }

    public void setSymptoms(){
        Toast.makeText(context, "Asymptomatic",Toast.LENGTH_LONG).show();
    }
}
