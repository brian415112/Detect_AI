package com.example.detect_ai.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.detect_ai.Model.Singleton.UserData;
import com.example.detect_ai.Model.StrategyAndFlyweight.SpinnerAdapter;
import com.example.detect_ai.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Tab3_BrowseHistory extends Fragment {
    UserData userData = UserData.getUserData();

    Spinner spinner, spinner2;
    Button button, button2;
    View view;

    private int position,position2;
    private boolean isFirstLoading = true;
    String[] measure_timestamp;
    List<String> checkedList = new ArrayList<>();

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab3_layout, container,false);

        FindViewById();

        //以下為測量部分
        browseHistory_Detect_data();

        //以下為上傳案例
        browseHistory_Upload_Case();

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            UserData.Refresh();

            //以下為測量部分
            browseHistory_Detect_data();

            //以下為上傳案例
            browseHistory_Upload_Case();
        }

        isFirstLoading = false;
    }

    public void FindViewById(){
        spinner = view.findViewById(R.id.spinner3);
        button = view.findViewById(R.id.button4);
        spinner2 = view.findViewById(R.id.spinner4);
        button2 = view.findViewById(R.id.button5);
    }

    public void process_measure_timestamp(){
        measure_timestamp = new String[userData.getMeasure().size()];

        for(int i =0;i < userData.getMeasure().size();i++){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

            measure_timestamp[i] = sdf.format(userData.getSingleMeasure(i).getTimestamp().toDate());
        }
    }

    public void browseHistory_Detect_data(){
        process_measure_timestamp();
        ArrayAdapter<String> measure_timestamp_List = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                measure_timestamp);
        spinner.setAdapter(measure_timestamp_List);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                position = p;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //點擊後執行跳頁的指令
        button.setOnClickListener(view -> {
            Intent intent = new Intent(getActivity(), BrowseHistory_Detect_Data.class);
            intent.putExtra("position", position);
            startActivity(intent);
        });
    }

    public void browseHistory_Upload_Case(){
        spinner2.setAdapter(new SpinnerAdapter(requireContext(), checkedList));

        spinner2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                position2 = p;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        //點擊後執行跳頁的指令
        button2.setOnClickListener(view -> {

            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
            checkedList.add(sdf.format(userData.getSingleUploadCase(position2).getTimestamp().toDate()));

            Intent intent = new Intent(getActivity(), BrowseHistory_Upload_Case.class);
            intent.putExtra("position", position2);
            startActivity(intent);
        });
    }
}
