package com.example.detect_ai.Model;

import com.example.detect_ai.Model.Builder.Measure;
import com.google.firebase.Timestamp;

public class UploadCase {
    private Measure measure;
    private String user_description;
    private String doctor_response;
    private Boolean doctor_response_status;
    private Timestamp timestamp;

    public UploadCase() {

    }

    public Measure getMeasure() {
        return measure;
    }

    public void setMeasure(Measure measure) {
        this.measure = measure;
    }

    public String getUser_description() {
        return user_description;
    }

    public void setUser_description(String user_description) {
        this.user_description = user_description;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public String getDoctor_response() {
        return doctor_response;
    }

    public void setDoctor_response(String doctor_response) {
        this.doctor_response = doctor_response;
    }

    public Boolean getDoctor_response_status() {
        return doctor_response_status;
    }

    public void setDoctor_response_status(Boolean doctor_response_status) {
        this.doctor_response_status = doctor_response_status;
    }
}
