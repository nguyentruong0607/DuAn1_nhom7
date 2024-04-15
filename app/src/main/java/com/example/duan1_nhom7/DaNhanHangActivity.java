package com.example.duan1_nhom7;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AlertDialog;
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
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class DaNhanHangActivity extends AppCompatActivity {

    TextView date, pttt, nameUser, phone, location, tongTienHang, tongThanhToan;
    private Button btnNhanHang;
    DonHangDAO donHangDAO;
    DAOHoaDon daoHoaDon;
    String currentDate, dateAfterThreeDays;
    RecyclerView rcv;
    AdapterItem adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_nhan_hang);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        currentDate = sdf.format(calendar.getTime());
        calendar.add(Calendar.DAY_OF_MONTH, 3);
         dateAfterThreeDays = sdf.format(calendar.getTime());

        btnNhanHang = findViewById(R.id.btnNhanHang2);
        date = findViewById(R.id.txtDateNhanHang2);
        pttt = findViewById(R.id.PTTTSPNhanHang2);
        nameUser = findViewById(R.id.txtNameNhanHang);
        phone = findViewById(R.id.txtPhoneNhanHang);
        location = findViewById(R.id.txtLocationNhanHang);
        tongThanhToan = findViewById(R.id.txtTongThanhToanNhanHangActivity);
        tongTienHang = findViewById(R.id.txtTongTienNhanHangActivity);
        rcv = findViewById(R.id.rcv_NhanHang);
        donHangDAO = new DonHangDAO(DaNhanHangActivity.this);
        daoHoaDon = new DAOHoaDon(DaNhanHangActivity.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hang");
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

            adapter = new AdapterItem(DaNhanHangActivity.this, list);
            rcv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

        btnNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }

            private void showConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(DaNhanHangActivity.this);
                builder.setMessage("Bạn đã nhận được đơn hàng này?");
                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (hoaDon != null) {

                            hoaDon.setStatus("3");

                            Calendar calendar = Calendar.getInstance();
                            calendar.add(Calendar.DAY_OF_MONTH, 2);
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
                            String ngayNhan = sdf.format(calendar.getTime());

                            // Cập nhật ngày hủy
                            hoaDon.setNgayMua(ngayNhan);

                            int rowsAffected = daoHoaDon.updateHoaDonStatusAndCancelDate(hoaDon);
                            if (rowsAffected > 0) {
                                Toast.makeText(DaNhanHangActivity.this, "Nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(DaNhanHangActivity.this, "Thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
                builder.setNegativeButton("Cancle", null);
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
