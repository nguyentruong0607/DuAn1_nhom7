package com.example.duan1_nhom7.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_nhom7.R;

public class Account_Fragment extends Fragment {
    private LinearLayout userFrgmTaiKhoan, userFrgmDoiMK, userFrgmTKDoanhThu, userFrgmTKNhanVien, userFrgmThemSP, userFrgmLoaiSP, userFrgmThemNhanVien, userFrgmDangXuat;
    TextView txtUserName, txtChucVu;


    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        userFrgmThemSP = view.findViewById(R.id.userFrgmThemSP);
        userFrgmLoaiSP=view.findViewById(R.id.userFrgmLoaiSP);
        userFrgmDangXuat = view.findViewById(R.id.userFrgmDangXuat);
        txtUserName = view.findViewById(R.id.txtUserName);
        txtChucVu = view.findViewById(R.id.txtChucVu);

        userFrgmLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new LoaiSPFragment());
            }
        });
        userFrgmThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ThemSPFragment());
            }
        });




        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}