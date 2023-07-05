package com.example.detect_ai.Controller.Detect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.detect_ai.R;

import java.util.ArrayList;

public class startBloodOxygenAndPulseDetect extends Fragment {
    View view;
    Button button;
    ArrayList<Double> ECG;

    public startBloodOxygenAndPulseDetect(ArrayList<Double> ecg) {
        this.ECG = ecg;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.start_bloodoxygen_pulse_detect_fragment, container, false);

        Countdown_BloodOxygenAndPulse countdown_bloodOxygenAndPulse = new Countdown_BloodOxygenAndPulse(ECG);

        button = view.findViewById(R.id.button7);
        //點擊後執行跳頁的指令
        button.setOnClickListener(view1 -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, countdown_bloodOxygenAndPulse, null)
                .addToBackStack(null)
                .commit());
        return view;
    }
}
