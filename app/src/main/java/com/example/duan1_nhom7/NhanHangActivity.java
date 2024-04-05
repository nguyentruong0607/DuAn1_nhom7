package com.example.duan1_nhom7;

import android.os.Bundle;
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

public class NhanHangActivity extends AppCompatActivity {
    ImageView img;
    TextView name, price, color, content, count, date, pttt, nameUser, phone, location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nhan_hang);

        count = findViewById(R.id.soLuongSPNhanHang);
        img = findViewById(R.id.imgNhanHanggg);
        name = findViewById(R.id.txtTenSPNhanHang);
        price = findViewById(R.id.txtGiaSPNhanHang);
        color = findViewById(R.id.txtMauSPNhanHang);

        content = findViewById(R.id.txtMoTaSPNhanHang);
        date = findViewById(R.id.txtDateNhanHanggg);
        pttt = findViewById(R.id.PTTTSPNhanHang);
        nameUser = findViewById(R.id.txtNameNhanHangggg);
        phone = findViewById(R.id.txtPhoneNhanHangggg);
        location = findViewById(R.id.txtLocationNhanHangggg);


        DonHang donHang = (DonHang) getIntent().getSerializableExtra("hanggg");
        if (donHang != null) {

            int id_sanPham = donHang.getId_sanPham();
            SanPhamDAO sanPhamDAO = new SanPhamDAO(NhanHangActivity.this);
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