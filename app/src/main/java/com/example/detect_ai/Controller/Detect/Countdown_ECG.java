package com.example.detect_ai.Controller.Detect;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.detect_ai.Model.ECG_printer;
import com.example.detect_ai.Model.Template.ECG_Getter;
import com.example.detect_ai.Model.Template.MeasureDataGetter;
import com.example.detect_ai.R;

import java.util.ArrayList;
import java.util.Objects;

public class Countdown_ECG extends Fragment {
    ArrayList<Double> ECG = new ArrayList<>();
    CountDownTimer countDownTimer;
    View view;
    TextView countdown_timer;
    ECG_printer ecgPrinter;
    startBloodOxygenAndPulseDetect startBloodOxygenAndPulseDetect;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_countdown_ecg, container, false);

        FindViewById();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        countDownTimer = new CountDownTimer(30000, 100) {
            final MeasureDataGetter ecg_getter = new ECG_Getter(getActivity(), this);
            double temp_ECG = 0;
            String response;

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                new Thread("GetData"){

                    @Override
                    public void run() {
                        response = ecg_getter.DataGetter();

                        if(response!=null) {
                            temp_ECG = Double.parseDouble(response);

                            ECG.add(temp_ECG);
                        }
                    }
                }.start();

                countdown_timer.setText("" + millisUntilFinished / 1000);
                ecgPrinter.showLine(temp_ECG);
            }

            @SuppressLint("SetTextI18n")
            public void onFinish() {
                countdown_timer.setText("over!");
                startBloodOxygenAndPulseDetect = new startBloodOxygenAndPulseDetect(ECG);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, startBloodOxygenAndPulseDetect, null)
                        .addToBackStack(null)
                        .commit();

            }
        }.start();
        return view;
    }

    private void FindViewById(){
        countdown_timer = view.findViewById(R.id.textView19);
        ecgPrinter = view.findViewById(R.id.chart);
    }
}