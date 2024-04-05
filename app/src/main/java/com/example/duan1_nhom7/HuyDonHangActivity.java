package com.example.duan1_nhom7;

import android.content.DialogInterface;
import android.content.Intent;
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

import com.example.duan1_nhom7.Adapter.AdapterChoXacNhan;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

public class HuyDonHangActivity extends AppCompatActivity {
    ImageView img;
    TextView name, price, color, content, count, date, pttt, nameUser, phone, location;
    Button btnHuy;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_huy_don_hang);

        count = findViewById(R.id.soLuongSPChoXacNhan);
        img = findViewById(R.id.imgChoXacNhan);
        name = findViewById(R.id.txtTenSPChoXacNhan);
        price = findViewById(R.id.txtGiaSPChoXacNhan);
        color = findViewById(R.id.txtMauSPChoXacNhan);
        btnHuy = findViewById(R.id.btnHuyDonHang);
        content = findViewById(R.id.txtMoTaSPChoXacNhan);
        date = findViewById(R.id.txtDateChoXacNhan);
        pttt = findViewById(R.id.PTTTSPChoXacNhan);
        nameUser = findViewById(R.id.txtNameHuy);
        phone = findViewById(R.id.txtPhoneHuy);
        location = findViewById(R.id.txtLocationHuy);




        DonHang donHang = (DonHang) getIntent().getSerializableExtra("donHang");
        if (donHang != null) {

            int id_sanPham = donHang.getId_sanPham();
            SanPhamDAO sanPhamDAO = new SanPhamDAO(HuyDonHangActivity.this);
            String moTaSP = sanPhamDAO.getMoTaSPById(id_sanPham);
            name.setText(donHang.getTenSP());
            double giaSP = donHang.getGia();
            String mGiaSP = String.format("%,.0f", giaSP);
            price.setText(mGiaSP + "VNĐ");
            count.setText(String.valueOf(donHang.getSoLuong()));
            color.setText(donHang.getMau());
            Picasso.get().load(donHang.getImage()).into(img);
            date.setText(donHang.getNgayMua());
            pttt.setText(donHang.getPttt());
            content.setText(moTaSP);
            nameUser.setText(donHang.getNameUser());
            phone.setText(donHang.getPhone());
            location.setText(donHang.getLocation());
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

                        DonHangDAO donHangDAO = new DonHangDAO(HuyDonHangActivity.this);
                        DonHang donHang = (DonHang) getIntent().getSerializableExtra("donHang");
                        if (donHang != null) {
                            // Cập nhật trạng thái của đơn hàng thành 4 (đã hủy)
                            donHang.setStatus("4");
                            // Sử dụng DonHangDAO để cập nhật trạng thái của đơn hàng trong cơ sở dữ liệu
                            int rowsAffected = donHangDAO.updateDonHangStatus(donHang);
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
}