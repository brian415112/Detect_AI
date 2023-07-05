package com.example.detect_ai.Controller;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.detect_ai.Model.ChainOfResponsibility2.EmptyHandler;
import com.example.detect_ai.Model.ChainOfResponsibility2.GreaterThan50Handler;
import com.example.detect_ai.Model.ChainOfResponsibility2.LessThan5Handler;
import com.example.detect_ai.Model.ChainOfResponsibility2.NormalHandler;
import com.example.detect_ai.Model.ChainOfResponsibility2.WordCountHandler;
import com.example.detect_ai.Model.Singleton.UserData;
import com.example.detect_ai.Model.UploadCase;
import com.example.detect_ai.R;

import java.text.SimpleDateFormat;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Tab2_UploadCase extends Fragment {
    UserData userData = UserData.getUserData();

    Spinner spinner;
    View view;

    private int position;
    UploadCase uploadCase = new UploadCase();
    String[] measure_timestamp;
    private boolean isFirstLoading = true;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab2_layout,container,false);
        measure_timestamp = new String[userData.getMeasure().size()];

        for(int i =0;i < userData.getMeasure().size();i++){
            @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

            measure_timestamp[i] = sdf.format(userData.getSingleMeasure(i).getTimestamp().toDate());
        }

        FindViewById();

        ArrayAdapter<String> measure_timestamp_List = new ArrayAdapter<>(this.getActivity(),
                android.R.layout.simple_spinner_dropdown_item,
                measure_timestamp);
        spinner.setAdapter(measure_timestamp_List);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int p, long id) {
                position = p;
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        EditText editText = view.findViewById((R.id.editText2));
        Button button = view.findViewById(R.id.button);

        button.setOnClickListener(view1 -> {
            String tempString = editText.getText().toString();
            String[] word_split = tempString.trim().split("\\s+");

            uploadCase.setMeasure(userData.getSingleMeasure(position));

            int word_count;
            if(tempString.equals("")){
                word_count = 0;
            }else{
                word_count = word_split.length;
            }

            WordCountHandler normalHandler = new NormalHandler(uploadCase, editText);
            WordCountHandler emptyHandler = new EmptyHandler();
            WordCountHandler lessThan5Handler = new LessThan5Handler();
            WordCountHandler greaterThan50Handler = new GreaterThan50Handler();

            normalHandler.setNextHandler(emptyHandler);
            emptyHandler.setNextHandler(lessThan5Handler);
            lessThan5Handler.setNextHandler(greaterThan50Handler);

            normalHandler.process(getContext(), word_count);
        });

       editText.setOnFocusChangeListener((v, hasFocus) -> {
           if(getActivity() != null){
               if (hasFocus) {
                   getActivity().getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
               } else {
                   InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                   imm.hideSoftInputFromWindow(editText.getWindowToken(), 0);
               }
           }
       });

       return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            UserData.Refresh();

            measure_timestamp = new String[userData.getMeasure().size()];

            for(int i =0;i < userData.getMeasure().size();i++){
                @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");

                measure_timestamp[i] = sdf.format(userData.getSingleMeasure(i).getTimestamp().toDate());
            }

            ArrayAdapter<String> measure_timestamp_List = new ArrayAdapter<>(this.getActivity(),
                    android.R.layout.simple_spinner_dropdown_item,
                    measure_timestamp);
            spinner.setAdapter(measure_timestamp_List);
        }

        isFirstLoading = false;
    }

    private void FindViewById(){
        spinner = view.findViewById(R.id.spinner);
    }
}