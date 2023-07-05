package com.example.detect_ai.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.detect_ai.Controller.Detect.startMeasure;
import com.example.detect_ai.R;
import com.example.detect_ai.Model.Singleton.UserData;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Tab1_DetectBody extends Fragment {
    UserData userData = UserData.getUserData();
    Button button;
    TextView textView;
    View view;

    private boolean isFirstLoading = true;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab1_layout,container,false);

        textView = view.findViewById(R.id.textView15);
        textView.setText("Hello, "+userData.getName()+".\nHurry up and Start Measuring!");

        button = view.findViewById(R.id.button6);
        //點擊後執行跳頁的指令
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getContext(), startMeasure.class);
            startActivity(intent);
        });
        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            UserData.Refresh();
            textView.setText("Hello, "+userData.getName()+".\nHurry up and Start Measuring!");
        }

        isFirstLoading = false;
    }
}
