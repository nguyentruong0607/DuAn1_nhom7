package com.example.duan1_nhom7.activity;

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

public class RegisterActivity extends AppCompatActivity {
    TextView tv_CoTk;
    UserDAO userDAO;
    Button btn_dangKy;
    EditText edt_registerName,edt_registerPhone,edt_diachi,edt_registerUser,edt_registerPass;
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

    }
    private void dangky(){
        String ten=edt_registerName.getText().toString();
        String dt=edt_registerPhone.getText().toString();
        String dc=edt_diachi.getText().toString();
        String user1=edt_registerUser.getText().toString();
        String pass=edt_registerPass.getText().toString();
        User user = new User(user1,pass,dt,dc,ten,1);
        if(userDAO.insert(user)>0){
            Log.i("aaaaaaaaaaa","thanhcong");
            Toast.makeText(this, "thanhcong", Toast.LENGTH_SHORT).show();
        }else {
            Log.i("aaaaaaaaaaa","bai vc");
            Toast.makeText(this, "bai", Toast.LENGTH_SHORT).show();

        }
    }

}