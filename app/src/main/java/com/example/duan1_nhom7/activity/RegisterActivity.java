package com.example.duan1_nhom7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.duan1_nhom7.R;

public class RegisterActivity extends AppCompatActivity {
    TextView tv_CoTk;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        anhXa();
        tv_CoTk.setOnClickListener(v -> {
            nextLogin();
        });
    }

    private  void nextLogin(){
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
    }


    private void anhXa(){
        tv_CoTk = findViewById(R.id.tv_CoTk);
    }
}