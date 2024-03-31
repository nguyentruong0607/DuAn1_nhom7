package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.Adapter.AdapterDaGiao;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.R;

import java.util.List;


public class DaHuyFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDaGiao adapter;
    private DonHangDAO donHangDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_da_huy, container, false);

        recyclerView = view.findViewById(R.id.rcv_DaHuy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getContext());
        List<DonHang> donHangList = donHangDAO.getDonHangByStatus("4");

        adapter = new AdapterDaGiao(getContext(), donHangList);
        recyclerView.setAdapter(adapter);
        return view;
    }
}