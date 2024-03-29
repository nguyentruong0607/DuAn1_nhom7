package com.example.duan1_nhom7.Activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;

import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.duan1_nhom7.DAO.AdminDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.MainActivity;
import com.example.duan1_nhom7.MainActivity_Admin;
import com.example.duan1_nhom7.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;

public class LoginActivity extends AppCompatActivity {
    TextView tv_loginRegister;
    TextInputEditText ed_user , ed_pass;
    private TextInputLayout text_inputLayoutLoginUser, text_inputLayoutLoginPass;

    Button btn_login;
    CheckBox checkBox;
    UserDAO userDAO;
    AdminDAO adminDAO;
    RadioButton rdoAdmin,rdoKhachHang;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        tv_loginRegister.setOnClickListener(v -> {
            nextRegister();
        });
        rdoAdmin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_loginRegister.setVisibility(View.GONE);

            }
        });
        rdoKhachHang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_loginRegister.setVisibility(View.VISIBLE);
            }
        });
        SharedPreferences sharedPreferences = getSharedPreferences("nhotaiKhoan", MODE_PRIVATE);
        checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                SharedPreferences.Editor editor = sharedPreferences.edit();
                if (isChecked) {
                    editor.putString("username", ed_user.getText().toString());
                    editor.putString("password", ed_pass.getText().toString());
                } else {
                    editor.remove("username");
                    editor.remove("password");
                }
                editor.apply();
            }
        });
        String username = sharedPreferences.getString("username", "");
        String password = sharedPreferences.getString("password", "");
        if (!username.isEmpty() && !password.isEmpty()) {
            ed_user.setText(username);
            ed_pass.setText(password);
        } else {
            // Không có tài khoản và mật khẩu được lưu trữ.
        }
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String strUser = ed_user.getText().toString();
                String strPass = ed_pass.getText().toString();
                boolean checkLogin = true;

//                Kiểm tra tên đăng nhập
                if (strUser.isEmpty()) {
                    ed_user.setHintTextColor(Color.RED);
                    Toast.makeText(LoginActivity.this, "Nhập tên đăng nhập!", Toast.LENGTH_SHORT).show();
                    checkLogin = false;
                }
//                Kiểm tra mật khẩu
                if (strPass.isEmpty()) {
                    ed_pass.setHintTextColor(Color.RED);
                    Toast.makeText(LoginActivity.this, "Nhập mật khẩu!", Toast.LENGTH_SHORT).show();
                    checkLogin = false;
                }

//                Kiểm tra User tồn tại
//                if (checkLogin) {
//                    ArrayList<User> list = userDAO.checkLogin(strUser, strPass);
//                    if (list.size() > 0) {
//                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
//                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                        intent.putExtra("user", strUser);
//                        startActivity(intent);
//                        User user = list.get(0);
//                        int id_user = user.getId_user();
//                        remmemberUser(id_user, strUser, strPass, checkBox.isChecked());
//                        closeKeyboard();
//                    } else {
//                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
//                    }
//                }
                // Kiểm tra đã chọn quyền hay chưa
                if (!rdoAdmin.isChecked() && !rdoKhachHang.isChecked()) {
                    Toast.makeText(LoginActivity.this, "Vui lòng chọn quyền!", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Kiểm tra và xử lý đăng nhập cho người dùng khách hàng
                if (rdoKhachHang.isChecked()) {
                    if (userDAO.checkLogin(strUser, strPass)) {
                        SharedPreferences shareQuyen = getSharedPreferences("luuDangNhap", MODE_PRIVATE);
                        SharedPreferences.Editor edit = shareQuyen.edit();
                        edit.putString("TK", strUser);
                        edit.putString("quyen", "khachhang");
                        edit.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }

                // Kiểm tra và xử lý đăng nhập cho người dùng quản trị viên
                adminDAO=new AdminDAO(getApplicationContext());
                if (rdoAdmin.isChecked()) {
                    if (adminDAO.checkDangNhap(strUser, strPass)) {
                        SharedPreferences shareQuyen = getSharedPreferences("luuDangNhap", MODE_PRIVATE);
                        SharedPreferences.Editor edit = shareQuyen.edit();
                        edit.putString("TK", strUser);
                        edit.putString("quyen", "admin");
                        edit.apply();
                        Intent intent = new Intent(LoginActivity.this, MainActivity_Admin.class);
                        startActivity(intent);
                    } else {
                        Toast.makeText(LoginActivity.this, "Tài khoản hoặc mật khẩu không đúng!", Toast.LENGTH_SHORT).show();
                    }
                }
            }

        });

    }

    private  void nextRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private void anhXa() {
        userDAO = new UserDAO(this);
        tv_loginRegister = findViewById(R.id.tv_loginRegister);
        ed_user = findViewById(R.id.edt_loginUser);
        ed_pass = findViewById(R.id.edt_loginPass);
        btn_login =findViewById(R.id.btn_loginLogin);
        text_inputLayoutLoginUser = findViewById(R.id.text_inputLayoutLoginUser);
        text_inputLayoutLoginPass = findViewById(R.id.text_inputLayoutLoginPass);
        checkBox=findViewById(R.id.ckb_loginRemember);
        rdoAdmin=findViewById(R.id.rdoNhanVien);
        rdoKhachHang=findViewById(R.id.rdokhachHang);

    }






}