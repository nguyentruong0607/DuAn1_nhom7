package com.example.duan1_nhom7;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
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
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.HoaDon;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class XacNhanDonHangAdmin extends AppCompatActivity {
    TextView date, pttt, nameUser, phone, location, tongTienHang, tongThanhToan;
    Button btnXacNhan;
    DonHangDAO donHangDAO;
    AdapterItem adapter;
    DAOHoaDon daoHoaDon;
    RecyclerView rcv;
    int id_HoaDon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xac_nhan_don_hang_admin);

        btnXacNhan = findViewById(R.id.btnXacNhanAdmin);
        date = findViewById(R.id.txtDateXacNhanAdmin);
        pttt = findViewById(R.id.PTTTSPXacNhanAdmin);
        nameUser = findViewById(R.id.txtNameXacNhanAdmin);
        phone = findViewById(R.id.txtPhoneXacNhanAdmin);
        location = findViewById(R.id.txtLocationXacNhanAdmin);
        tongThanhToan = findViewById(R.id.txtTongThanhToanXacNhanAdmin);
        tongTienHang = findViewById(R.id.txtTongTienHangXacNhanAdmin);
        rcv = findViewById(R.id.rcv_XacNhanAdmin);
        donHangDAO = new DonHangDAO(XacNhanDonHangAdmin.this);
        daoHoaDon = new DAOHoaDon(XacNhanDonHangAdmin.this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        rcv.setLayoutManager(layoutManager);

        HoaDon hoaDon = (HoaDon) getIntent().getSerializableExtra("hoaDonXacNhan");
        if (hoaDon != null) {

            id_HoaDon = hoaDon.getId_HoaDon();

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

            adapter = new AdapterItem(XacNhanDonHangAdmin.this, list);
            rcv.setAdapter(adapter);
            adapter.notifyDataSetChanged();


        }

        // Thêm sự kiện click cho nút hủy đơn hàng
        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }

            private void showConfirmationDialog() {
                AlertDialog.Builder builder = new AlertDialog.Builder(XacNhanDonHangAdmin.this);
                builder.setTitle("Xác nhận đơn hàng");
                builder.setMessage("Bạn có chắc chắn muốn xác nhận đơn hàng này?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (hoaDon != null) {

                            hoaDon.setStatus("2");



                            int rowsAffected = daoHoaDon.updateHoaDonStatus(id_HoaDon, "2");
                            if (rowsAffected > 0) {
                                Toast.makeText(XacNhanDonHangAdmin.this, "Xác nhận đơn hàng thành công", Toast.LENGTH_SHORT).show();
                                setResult(RESULT_OK);
                                finish();
                            } else {
                                Toast.makeText(XacNhanDonHangAdmin.this, "Xác nhận đơn hàng thất bại", Toast.LENGTH_SHORT).show();
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