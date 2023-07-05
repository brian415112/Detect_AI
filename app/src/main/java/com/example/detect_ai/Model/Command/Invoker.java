package com.example.detect_ai.Model.Command;

import java.util.ArrayList;
import java.util.List;

public class Invoker {
    private final List<ImageFileOperation> imageFileOperations;

    public Invoker(){
        imageFileOperations = new ArrayList<>();
    }

    public void setOperation(ImageFileOperation imageFileOperation){
        imageFileOperations.add(imageFileOperation);
    }

    public void notifyReceiver(){
        for (ImageFileOperation imageFileOperation : imageFileOperations){
            imageFileOperation.execute();
        }
        imageFileOperations.clear();
    }
}
