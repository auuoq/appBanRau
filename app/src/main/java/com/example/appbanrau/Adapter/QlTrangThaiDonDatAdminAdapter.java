package com.example.appbanrau.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.appbanrau.Fragment.DangGiaoHangAdminFragment;
import com.example.appbanrau.Fragment.DonDatAdminFragment;
import com.example.appbanrau.Fragment.HoanThanhDonDatAdminFragment;

public class QlTrangThaiDonDatAdminAdapter extends FragmentStateAdapter {
    public QlTrangThaiDonDatAdminAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new DonDatAdminFragment();
            case 1:
                return new DangGiaoHangAdminFragment();
            case 2:
                return new HoanThanhDonDatAdminFragment();
            default:
                return null;
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
