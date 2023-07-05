package com.example.detect_ai.Model.ChainOfResponsibility;

public interface ImageHandler {
    void setNextHandler(ImageHandler imageHandler);
    void process();
    String getHandlerName();
}