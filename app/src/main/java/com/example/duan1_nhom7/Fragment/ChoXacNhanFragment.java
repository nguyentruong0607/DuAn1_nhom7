package com.example.duan1_nhom7.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.HuyDonHangActivity;
import com.example.duan1_nhom7.R;
import java.util.List;

public class ChoXacNhanFragment extends Fragment {

    private static final int REQUEST_CODE_CANCEL_ORDER = 1001;
    private RecyclerView recyclerView;
    private AdapterChoXacNhan adapter;
    private DonHangDAO donHangDAO;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cho_xac_nhan, container, false);

        recyclerView = view.findViewById(R.id.rcv_ChoXacNhan);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        donHangDAO = new DonHangDAO(getContext());
        List<DonHang> donHangList = donHangDAO.getDonHangByStatus("1");

        adapter = new AdapterChoXacNhan(getContext(), donHangList);
        recyclerView.setAdapter(adapter);


        adapter.setOnItemClickListener(new AdapterChoXacNhan.OnItemClickListener() {
            @Override
            public void onItemClick(DonHang donHang) {
                Intent intent = new Intent(getContext(), HuyDonHangActivity.class);
                intent.putExtra("donHang", donHang);
                startActivityForResult(intent, REQUEST_CODE_CANCEL_ORDER);
            }
        });


        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE_CANCEL_ORDER) {
            if (resultCode == Activity.RESULT_OK) {
                // Load lại dữ liệu và cập nhật RecyclerView
                loadDonHangData();
            }
        }
    }

    private void loadDonHangData() {
        // Cập nhật lại danh sách đơn hàng chờ xác nhận
        List<DonHang> donHangList = donHangDAO.getDonHangByStatus("1");
        adapter.setData(donHangList);
        adapter.notifyDataSetChanged();
    }



}
