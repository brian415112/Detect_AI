package com.example.detect_ai.Model.Command;

public class DeleteImageOperation extends ImageFileOperation{
    public DeleteImageOperation(DB_Receiver DB_Receiver) {
        super(DB_Receiver);
    }

    @Override
    public void execute() {
        DB_Receiver.Delete();
    }
}
