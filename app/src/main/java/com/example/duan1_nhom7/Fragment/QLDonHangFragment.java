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
import com.example.duan1_nhom7.Adapter.AdapterChoXacNhanAdmin;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;

import java.util.List;


public class QLDonHangFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterChoXacNhanAdmin adapter;
    private DonHangDAO donHangDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_don_hang, container, false);

        recyclerView = view.findViewById(R.id.rcvXacNhanHangAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getContext());
        List<DonHang> donHangList = donHangDAO.getDonHangByStatus("1");

        adapter = new AdapterChoXacNhanAdmin(getContext(), donHangList);
        recyclerView.setAdapter(adapter);




        adapter.setOnItemClickListener(new AdapterChoXacNhanAdmin.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {

            }
        });

        return view;
    }
}