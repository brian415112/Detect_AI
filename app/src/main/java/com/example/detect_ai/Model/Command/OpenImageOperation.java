package com.example.detect_ai.Model.Command;

public class OpenImageOperation extends ImageFileOperation{

    public OpenImageOperation(DB_Receiver DB_Receiver) {
        super(DB_Receiver);
    }

    @Override
    public void execute() {
        DB_Receiver.Open();
    }
}
