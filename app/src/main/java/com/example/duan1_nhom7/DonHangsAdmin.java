package com.example.duan1_nhom7;

import android.os.Bundle;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterItem;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class DonHangsAdmin extends AppCompatActivity {

    TextView date, pttt, nameUser, phone, location, tongTienHang, tongThanhToan;
    RecyclerView rcv;
    DonHangDAO donHangDAO;
    AdapterItem adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_don_hangs_admin);

        date = findViewById(R.id.txtDateDonHangsAdmin);
        pttt = findViewById(R.id.PTTTSPDonHangsAdmin);
        nameUser = findViewById(R.id.txtNameDonHangsAdmin);
        phone = findViewById(R.id.txtPhoneDonHangsAdmin);
        location = findViewById(R.id.txtLocationDonHangsAdmin);
        tongThanhToan = findViewById(R.id.txtTongThanhToanDonHangsAdmin);
        tongTienHang = findViewById(R.id.txtTongTienHangDonHangsAdmin);
        rcv = findViewById(R.id.rcv_DonHangsAdmin);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);
        donHangDAO = new DonHangDAO(this);

        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("donhangs");
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

            adapter = new AdapterItem(DonHangsAdmin.this, list);
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