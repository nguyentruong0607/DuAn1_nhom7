package com.example.duan1_nhom7;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duan1_nhom7.Adapter.AdapterTrangThai;
import com.example.duan1_nhom7.Fragment.Account_Fragment;
import com.example.duan1_nhom7.Fragment.ChoXacNhanFragment;
import com.example.duan1_nhom7.Fragment.DaGiaoFragment;
import com.example.duan1_nhom7.Fragment.DaHuyFragment;
import com.example.duan1_nhom7.Fragment.DangGiaoFragment;
import com.google.android.material.tabs.TabLayout;

public class DonHangActivity extends Fragment {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_don_hang, container, false);

        tabLayout = view.findViewById(R.id.tablayout);
        viewPager = view.findViewById(R.id.viewPagerTab);

        AdapterTrangThai tt = new AdapterTrangThai(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tt.addFragment(new ChoXacNhanFragment(), "Chờ xác nhận");
        tt.addFragment(new DangGiaoFragment(), "Đang giao");
        tt.addFragment(new DaGiaoFragment(), "Đã giao");
        tt.addFragment(new DaHuyFragment(), "Đã hủy");

        viewPager.setAdapter(tt);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(tt.getFragmentPosition(new DaHuyFragment()));

        return view;
    }
}
