package com.example.detect_ai.Controller.Detect;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.detect_ai.R;

public class startMeasure extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_measure);

        FragmentManager fragmentMgr = getSupportFragmentManager();
        FragmentTransaction fragmentTrans = fragmentMgr.beginTransaction();
        startECG_detect startECG_detect = new startECG_detect();
        fragmentTrans.add(R.id.frameLayout, startECG_detect, "startECG_detect");
        fragmentTrans.commit();
    }
}
