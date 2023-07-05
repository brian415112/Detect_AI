package com.example.detect_ai.Controller;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.example.detect_ai.R;
import com.example.detect_ai.Model.Singleton.UserData;

@RequiresApi(api = Build.VERSION_CODES.O)
public class Tab4_ManageAccount extends Fragment {
    UserData userData = UserData.getUserData();

    View view;
    TextView name, birthday, sex, cell, tel, email;
    Button button_password, button_info;

    private boolean isFirstLoading = true;

    @SuppressLint("SetTextI18n")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab4_layout,container,false);

        FindViewById();

        name.setText(userData.getName());
        birthday.setText(userData.getBirthday()+"("+userData.getAge()+")");
        sex.setText(userData.getSex());
        cell.setText(userData.getCell());
        tel.setText(userData.getTel());
        email.setText(userData.getEmail());

        button_password.setOnClickListener(v -> Toast.makeText(getContext(), "This function is currently not supported.", Toast.LENGTH_SHORT).show());

        button_info.setOnClickListener(v -> Toast.makeText(getContext(), "This function is currently not supported.", Toast.LENGTH_SHORT).show());

        return view;
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onResume() {
        super.onResume();

        if (!isFirstLoading) {
            //如果不是第一次加载，刷新数据
            UserData.Refresh();

            name.setText(userData.getName());
            birthday.setText(userData.getBirthday()+"("+userData.getAge()+")");
            sex.setText(userData.getSex());
            cell.setText(userData.getCell());
            tel.setText(userData.getTel());
            email.setText(userData.getEmail());
        }

        isFirstLoading = false;
    }

    public void FindViewById(){
        name = view.findViewById(R.id.name);
        birthday = view.findViewById(R.id.birthday);
        sex = view.findViewById(R.id.sex);
        cell = view.findViewById(R.id.cell);
        tel = view.findViewById(R.id.tel);
        email = view.findViewById(R.id.email);
        button_info = view.findViewById(R.id.button_info);
        button_password = view.findViewById(R.id.button3);
    }
}
