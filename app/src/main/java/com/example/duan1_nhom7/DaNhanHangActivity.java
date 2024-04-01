package com.example.duan1_nhom7;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.squareup.picasso.Picasso;

public class DaNhanHangActivity extends AppCompatActivity {

    private ImageView img;
    private TextView name, price, color, content, count, date, pttt;
    private Button btnNhanHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_da_nhan_hang);

        count = findViewById(R.id.soLuongSPDangGiaoHang);
        img = findViewById(R.id.imgDangGiaoHang);
        name = findViewById(R.id.txtTenSPDangGiaoHang);
        price = findViewById(R.id.txtGiaSPDangGiaoHang);
        color = findViewById(R.id.txtMauSPDangGiaoHang);
        btnNhanHang = findViewById(R.id.btnNhanDonHangThanhCong);
        content = findViewById(R.id.txtMoTaSPDangGiaoHang);
        date = findViewById(R.id.txtDateDangGiaoHang);
        pttt = findViewById(R.id.PTTTSPDangGiaoHang);

        DonHang donHang = (DonHang) getIntent().getSerializableExtra("hang");
        if (donHang != null) {
            int id_sanPham = donHang.getId_sanPham();
            SanPhamDAO sanPhamDAO = new SanPhamDAO(DaNhanHangActivity.this);
            String moTaSP = sanPhamDAO.getMoTaSPById(id_sanPham);
            name.setText(donHang.getTenSP());
            double giaSP = donHang.getGia();
            String mGiaSP = String.format("%,.0f", giaSP);
            price.setText(mGiaSP + "VNĐ");
            count.setText(String.valueOf(donHang.getSoLuong()));
            color.setText(donHang.getMau());
            Picasso.get().load(donHang.getImage()).into(img);
            content.setText(moTaSP);
            date.setText(donHang.getNgayMua());
            pttt.setText(donHang.getPttt());
        }

        btnNhanHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmationDialog();
            }
        });
    }

    private void showConfirmationDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(DaNhanHangActivity.this);
        builder.setTitle("Xác nhận bạn đã nhận được đơn hàng");
        builder.setMessage("Bạn đã nhận được sản phẩm?");
        builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                DonHang donHang = (DonHang) getIntent().getSerializableExtra("hang");
                DonHangDAO donHangDAO = new DonHangDAO(DaNhanHangActivity.this);
                if (donHang != null) {

                    donHang.setStatus("3"); // Cập nhật trạng thái đơn hàng thành đã nhận (status = 3)
                    int rowsAffected = donHangDAO.updateDonHangStatus(donHang);
                    if (rowsAffected > 0) {
                        Toast.makeText(DaNhanHangActivity.this, "Đã nhận hàng", Toast.LENGTH_SHORT).show();
                        setResult(RESULT_OK);
                        finish();

                    } else {
                        Toast.makeText(DaNhanHangActivity.this, "Cập nhật trạng thái không thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
        builder.setNegativeButton("Thoát", null);
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}
