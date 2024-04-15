package com.example.duan1_nhom7;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.Adapter.AdapterTrangThai;
import com.example.duan1_nhom7.Fragment.ChoXacNhanFragment;
import com.example.duan1_nhom7.Fragment.DaGiaoAdminFragment;
import com.example.duan1_nhom7.Fragment.DaGiaoFragment;
import com.example.duan1_nhom7.Fragment.DaHuyFragment;
import com.example.duan1_nhom7.Fragment.DaXacNhanAdminFragment;
import com.example.duan1_nhom7.Fragment.DangGiaoFragment;
import com.example.duan1_nhom7.Fragment.DonHuyAdminFragment;
import com.example.duan1_nhom7.Fragment.QLDonHangFragment;
import com.google.android.material.tabs.TabLayout;


public class DonHangAdmin extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_hang_admin, container, false);


        tabLayout = view.findViewById(R.id.tablayoutAdmin);
        viewPager = view.findViewById(R.id.viewPagerTabAdmin);

        AdapterTrangThai tt = new AdapterTrangThai(getChildFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tt.addFragment(new QLDonHangFragment(), "Đơn hàng chờ xác nhận");
        tt.addFragment(new DaXacNhanAdminFragment(), "Đơn hàng đã xác nhận");
        tt.addFragment(new DaGiaoAdminFragment(), "Đơn hàng đã bán");
        tt.addFragment(new DonHuyAdminFragment(), "Đơn hàng bị hủy");

        viewPager.setAdapter(tt);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(tt.getFragmentPosition(new QLDonHangFragment()));

        return view;
    }
}