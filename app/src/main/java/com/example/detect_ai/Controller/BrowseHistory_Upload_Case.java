package com.example.detect_ai.Controller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.example.detect_ai.Model.Builder.Measure;
import com.example.detect_ai.R;
import com.example.detect_ai.Model.UploadCase;
import com.example.detect_ai.Model.Singleton.UserData;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

@RequiresApi(api = Build.VERSION_CODES.O)
public class BrowseHistory_Upload_Case extends AppCompatActivity {
    UserData userData = UserData.getUserData();
    UploadCase uploadCase;
    Measure measure;

    TextView textView;
    LineChart lineChart;

    private String browseData ="";
    ArrayList<Entry> values = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_browse_history__upload__case);

        FindViewById();

        Intent intent = getIntent();
        int position = intent.getIntExtra("position", 1);

        uploadCase = userData.getSingleUploadCase(position);
        measure = uploadCase.getMeasure();
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

        for(int i=0; i<measure.getECG().size(); i++){
            double temp = measure.getECG().get(i);
            float f = (float) temp;
            values.add(new Entry(i, f));
        }
        initDataSet(values);

        browseData += "Measure Time：" + sdf.format(measure.getTimestamp().toDate()) + "\n";
        browseData += "Blood Oxygen Level：" + measure.getBloodOxygen() + " %\n";
        browseData += "Pulse：" + measure.getPulse() + " (times per minute)\n\n";
        browseData += "Upload Time：" + sdf.format(uploadCase.getTimestamp().toDate()) + "\n";
        browseData += "Disease Description：" + uploadCase.getUser_description() + "\n";

        if (!uploadCase.getDoctor_response_status()){
            browseData += "Reply Status：The doctor has not replied.";
        }else {
            browseData += "Reply Status：The doctor has replied\n";
            browseData += "Doctor Reply："+uploadCase.getDoctor_response();
        }

        textView.setText(browseData);
    }

    private void FindViewById(){
        textView = findViewById(R.id.textView34);
        lineChart = findViewById(R.id.lineChart); //ECG
    }

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
}
