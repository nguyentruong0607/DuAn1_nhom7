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
import com.example.duan1_nhom7.Adapter.AdapterDaHuy;
import com.example.duan1_nhom7.Adapter.AdapterDanGiao;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DaNhanHangActivity;
import com.example.duan1_nhom7.DonHuyActivity;
import com.example.duan1_nhom7.R;

import java.util.List;


public class DaHuyFragment extends Fragment {

    private RecyclerView recyclerView;
    private AdapterDaHuy adapter;
    private DonHangDAO donHangDAO;
    int idUser;

    UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        View view = inflater.inflate(R.layout.fragment_da_huy, container, false);

        recyclerView = view.findViewById(R.id.rcv_DaHuy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userDAO = new UserDAO(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuDangNhap", MODE_PRIVATE);
        String userName = sharedPreferences.getString("TK", "");
        idUser = Integer.parseInt(userDAO.getIdUser(userName));

        donHangDAO = new DonHangDAO(getContext());
        List<DonHang> donHangList = donHangDAO.getDonHangByIdUserAndStatus(idUser,"4");

        adapter = new AdapterDaHuy(getContext(), donHangList);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new AdapterDaHuy.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                Intent intent = new Intent(getContext(), DonHuyActivity.class);
                intent.putExtra("hanghuy", donHang);
               startActivity(intent);
            }
        });

        return view;
    }
}