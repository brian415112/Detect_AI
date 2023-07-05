package com.example.detect_ai.Model.Singleton;

import android.annotation.SuppressLint;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.example.detect_ai.Model.Builder.Measure;
import com.example.detect_ai.Model.UploadCase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequiresApi(api = Build.VERSION_CODES.O)
public class UserData {
    private static final UserData userData = new UserData();

    private static String name;
    private static String password;
    private static String account;
    private static String birthday;
    private static long age;
    private static String cell;
    private static String tel;
    private static String email;
    private static String sex;
    private static ArrayList<Measure> measures = new ArrayList<>();
    private static ArrayList<UploadCase> uploadCases = new ArrayList<>();
    private static Map<String,Object> document = new HashMap<>(); //The data retrieved from the database need to be processed
    @SuppressLint("StaticFieldLeak")
    private static final FirebaseFirestore db = FirebaseFirestore.getInstance();

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        UserData.password = password;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        UserData.account = account;
    }

    public long getAge() {
        return age;
    }

    public void setAge(long age) {
        UserData.age = age;
    }

    public String getCell() {
        return cell;
    }

    public void setCell(String cell) {
        UserData.cell = cell;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        UserData.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        UserData.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        UserData.birthday = birthday;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        UserData.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        UserData.sex = sex;
    }

    public ArrayList<Measure> getMeasure(){
        return measures;
    }

    public void setMeasure(ArrayList<Measure> measure){
        UserData.measures = measure;
    }

    public void setUploadCases(ArrayList<UploadCase> uploadCases){
        UserData.uploadCases = uploadCases;
    }

    public ArrayList<UploadCase> getUploadCases(){
        return uploadCases;
    }

    private UserData(){}

    public static UserData getUserData(){
        Refresh();
        return  userData;
    }

    public static void Refresh(){
        Refresh_User_Basic_information();
        Refresh_Measure_information();
        Refresh_Update_information();
    }

    public static void Refresh_User_Basic_information(){
        final DocumentReference docRef = db.collection("users").document("f9hWHE9nVg0mseUvC2zX");
        docRef.addSnapshotListener((value, error) -> {
            if (error != null) {
                Log.w("Listen failed.", "Listen failed.", error);
                return;
            }

            if (value != null && value.exists()) {
                Log.d("Listen success.", "Current data: " + value.getData());
                document = value.getData();
                assert document != null;
                name = (String) document.get("name");
                password = (String) document.get("password");
                account = (String) document.get("account");
                birthday = (String) document.get("birthday");
                try {
                    age = age(birthday);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                cell = (String) document.get("cell");
                tel = (String) document.get("tel");
                email = (String) document.get("email");
                sex = (String) document.get("sex");
            } else {
                Log.d("Null", "Current data: null");
            }
        });
    }

    public static void Refresh_Measure_information(){

        db.collection("users/f9hWHE9nVg0mseUvC2zX/Measure")
                .orderBy("timestamp")
                .addSnapshotListener((value, e) -> {
                    if (e != null) {
                        Log.w("Measure document  Error", "Listen failed.", e);
                        return;
                    }
                    measures.clear();
                    assert value != null;
                    for (QueryDocumentSnapshot doc : value) {
                        measures.add(doc.toObject(Measure.class));
                    }

                    Collections.reverse(measures);
                });
    }

    public static void Refresh_Update_information(){

        db.collection("users/f9hWHE9nVg0mseUvC2zX/UploadCase")
                .orderBy("timestamp")
                .addSnapshotListener((value, e) -> {

                    if (e != null) {
                        Log.w("UploadCase get Error", "Listen failed.", e);
                        return;
                    }

                    uploadCases.clear();
                    assert value != null;
                    for (QueryDocumentSnapshot doc : value) {
                        uploadCases.add(doc.toObject(UploadCase.class));
                    }
                    Collections.reverse(uploadCases);
                });
    }

    public Measure getSingleMeasure(int i){
        return measures.get(i);
    }

    public UploadCase getSingleUploadCase(int i){
        return uploadCases.get(i);
    }

    public static int age(String line) throws ParseException {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        Date birth = df.parse(line);
        Calendar now = Calendar.getInstance();
        Calendar birthday = Calendar.getInstance();
        now.setTime(new Date());
        assert birth != null;
        birthday.setTime(birth);
        int age = now.get(Calendar.YEAR) - birthday.get(Calendar.YEAR);
        if (now.get(Calendar.DAY_OF_YEAR) < birthday.get(Calendar.DAY_OF_YEAR)) {
            age -= 1;
        }
        return age;
    }
}