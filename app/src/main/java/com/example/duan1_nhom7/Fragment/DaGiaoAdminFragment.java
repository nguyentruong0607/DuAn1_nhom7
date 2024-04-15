package com.example.duan1_nhom7.Fragment;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.Adapter.AdapterDaHuy;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.example.duan1_nhom7.DonHangsAdmin;
import com.example.duan1_nhom7.R;

import java.util.List;


public class DaGiaoAdminFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDaHuy adapter;
    private DonHangDAO donHangDAO;

    private DAOHoaDon daoHoaDon;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_da_giao_admin, container, false);

        recyclerView = view.findViewById(R.id.rcv_DaGiaoAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));


        donHangDAO = new DonHangDAO(getActivity());
        daoHoaDon = new DAOHoaDon(getActivity());

        List<DonHang> donHangs = donHangDAO.getDonHangsByStatus("3");

        adapter = new AdapterDaHuy(getContext(), donHangs);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterDaHuy.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                HoaDon hoaDon = daoHoaDon.getHoaDonById(donHang.getId_HoaDon());
                Intent intent = new Intent(getContext(), DonHangsAdmin.class);
                intent.putExtra("donhangs", hoaDon);
                startActivity(intent);
            }
        });

        return view;
    }
}