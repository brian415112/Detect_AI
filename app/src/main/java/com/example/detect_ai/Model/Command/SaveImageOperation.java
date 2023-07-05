package com.example.detect_ai.Model.Command;

public class SaveImageOperation extends ImageFileOperation{

    public SaveImageOperation(DB_Receiver DB_Receiver) {
        super(DB_Receiver);
    }

    @Override
    public void execute(){
        DB_Receiver.Save();
    }
}
