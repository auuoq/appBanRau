package com.example.appbanrau.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appbanrau.Fragment.FragDonDatUser;
import com.example.appbanrau.Fragment.FragDonHuyUser;
import com.example.appbanrau.Fragment.FragGiaoHangUser;
import com.example.appbanrau.Fragment.FragHoanThanhUser;


public class AdapterViewPagerDonDat extends FragmentStateAdapter {


    public AdapterViewPagerDonDat(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {

            case 0:
                return new FragDonDatUser();
            case 1:
                return new FragGiaoHangUser();
            case 2:
                return new FragHoanThanhUser();
            case 3:
                return new FragDonHuyUser();
            default:
                return new FragDonDatUser();

        }


    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
