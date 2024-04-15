package com.example.duan1_nhom7.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.Adapter.AdapterDaGiao;
import com.example.duan1_nhom7.Adapter.AdapterDanGiao;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.example.duan1_nhom7.DaNhanHangActivity;
import com.example.duan1_nhom7.NhanHangActivity;
import com.example.duan1_nhom7.R;

import java.util.List;

public class DaGiaoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterChoXacNhan adapter;
    private DonHangDAO donHangDAO;
    private DAOHoaDon daoHoaDon;
    int idUser;

    UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_giao, container, false);

        recyclerView = view.findViewById(R.id.rcv_DaGiao);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getActivity());
        daoHoaDon = new DAOHoaDon(getActivity());
        userDAO = new UserDAO(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuDangNhap", MODE_PRIVATE);
        String userName = sharedPreferences.getString("TK", "");
        idUser = Integer.parseInt(userDAO.getIdUser(userName));


        updateData();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();

        updateData();
    }

    private void updateData() {
        List<DonHang> donHangs = donHangDAO.getDonHangsByUserAndStatus(idUser, "3");
        adapter = new AdapterChoXacNhan(getContext(), donHangs);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterChoXacNhan.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                HoaDon hoaDon = daoHoaDon.getHoaDonById(donHang.getId_HoaDon());
                Intent intent = new Intent(getContext(), NhanHangActivity.class);
                intent.putExtra("hangggg", hoaDon);
                startActivity(intent);
            }
        });
    }
}
