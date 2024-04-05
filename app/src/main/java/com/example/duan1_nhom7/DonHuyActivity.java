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

import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.squareup.picasso.Picasso;

public class DonHuyActivity extends AppCompatActivity {
    private ImageView img;
    private TextView name, price, color, content, count, date, pttt, nameUser, phone, location;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_don_huy);

        count = findViewById(R.id.soLuongSPDonHuy);
        img = findViewById(R.id.imgDonHuy);
        name = findViewById(R.id.txtTenSPDonHuy);
        price = findViewById(R.id.txtGiaSPDonHuy);
        color = findViewById(R.id.txtMauSPDonHuy);

        content = findViewById(R.id.txtMoTaSPDonHuy);
        date = findViewById(R.id.txtDateDonHuy);
        pttt = findViewById(R.id.PTTTSPDonHuy);
        nameUser = findViewById(R.id.txtNameDonHuy);
        phone = findViewById(R.id.txtPhoneDonHuy);
        location = findViewById(R.id.txtLocationNhanDonHuy);

        DonHang donHang = (DonHang) getIntent().getSerializableExtra("hanghuy");
        if (donHang != null) {

            int id_sanPham = donHang.getId_sanPham();
            SanPhamDAO sanPhamDAO = new SanPhamDAO(DonHuyActivity.this);
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

    }
}