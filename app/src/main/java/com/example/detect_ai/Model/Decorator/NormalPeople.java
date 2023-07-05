package com.example.detect_ai.Model.Decorator;

import android.content.Context;
import android.os.Build;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.example.detect_ai.Model.Singleton.UserData;

@RequiresApi(api = Build.VERSION_CODES.O)
public class NormalPeople implements People {
    Context context;
    UserData userData = UserData.getUserData();

    public NormalPeople(Context context){
        this.context = context;
        Toast.makeText(context,
                "Hello~ "+userData.getName()+". The physical symptom(s) you currently have is(are)...",
                Toast.LENGTH_LONG).show();
    }

    @Override
    public void MessageReminder() {
        Toast.makeText(context, "You must take care of your health during busy working lives.",Toast.LENGTH_LONG).show();
    }
}
