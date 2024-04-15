package com.example.duan1_nhom7;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.Adapter.AdapterHoaDon;
import com.example.duan1_nhom7.Adapter.AdapterItem;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HuyDonHangActivity extends AppCompatActivity {

    TextView date, pttt, nameUser, phone, location, tongTienHang, tongThanhToan;
    Button btnHuy;
    DonHangDAO donHangDAO;
    AdapterItem adapter;
    DAOHoaDon daoHoaDon;
    RecyclerView rcv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huy_don_hang);


        btnHuy = findViewById(R.id.btnHuyDonHang);
        date = findViewById(R.id.txtDateChoXacNhan);
        pttt = findViewById(R.id.PTTTSPChoXacNhan);
        nameUser = findViewById(R.id.txtNameHuy);
        phone = findViewById(R.id.txtPhoneHuy);
        location = findViewById(R.id.txtLocationHuy);
        tongThanhToan = findViewById(R.id.txtTongThanhToanHuyActivity);
        tongTienHang = findViewById(R.id.txtTongTienHangHuyActivity);
        rcv = findViewById(R.id.rcv_huyDonActivityy);
        donHangDAO = new DonHangDAO(HuyDonHangActivity.this);
        daoHoaDon = new DAOHoaDon(HuyDonHangActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hoaDon");
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

            adapter = new AdapterItem(HuyDonHangActivity.this, list);
            rcv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

        // Thêm sự kiện click cho nút hủy đơn hàng
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }

            private void showConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(HuyDonHangActivity.this);
                builder.setTitle("Xác nhận hủy đơn hàng");
                builder.setMessage("Bạn có chắc chắn muốn hủy đơn hàng này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (hoaDon != null) {
                            // Cập nhật trạng thái của đơn hàng thành 4 (đã hủy)
                            hoaDon.setStatus("4");

                            Calendar calendar = Calendar.getInstance();
                            // Cộng thêm 1 ngày
                            calendar.add(Calendar.DAY_OF_MONTH, 1);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            String ngayHuy = sdf.format(calendar.getTime());


                            hoaDon.setNgayMua(ngayHuy);

                            int rowsAffected = daoHoaDon.updateHoaDonStatusAndCancelDate(hoaDon);
                            if (rowsAffected > 0) {
                                Toast.makeText(HuyDonHangActivity.this, "Hủy đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);

                                finish();
                            } else {
                                Toast.makeText(HuyDonHangActivity.this, "Hủy đơn hàng thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Không", null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }




        });



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