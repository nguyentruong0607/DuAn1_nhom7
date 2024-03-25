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
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom7.DTO.CreateOrder;
import com.example.duan1_nhom7.zalo.AppInfo;

import org.json.JSONObject;

import vn.zalopay.sdk.Environment;
import vn.zalopay.sdk.ZaloPayError;
import vn.zalopay.sdk.ZaloPaySDK;
import vn.zalopay.sdk.listeners.PayOrderListener;

public class ThanhToanActivity extends AppCompatActivity {

    Button btnPay;
    TextView txtTongTienHang, txtTongThanhToan, txtTongThanhToan2, txtPTTT;
    String tongTien;
    boolean isPaymentMethodSelected = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan2);

        btnPay = findViewById(R.id.btnThanhToan);
        txtTongTienHang = findViewById(R.id.txtTongTienHangThanhToan);
        txtTongThanhToan = findViewById(R.id.txtTongThanhToan);
        txtTongThanhToan2 = findViewById(R.id.txtTongThanhToan2);
        txtPTTT = findViewById(R.id.txtPhuongThucTT);

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
                        txtPTTT.setText("Zalopay");
                        isPaymentMethodSelected = true; // Cập nhật biến boolean khi phương thức thanh toán được chọn
                        dialog.dismiss();
                        setPayButtonListener("Zalopay");
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
                if(!isPaymentMethodSelected) {
                    // Hiển thị Toast thông báo yêu cầu chọn phương thức thanh toán trước khi thanh toán
                    Toast.makeText(ThanhToanActivity.this, "Chọn 1 phương thức thanh toán trước khi thanh toán", Toast.LENGTH_SHORT).show();
                } else {
                    // Phương thức thanh toán đã được chọn, tiến hành xử lý thanh toán
                    switch (paymentMethod) {
                        case "Zalopay":
                            requestZalo(tongTien);
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
                if(paymentMethod.equals("Chọn phương thức thanh toán")) {
                    // Hiển thị Toast thông báo yêu cầu chọn phương thức thanh toán trước khi thanh toán
                    Toast.makeText(ThanhToanActivity.this, "Chọn 1 phương thức thanh toán trước khi thanh toán", Toast.LENGTH_SHORT).show();
                } else {
                    // Phương thức thanh toán đã được chọn, tiến hành xử lý thanh toán
                    switch (paymentMethod) {
                        case "Zalopay":
                            requestZalo(tongTien);
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
                    }

                    @Override
                    public void onPaymentCanceled(String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán đã bị hủy", Toast.LENGTH_SHORT).show();
                    }
                    @Override
                    public void onPaymentError(ZaloPayError zaloPayError, String zpTransToken, String appTransID) {
                        Toast.makeText(ThanhToanActivity.this, "Thanh toán thất bại", Toast.LENGTH_SHORT).show();
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
