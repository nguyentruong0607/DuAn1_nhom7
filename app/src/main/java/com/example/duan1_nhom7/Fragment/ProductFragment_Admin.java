package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterSanPham;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;

public class ProductFragment_Admin extends Fragment {
    RecyclerView rcv_admin;
    ArrayList<SanPham> list;
    SanPhamDAO dao;
    AdapterSanPham adapterSanPham;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_fragment_admin,container,false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv_admin=view.findViewById(R.id.rcv_product_admin);
        dao=new SanPhamDAO(getContext());
        list=dao.getAllProduct(0);
        adapterSanPham=new AdapterSanPham(getContext(),list);
        rcv_admin.setAdapter(adapterSanPham);
    }
}
