package com.example.duan1_nhom7.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.Adapter.AdapterChoXacNhanAdmin;
import com.example.duan1_nhom7.Adapter.AdapterItem;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;
import com.example.duan1_nhom7.XacNhanDonHangAdmin;

import java.util.List;


public class QLDonHangFragment extends Fragment {

    private static final int REQUEST_CODE_CANCEL_ORDER = 1011;
    private RecyclerView recyclerView;
    private AdapterChoXacNhan adapter;
    private DonHangDAO donHangDAO;
    private DAOHoaDon daoHoaDon;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_q_l_don_hang, container, false);

        recyclerView = view.findViewById(R.id.rcvXacNhanHangAdmin);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getContext());
        List<DonHang> donHangList = donHangDAO.getDonHangsByStatus("1");
        daoHoaDon = new DAOHoaDon(getActivity());

        adapter = new AdapterChoXacNhan(getContext(), donHangList);
        recyclerView.setAdapter(adapter);

        adapter.setOnItemClickListener(new AdapterChoXacNhan.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                // Lấy thông tin hóa đơn từ ID
                HoaDon hoaDon = daoHoaDon.getHoaDonById(donHang.getId_HoaDon());
                if (hoaDon != null) {
                    // Nếu hóa đơn tồn tại, mở activity HuyDonHangActivity
                    Intent intent = new Intent(getContext(), XacNhanDonHangAdmin.class);
                    intent.putExtra("hoaDonXacNhan", hoaDon);
                    startActivityForResult(intent, REQUEST_CODE_CANCEL_ORDER);
                } else {
                    // Nếu không tìm thấy hóa đơn, xử lý trường hợp này
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
        List<DonHang> donHangs = donHangDAO.getDonHangsByStatus("1");
        adapter.setData(donHangs);
        adapter.notifyDataSetChanged();
    }
}