package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.Top10Adapter;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.Top10BanChay;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;
import java.util.List;

public class ThongKe_Fragment extends Fragment {
    private RecyclerView recyclerView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.top10_layout, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerView = view.findViewById(R.id.rcv_top10);
        SanPhamDAO sanPhamDAO = new SanPhamDAO(getContext());
        List<Top10BanChay> list = new ArrayList<>();
        list = sanPhamDAO.getTop10BestSellingProducts(getContext());
        Top10Adapter top10Adapter = new Top10Adapter(list,getContext());
        recyclerView.setAdapter(top10Adapter);
    }
}
