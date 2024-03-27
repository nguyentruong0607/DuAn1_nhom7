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
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AppCompatActivity;


import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.MainActivity;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        tv_loginRegister.setOnClickListener(v -> {
            nextRegister();
        });
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
                if (checkLogin) {
                    ArrayList<User> list = userDAO.checkLogin(strUser, strPass);
                    if (list.size() > 0) {
                        Toast.makeText(LoginActivity.this, "Đăng nhập thành công", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                        intent.putExtra("user", strUser);
                        startActivity(intent);
                        User user = list.get(0);
                        int id_user = user.getId_user();
                        remmemberUser(id_user, strUser, strPass, checkBox.isChecked());
                        closeKeyboard();
                    } else {
                        Toast.makeText(LoginActivity.this, "Tên đăng nhập hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
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
        //        Get Data từ SharedPreferences
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        String user = pref.getString("USERNAME", "");
        String pass = pref.getString("PASSWORD", "");
        boolean rem = pref.getBoolean("REMEMBER", false);
        ed_user.setText(user);
        ed_pass.setText(pass);
        checkBox.setChecked(rem);
    }


    public void remmemberUser(int id_user, String u, String p, boolean status) {
        SharedPreferences pref = getSharedPreferences("USER_FILE", MODE_PRIVATE);
        SharedPreferences.Editor editor = pref.edit();
        if (!status) {
            editor.clear();
        } else {
            editor.putInt("MA", id_user);
            editor.putString("USERNAME", u);
            editor.putString("PASSWORD", p);
            editor.putBoolean("REMEMBER", status);
        }
        editor.commit();
    }

    private void closeKeyboard() {
        View view = this.getCurrentFocus();
        if (view != null) {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


}