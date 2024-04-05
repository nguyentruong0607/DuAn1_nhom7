package com.example.duan1_nhom7;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentPagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.example.duan1_nhom7.Adapter.AdapterTrangThai;
import com.example.duan1_nhom7.Fragment.Account_Fragment;
import com.example.duan1_nhom7.Fragment.ChoXacNhanFragment;
import com.example.duan1_nhom7.Fragment.DaGiaoFragment;
import com.example.duan1_nhom7.Fragment.DaHuyFragment;
import com.example.duan1_nhom7.Fragment.DangGiaoFragment;
import com.google.android.material.tabs.TabLayout;

public class DonHangActivity extends AppCompatActivity {
    private TabLayout tabLayout;
    private ViewPager viewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_hang);

        tabLayout = findViewById(R.id.tablayout);
        viewPager = findViewById(R.id.viewPagerTab);

        AdapterTrangThai tt = new AdapterTrangThai(getSupportFragmentManager(), FragmentPagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        tt.addFragment(new ChoXacNhanFragment(), "Chờ xác nhận");
        tt.addFragment(new DangGiaoFragment(), "Đang giao");
        tt.addFragment(new DaGiaoFragment(), "Đã giao");
        tt.addFragment(new DaHuyFragment(), "Đã hủy");

        viewPager.setAdapter(tt);

        tabLayout.setupWithViewPager(viewPager);
        viewPager.setCurrentItem(tt.getFragmentPosition(new DaHuyFragment()));
    }




}
