package com.example.detect_ai.Controller.Detect;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.fragment.app.Fragment;

import com.example.detect_ai.R;

public class startECG_detect extends Fragment {

    View view;
    Button button;
    Countdown_ECG countdown_ecg;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.start_ecg_detect_fragment, container, false);
        countdown_ecg  = new Countdown_ECG();
        button = view.findViewById(R.id.button7);
        //點擊後執行跳頁的指令
        button.setOnClickListener(view -> requireActivity().getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.frameLayout, countdown_ecg, null)
                .addToBackStack(null)
                .commit());
        return view;

    }
}
