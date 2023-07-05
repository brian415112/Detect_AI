package com.example.detect_ai.Controller;

import android.annotation.SuppressLint;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detect_ai.Model.Builder.Measure;
import com.example.detect_ai.Model.ObserverAndVisitor.BloodOxygen;
import com.example.detect_ai.Model.ObserverAndVisitor.ECG;
import com.example.detect_ai.Model.ObserverAndVisitor.MeasureData;
import com.example.detect_ai.Model.ObserverAndVisitor.Pulse;
import com.example.detect_ai.Model.Singleton.UserData;
import com.example.detect_ai.R;
import com.github.mikephil.charting.charts.LineChart;
import com.google.android.material.switchmaterial.SwitchMaterial;

import java.text.SimpleDateFormat;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BrowseHistory_Detect_Data extends AppCompatActivity {
    UserData userData = UserData.getUserData();
    int position;
    int viewMode = 1;
    Measure measure;
    MeasureData measureData;
    String[] measure_timestamp;

    Button button;
    TextView textView_bloodOxygen, textView_pulse, textView_ecg_label;
    LinearLayout linearLayout_bloodOxygen, linearLayout_pulse;
    CheckBox checkBox_ecg , checkBox_bloodOxygen, checkBox_pulse;
    Spinner spinner;
    LineChart lineChart;
    SwitchMaterial switchMaterial;

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_history__detect__data);

        FindViewById();
        checkBox_bloodOxygen.setChecked(true);
        checkBox_ecg.setChecked(true);
        checkBox_pulse.setChecked(true);

        Intent intent = getIntent();
        position = intent.getIntExtra("position", 1);

        measure = userData.getSingleMeasure(position);
        measureData = new MeasureData();

        ECG ecgObserver = new ECG(lineChart);
        BloodOxygen bloodOxygen = new BloodOxygen(textView_bloodOxygen);
        Pulse pulse = new Pulse(textView_pulse);

        measureData.register(ecgObserver);
        measureData.register(bloodOxygen);
        measureData.register(pulse);

        checkBox_pulse.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                measureData.register(pulse);
                linearLayout_pulse.setVisibility(View.VISIBLE);
                measureData.inform(measure, viewMode);
            }else{
                measureData.unregister(pulse);
                linearLayout_pulse.setVisibility(View.GONE);
            }
        });

        checkBox_ecg.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                measureData.register(ecgObserver);
                textView_ecg_label.setVisibility(View.VISIBLE);
                lineChart.setVisibility(View.VISIBLE);
                measureData.inform(measure, viewMode);
            }else{
                measureData.unregister(ecgObserver);
                textView_ecg_label.setVisibility(View.GONE);
                lineChart.setVisibility(View.GONE);
            }
        });

        checkBox_bloodOxygen.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                measureData.register(bloodOxygen);
                linearLayout_bloodOxygen.setVisibility(View.VISIBLE);
                measureData.inform(measure, viewMode);
            }else{
                measureData.unregister(bloodOxygen);
                linearLayout_bloodOxygen.setVisibility(View.GONE);
            }
        });

        process_measure_timestamp();
        ArrayAdapter<String> measure_timestamp_List = new ArrayAdapter<>(this,
                android.R.layout.simple_spinner_dropdown_item,
                measure_timestamp);
        spinner.setAdapter(measure_timestamp_List);
        spinner.setSelection(position);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                position = p;
                measure = userData.getSingleMeasure(position);
                measureData.inform(measure, viewMode);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        switchMaterial.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if(isChecked){
                switchMaterial.setText("Special View Mode");
                viewMode = 2;
            }else{
                switchMaterial.setText("Simple View Mode");
                viewMode = 1;
            }

            measureData.inform(measure,viewMode);
        });

        button.setOnClickListener(v -> {
            getWindow().setEnterTransition(new Fade());
            getWindow().setExitTransition(new Fade());
            Intent intent1 = new Intent(BrowseHistory_Detect_Data.this, BrowseDiary.class);
            intent1.putExtra("timestamp", measure.getTimestamp().toString());
            startActivity(intent1, ActivityOptions.makeSceneTransitionAnimation(this).toBundle());
        });
    }

    private void FindViewById(){
        button = findViewById(R.id.button10);
        textView_bloodOxygen = findViewById(R.id.textView_bloodOxygen);
        textView_pulse = findViewById(R.id.textView_pulse);
        lineChart = findViewById(R.id.lineChart);
        textView_ecg_label = findViewById(R.id.textView_ecg_label);
        checkBox_bloodOxygen = findViewById(R.id.checkBox_blood_oxygen);
        checkBox_ecg = findViewById(R.id.checkBox_ecg);
        checkBox_pulse = findViewById(R.id.checkBox_pulse);
        spinner = findViewById(R.id.spinner2);
        linearLayout_bloodOxygen = findViewById(R.id.linear_bloodOxygen);
        linearLayout_pulse = findViewById(R.id.linear_pulse);
        switchMaterial = findViewById(R.id.switch1);
    }

    public void process_measure_timestamp(){
        measure_timestamp = new String[userData.getMeasure().size()];

        for(int i =0;i < userData.getMeasure().size();i++){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

            measure_timestamp[i] = sdf.format(userData.getSingleMeasure(i).getTimestamp().toDate());
        }
    }
}