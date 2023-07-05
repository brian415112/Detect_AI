package com.example.detect_ai.Controller.Detect;

import static com.google.firebase.Timestamp.now;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.detect_ai.Model.Builder.Measure;
import com.example.detect_ai.Model.Builder.MeasureFactory;
import com.example.detect_ai.Model.Decorator.AbnormalBloodOxygen;
import com.example.detect_ai.Model.Decorator.AbnormalPulse;
import com.example.detect_ai.Model.Decorator.Asymptomatic;
import com.example.detect_ai.Model.Decorator.NormalPeople;
import com.example.detect_ai.Model.Decorator.People;
import com.example.detect_ai.R;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.google.firebase.Timestamp;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class DetectResult extends Fragment {
    Measure m;
    Handler handler;
    TextView textView1, textView2;
    LineChart lineChart;
    ArrayList<Entry> values = new ArrayList<>();
    View view;
    Button button, button2;
    double bloodOxygen;
    double pulse;
    ArrayList<Double> ECG;
    Timestamp timestamp;

    public DetectResult(ArrayList<Double> ecg, double avgBloodOxygen, double avgPulse) {
        this.ECG = ecg;
        this.bloodOxygen = avgBloodOxygen;
        this.pulse = avgPulse;
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_detect_result, container, false);
        handler = new Handler();
        textView1 = view.findViewById(R.id.textView24); //BloodOxygen
        textView2 = view.findViewById(R.id.textView32); //Pulse
        lineChart = view.findViewById(R.id.lineChart); //ECG
        button = view.findViewById(R.id.button8);
        button2 = view.findViewById(R.id.button9);

        timestamp = now();

        m = new MeasureFactory()
                .setBloodOxygen(bloodOxygen)
                .setPulse(pulse)
                .setECG(ECG)
                .setTimestamp(timestamp)
                .build();

        for(int i=0; i<m.getECG().size(); i++){
            double temp = m.getECG().get(i);
            float f = (float) temp;
            values.add(new Entry(i, f));
        }
        initDataSet(values);

        new Thread() {
            public void run() {
                handler.post(udpUIRunnable); //向Handler post runnable對象
            }
        }.start();

        FirebaseFirestore db = FirebaseFirestore.getInstance();
        db.collection("users/f9hWHE9nVg0mseUvC2zX/Measure").add(m);

        //點擊後執行跳頁的指令
        button.setOnClickListener(view1 -> requireActivity().finish());

        button2.setOnClickListener(view1 -> {
            Intent intent = new Intent(getActivity(), Diary_After_Measurement.class);
            Bundle bundle = new Bundle();
            bundle.putString("timestamp", timestamp.toString());
            intent.putExtras(bundle);
            startActivity(intent);
        });

        return view;
    }

    Runnable udpUIRunnable = new Runnable() {
        @SuppressLint("SetTextI18n")
        @Override
        public void run() {

            textView1.setText((int)m.getBloodOxygen() + " %");
            textView2.setText((int)m.getPulse()+" (times per minute)");

            Context context = getContext();

            People normalPeople = new NormalPeople(context);
            People people;

            Boolean bloodOxygen_judgment = (int)bloodOxygen>95;
            Boolean pulse_judgment = (int)pulse<85 && (int)pulse>55;

            if(bloodOxygen_judgment && pulse_judgment){
                people = new AbnormalBloodOxygen(new AbnormalPulse(normalPeople, context), context);
            }else if(bloodOxygen_judgment){
                people = new AbnormalPulse(normalPeople, context);
            }else if(pulse_judgment){
                people = new AbnormalBloodOxygen(normalPeople, context);
            }else{
                people = new Asymptomatic(normalPeople, context);
            }

            people.MessageReminder();
        }
    };

    @RequiresApi(api = Build.VERSION_CODES.O)
    private void initDataSet(final ArrayList<Entry> values) {
        final LineDataSet set;

        set = new LineDataSet(values, "");
        set.setMode(LineDataSet.Mode.CUBIC_BEZIER);//類型為折線
        set.setColor(Color.GREEN);//線的顏色
        set.setLineWidth(1.5f);//線寬
        set.setDrawCircles(false); //不顯示相應座標點的小圓圈(預設顯示)
        set.setDrawValues(false);//不顯示座標點對應Y軸的數字(預設顯示)

        LineData data = new LineData(set);
        lineChart.setData(data);//一定要放在最後
        lineChart.invalidate();//繪製圖表
    }

    public void setMeasure(Measure m){
        this.m = m;
    }
}
