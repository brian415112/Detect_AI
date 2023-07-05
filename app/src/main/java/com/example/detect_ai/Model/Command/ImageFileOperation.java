package com.example.detect_ai.Model.Command;

public abstract class ImageFileOperation {
    protected DB_Receiver DB_Receiver;

    public ImageFileOperation(DB_Receiver db_receiver){
        this.DB_Receiver = db_receiver;
    }

    public abstract void execute();

}
