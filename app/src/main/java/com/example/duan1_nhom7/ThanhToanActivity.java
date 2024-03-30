package com.example.duan1_nhom7;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.StrictMode;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterHoaDon;
import com.example.duan1_nhom7.DAO.CreateOrder;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.Zalo.AppInfo;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {

    RecyclerView rcvHoaDon;
    Button btnPay;
    TextView txtTongTienHang, txtTongThanhToan, txtTongThanhToan2, txtPTTT, dateDatHang, dateNhanHang;
    EditText edLocation;
    String tongTien;
    boolean isPaymentMethodSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        btnPay = findViewById(R.id.btnThanhToan);
        txtTongTienHang = findViewById(R.id.txtTongTienHangThanhToan);
        txtTongThanhToan = findViewById(R.id.txtTongThanhToan);
        txtTongThanhToan2 = findViewById(R.id.txtTongThanhToan2);
        txtPTTT = findViewById(R.id.txtPhuongThucTT);
        dateDatHang = findViewById(R.id.txtDateDatHang);
        dateNhanHang = findViewById(R.id.txtDateNhanHang);
        edLocation = findViewById(R.id.edLocationGiaoHang);
        rcvHoaDon = findViewById(R.id.rcv_SPThanhToan);

        // Tạo định dạng ngày
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        String currentDate = sdf.format(calendar.getTime());
        dateDatHang.setText(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateAfterThreeDays = sdf.format(calendar.getTime());
        dateNhanHang.setText(dateAfterThreeDays);

        UserDAO daoDiaChi = new UserDAO(this);

        // Lấy danh sách địa chỉ từ DAO
        List<String> diaChiList = daoDiaChi.getDiaChi();

        // Hiển thị địa chỉ đầu tiên trong danh sách (hoặc một giá trị mặc định khác nếu cần)
        if (!diaChiList.isEmpty()) {
            String firstAddress = diaChiList.get(0);
            edLocation.setText(firstAddress);
        }

        Intent intent = getIntent();
        ArrayList<GioHang> listGioHang = null;
        if (intent != null) {
            listGioHang = (ArrayList<GioHang>) intent.getSerializableExtra("list_gio_hang");
        }

        // Kiểm tra xem listGioHang có dữ liệu không
        if (listGioHang != null && !listGioHang.isEmpty()) {
            // Tạo Adapter mới và gán cho RecyclerView
            AdapterHoaDon adapter = new AdapterHoaDon(this, listGioHang);
            rcvHoaDon.setAdapter(adapter);
            rcvHoaDon.setLayoutManager(new LinearLayoutManager(this));
        } else {
            // Nếu không có dữ liệu, hiển thị thông báo cho người dùng
            Toast.makeText(this, "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
        }


        // Tien
        String tongTienWithDot = getIntent().getStringExtra("tong_tien");
        txtTongTienHang.setText(tongTienWithDot);

        // Loại bỏ dấu chấm và ký tự không phải số
        String tongTienWithoutDot = tongTienWithDot.replaceAll("[^\\d]", "");
        int tongTienInt = Integer.parseInt(tongTienWithoutDot);
        int tongTienPlus30000 = tongTienInt + 30000;
        tongTien = String.valueOf(tongTienPlus30000);
        String formattedTongTien = String.format("%,.0f VNĐ", (float) tongTienPlus30000);
        txtTongThanhToan.setText(formattedTongTien);
        txtTongThanhToan2.setText(formattedTongTien);

        StrictMode.ThreadPolicy policy = new
                StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        ZaloPaySDK.init(AppInfo.APP_ID, Environment.SANDBOX);

        txtPTTT.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(ThanhToanActivity.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                dialog.setContentView(R.layout.dialog_phuong_thuc_tt);

                Window window = dialog.getWindow();
                if (window == null) {
                    return;
                }

                window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

                WindowManager.LayoutParams layoutParams = window.getAttributes();
                layoutParams.gravity = Gravity.CENTER;
                window.setAttributes(layoutParams);

                Button btnZalo = dialog.findViewById(R.id.btnZalopay);
                Button btnNhanHang = dialog.findViewById(R.id.btnTTKhiNhanHang);

                btnZalo.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        requestZalo(tongTien);
                        dialog.dismiss();

                    }
                });

                btnNhanHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtPTTT.setText("Thanh toán khi nhận hàng");
                        isPaymentMethodSelected = true; // Cập nhật biến boolean khi phương thức thanh toán được chọn
                        dialog.dismiss();
                        setPayButtonListener("Thanh toán khi nhận hàng");
                    }
                });

                dialog.show();
            }
        });


        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentMethod = txtPTTT.getText().toString();
                if (!isPaymentMethodSelected) {
                    // Hiển thị Toast thông báo yêu cầu chọn phương thức thanh toán trước khi thanh toán
                    Toast.makeText(ThanhToanActivity.this, "Chọn 1 phương thức thanh toán trước khi đặt hàng", Toast.LENGTH_SHORT).show();
                } else {
                    // Phương thức thanh toán đã được chọn, tiến hành xử lý thanh toán
                    switch (paymentMethod) {
                        case "Zalopay":
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán bằng Zalopay", Toast.LENGTH_SHORT).show();
                            break;
                        case "Thanh toán khi nhận hàng":
                            // Xử lý thanh toán khi nhận hàng
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán khi nhận hàng", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });
    }

    private void setPayButtonListener(String paymentMethod) {
        btnPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String paymentMethod = txtPTTT.getText().toString();
                if (paymentMethod.equals("Chọn phương thức thanh toán")) {
                    // Hiển thị Toast thông báo yêu cầu chọn phương thức thanh toán trước khi thanh toán
                    Toast.makeText(ThanhToanActivity.this, "Chọn 1 phương thức thanh toán trước khi thanh toán", Toast.LENGTH_SHORT).show();
                } else {
                    // Phương thức thanh toán đã được chọn, tiến hành xử lý thanh toán
                    switch (paymentMethod) {
                        case "Zalopay":
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán bằng Zalopay", Toast.LENGTH_SHORT).show();
                            break;
                        case "Thanh toán khi nhận hàng":
                            // Xử lý thanh toán khi nhận hàng
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán khi nhận hàng", Toast.LENGTH_SHORT).show();
                            break;
                    }
                }
            }
        });

    }

    private void requestZalo(String tongTien) {
        CreateOrder orderApi = new CreateOrder();
        try {
            JSONObject data = orderApi.createOrder(tongTien);
            String code = data.getString("return_code");

            if (code.equals("1")) {
                String token = data.getString("zp_trans_token");

                ZaloPaySDK.getInstance().payOrder(ThanhToanActivity.this, token, "demozpdk://app", new PayOrderListener() {
                    @Override
                    public void onPaymentSucceeded(final String transactionId, final String transToken, final String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán thành công", Toast.LENGTH_SHORT).show();
                        txtPTTT.setText("Zalopay");
                        isPaymentMethodSelected = true;
                        setPayButtonListener("Zalopay");
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán đã bị hủy", Toast.LENGTH_SHORT).show();
                        txtPTTT.setText("Chọn phương thức thanh toán >");
                    }

                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
                        txtPTTT.setText("Chọn phương thức thanh toán >");
                    }
                });
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        ZaloPaySDK.getInstance().onResult(intent);
    }
}