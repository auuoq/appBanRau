package com.example.appbanrau.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appbanrau.Fragment.FragmentCuTrangChuAdmin;
import com.example.appbanrau.Fragment.FragmentQuaTrangChuAdmin;
import com.example.appbanrau.Fragment.FragmentRauTrangChuAdmin;

public class AdapterViewPagerTrangChu extends FragmentStateAdapter {
    public AdapterViewPagerTrangChu(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new FragmentRauTrangChuAdmin();
            case 1:
                return new FragmentCuTrangChuAdmin();
            case 2:
                return new FragmentQuaTrangChuAdmin();
            default:
                return new FragmentRauTrangChuAdmin();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
