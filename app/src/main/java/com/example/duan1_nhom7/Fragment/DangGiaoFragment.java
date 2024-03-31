package com.example.duan1_nhom7.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.Adapter.AdapterDanGiao;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DaNhanHangActivity;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;

import java.util.List;

public class DangGiaoFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDanGiao adapter;
    private DonHangDAO donHangDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dang_giao, container, false);

        recyclerView = view.findViewById(R.id.rcv_DangGiao);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getContext());
        List<DonHang> donHangList = donHangDAO.getDonHangByStatus("2");

        adapter = new AdapterDanGiao(getContext(), donHangList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterDanGiao.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                Intent intent = new Intent(getContext(), DaNhanHangActivity.class);
                intent.putExtra("hang", donHang); // Gửi thông tin đơn hàng sang activity mới
                getContext().startActivity(intent);
            }
        });

        return view;
    }


}