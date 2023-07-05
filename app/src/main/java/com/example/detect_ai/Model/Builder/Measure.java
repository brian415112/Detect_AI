package com.example.detect_ai.Model.Builder;

import com.example.detect_ai.Model.ObserverAndVisitor.Observer;
import com.google.firebase.Timestamp;

import java.io.Serializable;
import java.util.ArrayList;

public class Measure implements Serializable{
    private final ArrayList<Observer> observerList = new ArrayList<>(); // 紀錄已註冊的測量種類
    private double bloodOxygen;
    private double pulse;
    private ArrayList<Double> ECG = new ArrayList<>();
    private Timestamp timestamp;

    public Measure(){

    }

    public Measure(double bloodOxygen, double pulse, ArrayList<Double> ecg, Timestamp timestamp) {
        this.bloodOxygen = bloodOxygen;
        this.pulse = pulse;
        this.ECG = ecg;
        this.timestamp = timestamp;
    }

    public double getBloodOxygen() {
        return bloodOxygen;
    }

    public void setBloodOxygen(double bloodOxygen) {
        this.bloodOxygen = bloodOxygen;
    }

    public double getPulse() {
        return pulse;
    }

    public void setPulse(double pulse) {
        this.pulse = pulse;
    }

    public ArrayList<Double> getECG() {
        return ECG;
    }

    public void setECG(ArrayList<Double> ECG){
        this.ECG = ECG;
    }

    public Timestamp getTimestamp(){
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }



}
