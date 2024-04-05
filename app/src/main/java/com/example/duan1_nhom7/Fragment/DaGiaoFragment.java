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

import com.example.duan1_nhom7.Adapter.AdapterDaGiao;
import com.example.duan1_nhom7.Adapter.AdapterDanGiao;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DaNhanHangActivity;
import com.example.duan1_nhom7.NhanHangActivity;
import com.example.duan1_nhom7.R;

import java.util.List;

public class DaGiaoFragment extends Fragment {
    private RecyclerView recyclerView;
    private AdapterDaGiao adapter;
    private DonHangDAO donHangDAO;
    int idUser;

    UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_da_giao, container, false);

        recyclerView = view.findViewById(R.id.rcv_DaGiao);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getContext());
        userDAO = new UserDAO(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuDangNhap", MODE_PRIVATE);
        String userName = sharedPreferences.getString("TK", "");
        idUser = Integer.parseInt(userDAO.getIdUser(userName));

        // Đảm bảo rằng mỗi khi fragment được hiển thị, dữ liệu sẽ được cập nhật lại
        updateData();



        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        // Cập nhật dữ liệu mỗi khi fragment được hiển thị
        updateData();
    }

    private void updateData() {
        List<DonHang> donHangList = donHangDAO.getDonHangByIdUserAndStatus(idUser,"3");
        adapter = new AdapterDaGiao(getContext(), donHangList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterDaGiao.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                Intent intent = new Intent(getContext(), NhanHangActivity.class);
                intent.putExtra("hanggg", donHang);
startActivity(intent);
            }
        });
    }
}
