package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterGioHang;
import com.example.duan1_nhom7.DAO.GioHangDAO;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;

public class StoreFragment extends Fragment {
    RecyclerView recycle_gioHang;
    GioHangDAO daoGioHang;
    ArrayList<GioHang> listGioHang;
    public static TextView txtGHTongTien;
    double tongTien = 0;
    ImageView iconRefreshStore;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_store,container,false);
        return  view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        daoGioHang= new GioHangDAO(getContext());
        recycle_gioHang=view.findViewById(R.id.recycle_giohang);
        txtGHTongTien=view.findViewById(R.id.txtGHTongTien);
        listGioHang=daoGioHang.getGioHang();
         createData();
    }
    private void createData() {
        daoGioHang = new GioHangDAO(getContext());
        listGioHang = daoGioHang.getGioHang();
        if (listGioHang.size() == 0){
            recycle_gioHang.setVisibility(View.GONE);
        }
        else {
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
