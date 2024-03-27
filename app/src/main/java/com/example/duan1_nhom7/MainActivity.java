package com.example.duan1_nhom7;

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
import com.example.duan1_nhom7.Fragment.LoaiSPFragment;
import com.example.duan1_nhom7.Fragment.ProductFragment;
import com.example.duan1_nhom7.Fragment.StoreFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity implements BottomNavigationView.OnNavigationItemSelectedListener {
    public static BottomNavigationView bottomNavigationView;
    SanPham sanPham;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(this);
        bottomNavigationView.setSelectedItemId(R.id.pageTrangChu);
        loadFragment(new HomeFragment());

    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment;
        if (item.getItemId() == R.id.pageTrangChu) {
            fragment = new HomeFragment();
            loadFragment(fragment);
            return true;
        } else if (item.getItemId() == R.id.pageSanPham) {
            fragment = new ProductFragment(sanPham);
            loadFragment(fragment);
            return true;
        } else if (item.getItemId() == R.id.pageBanHang) {
            fragment = new StoreFragment();
            loadFragment(fragment);
            return true;
        } else if (item.getItemId() == R.id.pageTaiKhoan) {
            loadFragment(new Account_Fragment());
            return true;
        } else {
            return false;
        }

    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}