package com.example.detect_ai.Model.ObserverAndVisitor;

import com.example.detect_ai.Model.Builder.Measure;

import java.util.ArrayList;

public class MeasureData implements Observable{
    private final ArrayList<Observer> observerList = new ArrayList<>(); // 紀錄已註冊的測量種類
    private Measure measure;
    SimpleVisitor simpleVisitor = new SimpleVisitor();
    SpecialVisitor specialVisitor = new SpecialVisitor();

    /**
     * 註冊測量種類
     */
    @Override
    public void register(Observer observer) {
        if(measure == null){
            observerList.add(observer);
        }else{
            observerList.add(observer);
            observer.update(measure);
        }
    }

    /**
     * 取消註冊測量種類
     */
    @Override
    public void unregister(Observer observer) {
        observerList.remove(observer);
    }

    /**
     * 通知全部的測量種類
     */
    @Override
    public void inform(Measure measure, int visitMode) {
        this.measure = measure;

        for(Observer observer : observerList){
            observer.update(measure);

            if(visitMode == 1){
                observer.accept(simpleVisitor);
            }else{
                observer.accept(specialVisitor);
            }
        }
    }
}
