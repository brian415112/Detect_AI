package com.example.detect_ai.Model.Template;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public class ECG_Getter extends MeasureDataGetter{

    public ECG_Getter(Activity activity, CountDownTimer countDownTimer) {
        super(activity, countDownTimer);
    }

    public void SetDeliveryRequirements(){
        request = new Request.Builder()
                .url("http://192.168.4.1/ecg")
                .build();
    }

    @Override
    public void SetUpCallBack() {

        //設置回傳
        Call call = client.newCall(request);
        call.enqueue(new Callback() {

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                //取得回傳
                tempResponse = Objects.requireNonNull(response.body()).string();
                getStatus = 1;
            }

            @SuppressLint("WrongConstant")
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                getStatus = 0;
                flag++;
                countDownTimer.cancel();

                if (e instanceof SocketTimeoutException) {
                    //判斷超時異常
                    Log.i("Failure_ECG", e.getMessage());
                }
                if (e instanceof ConnectException) {
                    //判斷連線異常，
                    Log.i("Failure_ECG", e.getMessage());
                }

                if(flag==1){
                    new Thread() {
                        public void run() {
                            if(activity != null){
                                Looper.prepare();
                                Handler handler = new Handler();
                                handler.post(udpUIRunnable); //向Handler post runnable對象
                                Looper.loop();
                            }
                        }
                    }.start();

                    if (activity != null){
                        activity.finish();
                    }
                }
            }
            final Runnable udpUIRunnable = () -> Toast.makeText(activity,
                    "Please check if the specified Wifi is connected (Measure).", Toast.LENGTH_SHORT).show();
        });
    }
}
