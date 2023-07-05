package com.example.detect_ai.Controller;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detect_ai.Model.Singleton.UserData;
import com.example.detect_ai.R;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Welcome extends AppCompatActivity {
    UserData userData = UserData.getUserData();

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        new Thread(() -> {
            try {
                Thread.sleep(2000);
                Intent intent = new Intent();
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.setClass(Welcome.this, MainActivity.class);
                startActivity(intent);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }).start();
    }
}