package com.example.duan1_nhom7.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
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
import com.example.duan1_nhom7.Adapter.AdapterDanGiao;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.example.duan1_nhom7.DaNhanHangActivity;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;

import java.util.List;

public class DangGiaoFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDanGiao adapter;
    private DonHangDAO donHangDAO;
    private DAOHoaDon daoHoaDon;
    private static final int REQUEST_CODE_RECEIVE_ORDER = 1005;
    int idUser;

    UserDAO userDAO;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_dang_giao, container, false);

        recyclerView = view.findViewById(R.id.rcv_DangGiao);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userDAO = new UserDAO(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuDangNhap", MODE_PRIVATE);
        String userName = sharedPreferences.getString("TK", "");
        idUser = Integer.parseInt(userDAO.getIdUser(userName));


        daoHoaDon = new DAOHoaDon(getActivity());
        donHangDAO = new DonHangDAO(getActivity());
        List<DonHang> donHangs = donHangDAO.getDonHangsByUserAndStatus(idUser, "2");
       adapter = new AdapterDanGiao(getContext(), donHangs);
       recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterDanGiao.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                HoaDon hoaDon = daoHoaDon.getHoaDonById(donHang.getId_HoaDon());
                Intent intent = new Intent(getContext(), DaNhanHangActivity.class);
                intent.putExtra("hang", hoaDon);
                startActivityForResult(intent, REQUEST_CODE_RECEIVE_ORDER);
            }
        });


        return view;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_RECEIVE_ORDER) {
            if (resultCode == Activity.RESULT_OK) {
                // Load lại dữ liệu và cập nhật RecyclerView
                loadDonHangData();
            }
        }
    }

    private void loadDonHangData() {
        List<DonHang> donHangs = donHangDAO.getDonHangsByUserAndStatus(idUser, "2");
        adapter.setData(donHangs);
        adapter.notifyDataSetChanged();
    }



}