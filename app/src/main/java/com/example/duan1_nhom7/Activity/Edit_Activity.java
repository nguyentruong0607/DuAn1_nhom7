package com.example.duan1_nhom7.Activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.MainActivity;
import com.example.duan1_nhom7.R;

public class Edit_Activity extends AppCompatActivity {

    EditText edname,edsdt,eddiachi;
    Button btn_edit;
    int id;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_edit);
        edname = findViewById(R.id.edt_registerName22);
        edsdt = findViewById(R.id.edt_registerPhone22);
        eddiachi = findViewById(R.id.edt_diachi22);
        btn_edit = findViewById(R.id.btn_edit_user);
        UserDAO userDAO = new UserDAO(this);
        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            // Retrieve the User object from the Bundle
            User user1 = (User) bundle.getSerializable("user");
            id = user1.getId_user();
                    edname.setText(user1.getFullname());
            edsdt.setText(user1.getSodienthoai());
            eddiachi.setText(user1.getDiaChi());
            Log.i("zzzz",user1.toString());


        }
        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String a = edname.getText().toString();
                String b = edsdt.getText().toString();
                String c = eddiachi.getText().toString();
                User user = new User(id,b,c,a);
                if(  userDAO.updateUser(user)>=1){
                    Intent intent = new Intent(Edit_Activity.this, MainActivity.class);
                    startActivity(intent);
                    Toast.makeText(Edit_Activity.this, "sửa thành công", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Edit_Activity.this, "thất bại", Toast.LENGTH_SHORT).show();
                }

            }
        });

    }
}