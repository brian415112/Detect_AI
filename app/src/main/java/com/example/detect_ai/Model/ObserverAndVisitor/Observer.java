package com.example.detect_ai.Model.ObserverAndVisitor;

import com.example.detect_ai.Model.Builder.Measure;

public interface Observer {
    /**
     * 給Observable(News)呼叫的方法
     */
    void update(Measure measure);

    void accept(Visitor v);
}
