package com.example.duan1_nhom7;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.Fragment.Account_Fragment;
import com.example.duan1_nhom7.Fragment.HomeFragment;
import com.example.duan1_nhom7.Fragment.ProductFragment;
import com.example.duan1_nhom7.Fragment.QLy_DonHang_Fragment;
import com.example.duan1_nhom7.Fragment.StoreFragment;
import com.example.duan1_nhom7.Fragment.ViewPagerFragment;
import com.example.duan1_nhom7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity_Admin extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
   BottomNavigationView bottomNavigationView_admin;
   SanPham sanPham;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_admin);
        bottomNavigationView_admin = findViewById(R.id.navigation_admin);
        bottomNavigationView_admin.setOnNavigationItemSelectedListener(this);
        bottomNavigationView_admin.setSelectedItemId(R.id.item_navi_bottom_admin_home);
        loadFragment(new ViewPagerFragment());
    }
    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        Fragment fragment;
        if (menuItem.getItemId() == R.id.item_navi_bottom_admin_home) {
            fragment = new ViewPagerFragment();
            loadFragment(fragment);
            return true;
        } else if (menuItem.getItemId() == R.id.item_navi_bottom_admin_donhang) {
            fragment = new QLy_DonHang_Fragment();
            loadFragment(fragment);
            return true;
        } else if (menuItem.getItemId() == R.id.item_navi_bottom_admin_thongKe) {
            fragment = new QLy_DonHang_Fragment();
            loadFragment(fragment);
            return true;
        } else if (menuItem.getItemId() == R.id.item_navi_bottom_admin) {
            loadFragment(new Account_Fragment());
            return true;
        } else {
            return false;
        }

    }
}