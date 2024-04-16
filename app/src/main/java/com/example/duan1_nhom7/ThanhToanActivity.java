package com.example.duan1_nhom7;

import android.app.Dialog;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
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
import java.util.List;
import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterHoaDon;
import com.example.duan1_nhom7.DAO.CreateOrder;
import com.example.duan1_nhom7.DAO.DAOHoaDon;
import com.example.duan1_nhom7.DAO.DonHangDAO;
import com.example.duan1_nhom7.DAO.GioHangDAO;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.DonHang;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.HoaDon;
import com.example.duan1_nhom7.Fragment.HomeFragment;
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
    EditText edLocation, edName, edPhone;
    String currentDate;
    boolean isPaymentMethodSelected = false;
    DonHangDAO donHangDAO;
    UserDAO userDAO;
    GioHangDAO gioHangDAO;
    SanPhamDAO sanPhamDAO;
    DAOHoaDon daoHoaDon;
    private ArrayList<GioHang> listGioHang = null;
    String idUser, fullname, phone, location;
    int soLuong;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        soLuong = 0;
        createNotificationChannel();
        donHangDAO = new DonHangDAO(this);
        gioHangDAO = new GioHangDAO(this);
        daoHoaDon = new DAOHoaDon(ThanhToanActivity.this);
        sanPhamDAO = new SanPhamDAO(ThanhToanActivity.this);

        btnPay = findViewById(R.id.btnThanhToan);
        txtTongTienHang = findViewById(R.id.txtTongTienHangThanhToan);
        txtTongThanhToan = findViewById(R.id.txtTongThanhToan);
        txtTongThanhToan2 = findViewById(R.id.txtTongThanhToan2);
        txtPTTT = findViewById(R.id.txtPhuongThucTT);
        dateDatHang = findViewById(R.id.txtDateDatHang);
        dateNhanHang = findViewById(R.id.txtDateNhanHang);
        edLocation = findViewById(R.id.edLocationGiaoHang);
        rcvHoaDon = findViewById(R.id.rcv_SPThanhToan);
        edName = findViewById(R.id.txtnameUserTT);
        edPhone = findViewById(R.id.txtphoneUserTT);

        userDAO = new UserDAO(ThanhToanActivity.this);
        SharedPreferences sharedPreferences = getSharedPreferences("luuDangNhap", MODE_PRIVATE);
        String userName = sharedPreferences.getString("TK", "");
        idUser = userDAO.getIdUser(userName);
        fullname = userDAO.getFullName(userName);
        phone = userDAO.getPhone(userName);
        location = userDAO.getLocation(userName);

        edPhone.setText(phone);
        edName.setText(fullname);
        edLocation.setText(location);
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        Calendar calendar = Calendar.getInstance();
        currentDate = sdf.format(calendar.getTime());
        dateDatHang.setText(currentDate);
        calendar.add(Calendar.DAY_OF_MONTH, 3);
        String dateAfterThreeDays = sdf.format(calendar.getTime());
        dateNhanHang.setText(dateAfterThreeDays);


        Intent intent = getIntent();

        if (intent != null) {
            listGioHang = (ArrayList<GioHang>) intent.getSerializableExtra("list_gio_hang");
        }

        if (listGioHang != null && !listGioHang.isEmpty()) {
            AdapterHoaDon adapter = new AdapterHoaDon(this, listGioHang);
            rcvHoaDon.setAdapter(adapter);
            rcvHoaDon.setLayoutManager(new LinearLayoutManager(this));


        } else {
            Toast.makeText(this, "Không có sản phẩm trong giỏ hàng", Toast.LENGTH_SHORT).show();
        }

        String tongTienWithDot = getIntent().getStringExtra("tong_tien");
        txtTongTienHang.setText(tongTienWithDot);


        txtTongThanhToan.setText(tongTienWithDot);
        txtTongThanhToan2.setText(tongTienWithDot);

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
                        String tongTien = txtTongThanhToan.getText().toString().replaceAll("[^\\d]", "");
                        requestZalo(tongTien);
                        dialog.dismiss();
                    }
                });

                btnNhanHang.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        txtPTTT.setText("Thanh toán khi nhận hàng");
                        isPaymentMethodSelected = true;
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
                    Toast.makeText(ThanhToanActivity.this, "Chọn 1 phương thức thanh toán trước khi đặt hàng", Toast.LENGTH_SHORT).show();
                } else {
                    switch (paymentMethod) {
                        case "Zalopay":
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán bằng Zalopay", Toast.LENGTH_SHORT).show();
                            break;
                        case "Thanh toán khi nhận hàng":
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
                // Sử dụng tham số của phương thức để tránh sự nhầm lẫn với biến cục bộ có cùng tên
                if (paymentMethod.equals("Chọn phương thức thanh toán")) {
                    Toast.makeText(ThanhToanActivity.this, "Chọn 1 phương thức thanh toán trước khi thanh toán", Toast.LENGTH_SHORT).show();
                } else {
                    switch (paymentMethod) {
                        case "Zalopay":
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán bằng Zalopay", Toast.LENGTH_SHORT).show();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                            Calendar calendar = Calendar.getInstance();
                            String date = sdf.format(calendar.getTime());

                            HoaDon hoaDon = new HoaDon();
                            hoaDon.setId_user(Integer.parseInt(idUser)); // Chuyển đổi từ String sang int
                            hoaDon.setNgayMua(date);
                            String numericString = txtTongThanhToan.getText().toString().replaceAll("[^\\d]", "");
                            hoaDon.setTongTien(Integer.parseInt(numericString));
                            hoaDon.setPttt("Zalopay");
                            hoaDon.setPhone(edPhone.getText().toString());
                            hoaDon.setNameUser(edName.getText().toString());
                            hoaDon.setLocation(edLocation.getText().toString());
                            hoaDon.setStatus("1");

                            int idHoaDon = daoHoaDon.insertHoaDonAndGetId(hoaDon);

                            for (GioHang gioHang : listGioHang) {
                                DonHang donHang = new DonHang();
                                donHang.setId_HoaDon(idHoaDon);
                                donHang.setId_sanPham(gioHang.getId_sanPham());
                                donHang.setSoLuong(gioHang.getSoLuong());
                                donHang.setGiaBan(gioHang.getDonGia());
                                donHang.setMau(gioHang.getMau());

                                donHangDAO.insertDonHang(donHang);

                                // Trừ trong giỏ hàng
                                int soLuongSanPham = sanPhamDAO.getSoLuongSanPhamById(gioHang.getId_sanPham());
                                int soLuongDaBan = gioHang.getSoLuong();
                                int soLuongConLai = soLuongSanPham - soLuongDaBan;
                                sanPhamDAO.updateSoluongSP(gioHang.getId_sanPham(), soLuongConLai);

                            }

                            sendCancelNotification();
                            gioHangDAO.deleteAllGioHang();
                            Intent intent = new Intent(ThanhToanActivity.this, MainActivity.class);
                            startActivity(intent);
                            finish();
                            break;
                        case "Thanh toán khi nhận hàng":
                            Toast.makeText(ThanhToanActivity.this, "Thanh toán khi nhận hàng", Toast.LENGTH_SHORT).show();


                            SimpleDateFormat sdff = new SimpleDateFormat("yyyy/MM/dd", Locale.getDefault());
                            Calendar calendarr = Calendar.getInstance();
                            String datee = sdff.format(calendarr.getTime());

                            HoaDon hoaDonn = new HoaDon();
                            hoaDonn.setId_user(Integer.parseInt(idUser)); // Chuyển đổi từ String sang int
                            hoaDonn.setNgayMua(datee);
                            String numericStringg = txtTongThanhToan.getText().toString().replaceAll("[^\\d]", "");
                            hoaDonn.setTongTien(Integer.parseInt(numericStringg));
                            hoaDonn.setPttt("Thanh toán khi nhận hàng");
                            hoaDonn.setPhone(edPhone.getText().toString());
                            hoaDonn.setNameUser(edName.getText().toString());
                            hoaDonn.setLocation(edLocation.getText().toString());
                            hoaDonn.setStatus("1");


                            int idHoaDonn = daoHoaDon.insertHoaDonAndGetId(hoaDonn);

                            for (GioHang gioHang : listGioHang) {
                                DonHang donHang = new DonHang();
                                donHang.setId_HoaDon(idHoaDonn);
                                donHang.setId_sanPham(gioHang.getId_sanPham());
                                donHang.setSoLuong(gioHang.getSoLuong());
                                donHang.setGiaBan(gioHang.getDonGia());
                                donHang.setMau(gioHang.getMau());

                                donHangDAO.insertDonHang(donHang);

                                // Trừ trong giỏ hàng
                                int soLuongSanPham = sanPhamDAO.getSoLuongSanPhamById(gioHang.getId_sanPham());
                                int soLuongDaBan = gioHang.getSoLuong();
                                int soLuongConLai = soLuongSanPham - soLuongDaBan;
                                sanPhamDAO.updateSoluongSP(gioHang.getId_sanPham(), soLuongConLai);

                            }

                            sendCancelNotification();
                            gioHangDAO.deleteAllGioHang();
                            Intent intentn = new Intent(ThanhToanActivity.this, MainActivity.class);
                            startActivity(intentn);
                            finish();
                            break;
                    }
                }
            }


            private void sendCancelNotification() {
                NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);

                // Generate unique notification id
                int notificationId = generateNotificationId();

                // Build notification
                NotificationCompat.Builder builder = new NotificationCompat.Builder(ThanhToanActivity.this, "order_cancel_channel")
                        .setSmallIcon(R.drawable.avt)
                        .setContentTitle("Thông báo")
                        .setContentText("Đặt hàng thành công.")
                        .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                        .setAutoCancel(true);

                // Notify
                notificationManager.notify(notificationId, builder.build());
            }

            // Method to generate unique notification id
            private int generateNotificationId() {
                return (int) System.currentTimeMillis();
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


    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Thong bao";
            String description = "Huy don hang thanh cong";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("order_cancel_channel", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


}
