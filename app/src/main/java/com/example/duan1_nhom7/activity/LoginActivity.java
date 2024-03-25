package com.example.duan1_nhom7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.MainActivity;
import com.example.duan1_nhom7.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class LoginActivity extends AppCompatActivity {
TextView tv_loginRegister;
    TextInputEditText ed_user , ed_pass;
    private TextInputLayout text_inputLayoutLoginUser, text_inputLayoutLoginPass;

Button btn_login;
    UserDAO userDAO;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        anhXa();
        tv_loginRegister.setOnClickListener(v -> {
            nextRegister();
        });
        btn_login.setOnClickListener(v -> {
            Login();
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
    }

    private void Login(){
        String a = ed_user.getText().toString();
        String b = ed_pass.getText().toString();
       if(valUser() | valPass()){
           if (userDAO.checkLogin(a,b)){
               Intent intent = new Intent(this, MainActivity.class);
               startActivity(intent);
           }
           else {
               Toast.makeText(this, "Sai tài khoản hoặc mật khẩu", Toast.LENGTH_SHORT).show();
           }
       }
    }

    private Boolean valUser() {
        String valUser = text_inputLayoutLoginUser.getEditText().getText().toString().trim();

        String check = "\\A\\w{4,20}\\z";
        if (valUser.isEmpty()) {
            text_inputLayoutLoginUser.setError("Không được bỏ trống");
            return false;
        } else if (!valUser.matches(check)) {
            text_inputLayoutLoginUser.setError("Tên tài khoản không có khoản trắng");
            return false;
        }
        else {
            text_inputLayoutLoginUser.setError(null);
            text_inputLayoutLoginUser.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean valPass() {
        String valPass = text_inputLayoutLoginPass.getEditText().getText().toString().trim();
        if (valPass.isEmpty()) {
            text_inputLayoutLoginPass.setError("Không được bỏ trống");
            return false;
        } else {
            text_inputLayoutLoginPass.setError(null);
            text_inputLayoutLoginPass.setErrorEnabled(false);
            return true;
        }
    }
}