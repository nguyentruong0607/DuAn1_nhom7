package com.example.duan1_nhom7.Adapter;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.Fragment.LoaiSPFragment;
import com.example.duan1_nhom7.Fragment.ProductFragment;
import com.example.duan1_nhom7.Fragment.ProductFragment_Admin;

public class ViewPagerAdapter extends FragmentStateAdapter {
    int soluong=2;
    SanPham sanPham;
    public ViewPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position){
            case 0: return new ProductFragment_Admin();
            case 1: return new LoaiSPFragment();
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return soluong;
    }
}
