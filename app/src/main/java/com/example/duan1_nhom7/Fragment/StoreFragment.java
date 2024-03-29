package com.example.duan1_nhom7.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterGioHang;
import com.example.duan1_nhom7.DAO.GioHangDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.R;
import com.example.duan1_nhom7.ThanhToanActivity;

import java.util.ArrayList;

public class StoreFragment extends Fragment {
    RecyclerView recycle_gioHang;
    GioHangDAO daoGioHang;
    ArrayList<GioHang> listGioHang;
    public static TextView txtGHTongTien;
    double tongTien = 0;
    ImageView iconRefreshStore;
    Button btnThanhToan;
    UserDAO daoUser;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        daoUser = new UserDAO(getContext());

        View view = inflater.inflate(R.layout.fragment_store, container, false);
        return view;

    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoGioHang = new GioHangDAO(getContext());
        recycle_gioHang = view.findViewById(R.id.recycle_giohang);
        txtGHTongTien = view.findViewById(R.id.txtGHTongTien);
        btnThanhToan = view.findViewById(R.id.btnGioHangTT);
        listGioHang = daoGioHang.getGioHang();
        createData();

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (txtGHTongTien.getText().toString().equals("0 VNĐ") || txtGHTongTien.getText().toString().equals("0VNĐ")) {
                    Toast.makeText(getContext(), "Giỏ hàng của bạn đang trống.", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(getActivity(), ThanhToanActivity.class);
                    String tongTienThanhToan = txtGHTongTien.getText().toString();
                    intent.putExtra("tong_tien", tongTienThanhToan);
                    startActivity(intent);

                }
            }

        });

    }

    private void createData() {
        daoGioHang = new GioHangDAO(getContext());
        listGioHang = daoGioHang.getGioHang();
        if (listGioHang.size() == 0) {
            recycle_gioHang.setVisibility(View.GONE);
        } else {
            LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
            recycle_gioHang.setLayoutManager(linearLayoutManager);

            tongTien = daoGioHang.tongTienGiohang();
            String outTongTien = String.format("%,.0f", tongTien);
            txtGHTongTien.setText(outTongTien + " VNĐ");

            AdapterGioHang adapterGioHang = new AdapterGioHang(getContext(), listGioHang);
            recycle_gioHang.setAdapter(adapterGioHang);
        }
    }
}
