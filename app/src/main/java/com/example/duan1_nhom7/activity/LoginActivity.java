package com.example.duan1_nhom7.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1_nhom7.R;

public class LoginActivity extends AppCompatActivity {
TextView tv_loginRegister;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        anhXa();
        tv_loginRegister.setOnClickListener(v -> {
            nextRegister();
        });
    }

    private  void nextRegister(){
        Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
        startActivity(intent);
    }


    private void anhXa(){
        tv_loginRegister = findViewById(R.id.tv_loginRegister);
    }
}