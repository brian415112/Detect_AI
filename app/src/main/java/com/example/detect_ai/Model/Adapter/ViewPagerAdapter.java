package com.example.detect_ai.Model.Adapter;

import android.os.Build;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.detect_ai.Controller.Tab1_DetectBody;
import com.example.detect_ai.Controller.Tab2_UploadCase;
import com.example.detect_ai.Controller.Tab3_BrowseHistory;
import com.example.detect_ai.Controller.Tab4_ManageAccount;

public class ViewPagerAdapter extends FragmentStateAdapter {

    public ViewPagerAdapter(FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0:
                return new Tab1_DetectBody();
            case 1:
                return new Tab2_UploadCase();
            case 2:
                return new Tab3_BrowseHistory();
            default:
                return new Tab4_ManageAccount();
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }
}