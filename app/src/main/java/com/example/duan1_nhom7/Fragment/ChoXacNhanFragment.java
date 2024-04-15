package com.example.duan1_nhom7.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;
import java.util.List;

public class ChoXacNhanFragment extends Fragment {

    private static final int REQUEST_CODE_CANCEL_ORDER = 1001;
    private RecyclerView recyclerView;
    private AdapterChoXacNhan adapter;
    private DonHangDAO donHangDAO;
    private DAOHoaDon daoHoaDon;
    int idUser;

    UserDAO userDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);

        recyclerView = view.findViewById(R.id.rcv_ChoXacNhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        userDAO = new UserDAO(getActivity());
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuDangNhap", MODE_PRIVATE);
        String userName = sharedPreferences.getString("TK", "");
        idUser = Integer.parseInt(userDAO.getIdUser(userName));

        donHangDAO = new DonHangDAO(getActivity());
        daoHoaDon = new DAOHoaDon(getActivity());
        List<DonHang> donHangs = donHangDAO.getDonHangsByUserAndStatus(idUser, "1");

        adapter = new AdapterChoXacNhan(getActivity(), donHangs);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterChoXacNhan.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {

                HoaDon hoaDon = daoHoaDon.getHoaDonById(donHang.getId_HoaDon());
                if (hoaDon != null) {
                    // Nếu hóa đơn tồn tại, mở activity HuyDonHangActivity
                    Intent intent = new Intent(getContext(), HuyDonHangActivity.class);
                    intent.putExtra("hoaDon", hoaDon);
                    startActivityForResult(intent, REQUEST_CODE_CANCEL_ORDER);
                } else {
                    // Nếu không tìm thấy hóa đơn
                }
            }
        });

        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CANCEL_ORDER && resultCode == Activity.RESULT_OK) {
            // Cập nhật lại dữ liệu khi kết thúc hoạt động hủy đơn hàng
            refreshData();
        }
    }

    private void refreshData() {
        // Lấy lại danh sách đơn hàng cần xác nhận và cập nhật RecyclerView
        List<DonHang> donHangs = donHangDAO.getDonHangsByUserAndStatus(idUser, "1");
        adapter.setData(donHangs);
        adapter.notifyDataSetChanged();
    }
}
