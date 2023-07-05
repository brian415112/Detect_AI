package com.example.detect_ai.Model.Template;

import android.app.Activity;
import android.os.CountDownTimer;
import android.util.Log;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;

public abstract class MeasureDataGetter {
    OkHttpClient client;
    Request request;
    Activity activity;
    CountDownTimer countDownTimer;
    String tempResponse;
    int flag;
    int getStatus;

    public MeasureDataGetter(Activity activity, CountDownTimer countDownTimer){
        this.activity = activity;
        this.countDownTimer = countDownTimer;
    }

    public final String DataGetter(){
        EstablishConnection();
        SetDeliveryRequirements();

        SetUpCallBack();

        while (getStatus == -1){
            Log.e("WaitingResponse","Waiting for response result.");
        }

        return tempResponse;
    }

    public void EstablishConnection(){
        flag = 0;
        getStatus = -1;

        //建立連線
        client = new OkHttpClient().newBuilder()
                .connectTimeout(5, TimeUnit.SECONDS)
                .readTimeout(5, TimeUnit.SECONDS)
                .addInterceptor(new HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BASIC))
                .build();
    }

    public abstract void SetDeliveryRequirements();
    public abstract void SetUpCallBack();
}
