package com.example.detect_ai.Controller.Detect;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.StrictMode;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.detect_ai.Model.Template.BloodOxygenGetter;
import com.example.detect_ai.Model.Template.MeasureDataGetter;
import com.example.detect_ai.Model.Template.PulseGetter;
import com.example.detect_ai.R;

import java.util.ArrayList;

public class Countdown_BloodOxygenAndPulse extends Fragment {
    TextView textView, textView2;
    ArrayList<Integer> bloodOxygen = new ArrayList<>();
    ArrayList<Integer> pulse = new ArrayList<>();
    double avgPulse, avgBloodOxygen;
    ArrayList<Double> ECG;

    CountDownTimer countDownTimer;
    View view;
    TextView countdown_timer;
    DetectResult detectResult;

    public Countdown_BloodOxygenAndPulse(ArrayList<Double> ecg) {
        this.ECG = ecg;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_countdown_bloodoxygen_pulse, container, false);

        FindViewById();

        int SDK_INT = android.os.Build.VERSION.SDK_INT;
        if (SDK_INT > 8)
        {
            StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder()
                    .permitAll().build();
            StrictMode.setThreadPolicy(policy);
        }

        countDownTimer = new CountDownTimer(30000, 1000) {
            final MeasureDataGetter bloodOxygenGetter = new BloodOxygenGetter(getActivity(), this);
            final MeasureDataGetter pulseGetter = new PulseGetter(getActivity(), this);
            String response, response2;
            int temp_BloodOxygen, temp_Pulse = 0;

            @SuppressLint("SetTextI18n")
            public void onTick(long millisUntilFinished) {
                new Thread("GetData"){
                    @Override
                    public void run() {
                        response = bloodOxygenGetter.DataGetter();
                        if(response != null) {
                            temp_BloodOxygen = Integer.parseInt(response);

                            bloodOxygen.add(temp_BloodOxygen);
                        }
                    }
                }.start();

                new Thread("GetData2"){
                    @Override
                    public void run() {
                        response2 = pulseGetter.DataGetter();
                        if(response2 != null) {
                            temp_Pulse = Integer.parseInt(response2);

                            pulse.add(temp_Pulse);
                        }
                    }
                }.start();

                countdown_timer.setText("" + millisUntilFinished / 1000);

                textView.setText(temp_BloodOxygen+" %");
                textView2.setText(temp_Pulse+" (times per minute)");
            }

            @SuppressLint("SetTextI18n")
            @RequiresApi(api = Build.VERSION_CODES.O)
            public void onFinish() {
                countdown_timer.setText("Over!");

                double total = 0;
                for(int i=0; i<bloodOxygen.size(); i++){
                    total += bloodOxygen.get(i);
                }
                avgBloodOxygen = total/bloodOxygen.size();

                total = 0;
                for(int i=0; i<pulse.size(); i++){
                    total += pulse.get(i);
                }
                avgPulse = total/pulse.size();

                detectResult = new DetectResult(ECG, avgBloodOxygen, avgPulse);

                requireActivity().getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.frameLayout, detectResult, null)
                        .addToBackStack(null)
                        .commit();
            }
        }.start();

        return view;
    }

    private void FindViewById(){
        countdown_timer = view.findViewById(R.id.textView19);
        textView = view.findViewById(R.id.textView27);
        textView2 = view.findViewById(R.id.textView31);
    }
}
