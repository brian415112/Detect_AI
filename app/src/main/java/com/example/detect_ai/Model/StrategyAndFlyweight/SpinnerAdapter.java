package com.example.detect_ai.Model.StrategyAndFlyweight;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

import com.example.detect_ai.Model.Singleton.UserData;
import com.example.detect_ai.R;

import java.text.SimpleDateFormat;
import java.util.List;

@RequiresApi(api = Build.VERSION_CODES.O)
public class SpinnerAdapter extends BaseAdapter {
    List<String> checkedList;
    String[] uploadCase_timestamp;
    UserData userData;
    Context context;
    ReplyContext replyContext;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public SpinnerAdapter(Context context, List<String> list){
        this.context = context;
        userData = UserData.getUserData();

        uploadCase_timestamp = new String[getCount()];
        replyContext = new ReplyContext();

        this.checkedList = list;
    }

    @Override
    public int getCount() {
        return userData.getUploadCases().size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    //getView為設置未點開時的Spinner畫面
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, parent);
    }

    //getDropDownView為設置點開後的畫面
    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createView(position, parent);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("SetTextI18n")
    private View createView(int position, ViewGroup parent){
        View convertView = LayoutInflater.from(context).inflate(R.layout.spinner_item, parent, false);
        TextView tvName = convertView.findViewById(R.id.textViewTitle); //控制介面元件
        TextView tvState = convertView.findViewById(R.id.textViewState);
        String color;

        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss");
        uploadCase_timestamp[position] = sdf.format(userData.getSingleUploadCase(position).getTimestamp().toDate());

        if(checkedList.contains(uploadCase_timestamp[position])){
            color = "#800080"; //紫色
        }else{
            color = "#000000"; //黑色
        }

        replyContext.choiceStrategy(userData.getSingleUploadCase(position).getDoctor_response_status(), color);

        replyContext.print(tvName, tvState, uploadCase_timestamp[position]);

        return convertView;
    }
}