package com.example.detect_ai.Model.ChainOfResponsibility2;

import static com.google.firebase.Timestamp.now;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;
import android.widget.Toast;

import com.example.detect_ai.Model.UploadCase;
import com.google.firebase.firestore.FirebaseFirestore;

public class NormalHandler implements WordCountHandler {
    private WordCountHandler wordCountHandler;
    UploadCase uploadCase;
    EditText editText;

    public NormalHandler(UploadCase uploadCase, EditText editText){
        this.uploadCase = uploadCase;
        this.editText = editText;
    }

    @Override
    public void setNextHandler(WordCountHandler wordCountHandler) {
        this.wordCountHandler = wordCountHandler;
    }

    @Override
    public void process(Context context, int word_count) {
        if(word_count > 5 && word_count < 50){
            uploadCase.setTimestamp(now());
            uploadCase.setUser_description(editText.getText().toString());
            uploadCase.setDoctor_response("");
            uploadCase.setDoctor_response_status(false);

            FirebaseFirestore db = FirebaseFirestore.getInstance();
            db.collection("users/f9hWHE9nVg0mseUvC2zX/UploadCase").add(uploadCase);

            Toast.makeText(context, "Upload successfully.", Toast.LENGTH_SHORT).show();

            editText.setText("");
        }else if(wordCountHandler != null){
            Log.i("NextHandler", wordCountHandler.getHandlerName());
            wordCountHandler.process(context, word_count);
        }else{
            Toast.makeText(context,"Word count can't handle.",Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public String getHandlerName() {
        return "NormalHandler";
    }
}
