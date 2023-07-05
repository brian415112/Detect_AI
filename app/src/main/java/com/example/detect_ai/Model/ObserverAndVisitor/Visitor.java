package com.example.detect_ai.Model.ObserverAndVisitor;

public interface Visitor {
    void visit(ECG ecg);
    void visit(Pulse pulse);
    void visit(BloodOxygen bloodOxygen);
}
