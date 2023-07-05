package com.example.detect_ai.Model.ObserverAndVisitor;

import com.example.detect_ai.Model.Builder.Measure;

public interface Observable {
    /**
     * 註冊Observer(Reader)
     */
    void register(Observer reader);

    /**
     * 取消註冊Observer(Reader)
     */
    void unregister(Observer reader);

    /**
     * 通知Observer(Reader)
     */
    void inform(Measure measure, int visitMode);
}
