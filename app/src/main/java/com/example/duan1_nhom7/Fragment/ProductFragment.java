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
import java.util.List;

public class ProductFragment extends Fragment {
    List<SanPham> list;
    AdapterSanPham adapterSanPham;
    SanPhamDAO sanPhamDAO;
    RecyclerView rcvProduct;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvProduct=view.findViewById(R.id.rcvProduct);

        sanPhamDAO=new SanPhamDAO(getContext());
        list=sanPhamDAO.getAllProduct(0);
        adapterSanPham=new AdapterSanPham(getContext(), (ArrayList<SanPham>) list);
        rcvProduct.setAdapter(adapterSanPham);
    }
}
