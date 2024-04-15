package com.example.duan1_nhom7;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterItem;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NhanHangActivity extends AppCompatActivity {
    TextView date, pttt, nameUser, phone, location, tongTienHang, tongThanhToan;

    DonHangDAO donHangDAO;
    DAOHoaDon daoHoaDon;
    RecyclerView rcv;
    AdapterItem adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_hang);


        date = findViewById(R.id.txtDateNhanHang3);
        pttt = findViewById(R.id.PTTTSPNhanHang3);
        nameUser = findViewById(R.id.txtNameNhanHang3);
        phone = findViewById(R.id.txtPhoneNhanHang3);
        location = findViewById(R.id.txtLocationNhanHang3);
        tongThanhToan = findViewById(R.id.txtTongThanhToanNhanHangActivity3);
        tongTienHang = findViewById(R.id.txtTongTienNhanHangActivity3);
        rcv = findViewById(R.id.rcv_NhanHang3);
        donHangDAO = new DonHangDAO(NhanHangActivity.this);
        daoHoaDon = new DAOHoaDon(NhanHangActivity.this);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hangggg");
        if (hoaDon != null) {

            int id_HoaDon = hoaDon.getId_HoaDon();

            int giaSP = hoaDon.getTongTien();
            String mGiaSP = String.format("%,.0f", (float) giaSP);
            tongTienHang.setText(mGiaSP + "VNĐ");
            tongThanhToan.setText(mGiaSP + "VNĐ");


            date.setText(convertDateFormat(hoaDon.getNgayMua()));
            pttt.setText(hoaDon.getPttt());
            nameUser.setText(hoaDon.getNameUser());
            phone.setText(hoaDon.getPhone());
            location.setText(hoaDon.getLocation());

            List<DonHang> list = donHangDAO.getDonHangsByIdHoaDon(id_HoaDon);

            adapter = new AdapterItem(NhanHangActivity.this, list);
            rcv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

    }
    private String convertDateFormat(String dateString) {
        SimpleDateFormat sdfInput = new SimpleDateFormat("yyyy/MM/dd");
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdfInput.parse(dateString);
            return sdfOutput.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}