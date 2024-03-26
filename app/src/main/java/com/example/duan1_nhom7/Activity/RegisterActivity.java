package com.example.duan1_nhom7.Activity;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.Database.DbHelper;
import com.example.duan1_nhom7.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_CoTk;
    UserDAO userDAO;
    Button btn_dangKy;
    private TextInputLayout text_inputLayoutName, text_inputLayoutPhone, text_inputLayoutUser, text_inputLayoutPass,text_inputLayoutDiaChi;

    TextInputEditText edt_registerName,edt_registerPhone,edt_diachi,edt_registerUser,edt_registerPass;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        // Create an instance of DbHelper
        DbHelper dbHelper = new DbHelper(this);

        // Get writable database
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        anhXa();
        tv_CoTk.setOnClickListener(v -> {
            nextLogin();
        });
        btn_dangKy.setOnClickListener(v -> {
            dangky();
        });


    }

    private  void nextLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    private void anhXa(){
        tv_CoTk = findViewById(R.id.tv_CoTk);
        userDAO = new UserDAO(this);
        edt_registerName=findViewById(R.id.edt_registerName);
        edt_registerPhone=findViewById(R.id.edt_registerPhone);
        edt_diachi=findViewById(R.id.edt_diachi);
        edt_registerUser=findViewById(R.id.edt_registerUser);
        edt_registerPass=findViewById(R.id.edt_registerPass);
        btn_dangKy = findViewById(R.id.btn_registerRegister);
        text_inputLayoutName = findViewById(R.id.text_inputLayoutName);
        text_inputLayoutPhone = findViewById(R.id.text_inputLayoutPhone);
        text_inputLayoutUser = findViewById(R.id.text_inputLayoutUser);
        text_inputLayoutPass = findViewById(R.id.text_inputLayoutPass);
        text_inputLayoutDiaChi = findViewById(R.id.text_diachi);
    }
    private void dangky(){
        String ten=edt_registerName.getText().toString();
        String dt=edt_registerPhone.getText().toString();
        String dc=edt_diachi.getText().toString();
        String user1=edt_registerUser.getText().toString();
        String pass=edt_registerPass.getText().toString();
        User user = new User(user1,pass,dt,dc,ten,1);

        if(valName()|valPass()|valPhone()|valUser()|valDiaChi()){

            if(userDAO.insert(user)>0){
                Log.i("aaaaaaaaaaa","thanhcong");
                Toast.makeText(this, "thanhcong", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this , LoginActivity.class);
                startActivity(intent);

            }else {
                Log.i("aaaaaaaaaaa","bai vc");
                Toast.makeText(this, "bai", Toast.LENGTH_SHORT).show();

            }
        }

    }



    //Validate;
    private Boolean valName() {
        String valName = text_inputLayoutName.getEditText().getText().toString().trim();


        if (valName.isEmpty()) {
            text_inputLayoutName.setError("Không được để trống.");
            return false;
        } else if (valName.length() > 18) {
            text_inputLayoutName.setError("Tên quá dài.");
            return false;
        } else if (valName.length() < 10) {
            text_inputLayoutName.setError("Tên quá ngắn.");
            return false;
        } else {

            text_inputLayoutName.setError(null);
            text_inputLayoutName.setErrorEnabled(false);
            return true;
        }


    }


    private Boolean valPhone() {
        String valPhone = text_inputLayoutPhone.getEditText().getText().toString().trim();

        if (valPhone.isEmpty()) {
            text_inputLayoutPhone.setError("Không được để trống.");
            return false;


        } else if (valPhone.length() > 10  || valPhone.length() < 10 ) {
            text_inputLayoutPhone.setError("Số điện thoại có 10 số.");
            return false;

        } else if (!ischeckPhone(valPhone)) {
            text_inputLayoutPhone.setError("Số điện thoại là số.");
            return false;

        } else {
            text_inputLayoutPhone.setError(null);
            text_inputLayoutPhone.setErrorEnabled(false);
            return true;
        }
    }

    //Check số điện thoại là số;
    public Boolean ischeckPhone(String phone) {
        try {
            Integer.parseInt(phone);
            return true;
        } catch (Exception e) {
            return false;
        }
    }


    private Boolean valUser() {
        String valUser = text_inputLayoutUser.getEditText().getText().toString().trim();
        String check = "\\A\\w{4,20}\\z";
        if (valUser.isEmpty()) {
            text_inputLayoutUser.setError("Không được để trống");
            return false;
        } else if (valUser.length() > 15) {
            text_inputLayoutUser.setError("Tên đăng nhập quá dài");
            return false;
        } else if (!valUser.matches(check)) {
            text_inputLayoutUser.setError("Tên đăng nhập không có khoảng trắng");
            return false;
        } else {
            text_inputLayoutUser.setError(null);
            text_inputLayoutUser.setErrorEnabled(false);
            return true;
        }

    }

    private Boolean valPass() {
        String valPass = text_inputLayoutPass.getEditText().getText().toString().trim();
        String check = "^" + "(?=.*[a-zA-Z])" + "(?=.*[@#$%^&+=])" + "(?=\\S+$)" + ".{4,}" + "$";
        if (valPass.isEmpty()) {
            text_inputLayoutPass.setError("Không được bỏ trống");
            return false;
        } else if (!valPass.matches(check)) {
            text_inputLayoutPass.setError("Mật khẩu quá yếu");
            return false;
        } else {
            text_inputLayoutPass.setError(null);
            text_inputLayoutPass.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean valDiaChi() {
        String valDia = text_inputLayoutDiaChi.getEditText().getText().toString().trim();
        String check = "\\A\\w{4,20}\\z";
        if (valDia.isEmpty()) {
            text_inputLayoutDiaChi.setError("Không được để trống");
            return false;
        }
        else if (!valDia.matches(check)) {
            text_inputLayoutDiaChi.setError("Tên đăng nhập không có khoảng trắng");
            return false;
        } else {
            text_inputLayoutDiaChi.setError(null);
            text_inputLayoutDiaChi.setErrorEnabled(false);
            return true;
        }

    }
}