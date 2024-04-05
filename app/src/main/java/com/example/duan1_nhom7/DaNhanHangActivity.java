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

import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class DaNhanHangActivity extends AppCompatActivity {

    private ImageView img;
    private TextView name, price, color, content, count, date, pttt, nameUser, phone, location;
    private Button btnNhanHang;
    String currentDate, dateAfterThreeDays;

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
        nameUser = findViewById(R.id.txtNameNhanHang);
        phone = findViewById(R.id.txtPhoneNhanHang);
        location = findViewById(R.id.txtLocationNhanHang);

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        currentDate = sdf.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_MONTH, 3);
         dateAfterThreeDays = sdf.format(calendar.getTime());


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
            nameUser.setText(donHang.getNameUser());
            phone.setText(donHang.getPhone());
            location.setText(donHang.getLocation());
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
                    donHang.setNgayMua(dateAfterThreeDays);
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

    public static class DonHuyActivity extends AppCompatActivity {

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            EdgeToEdge.enable(this);
            setContentView(R.layout.activity_don_huy);
            ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }
    }
}
