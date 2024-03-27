package com.example.duan1_nhom7.Fragment;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_nhom7.Activity.LoginActivity;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.R;

public class Account_Fragment extends Fragment {
    private LinearLayout userFrgmTaiKhoan, userFrgmDoiMK, userFrgmTKDoanhThu, userFrgmQLuser, userFrgmThemSP, userFrgmLoaiSP, userFrgmThemNhanVien, userFrgmDangXuat;
    TextView txtUserName, txtChucVu;
    UserDAO daoUser;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        userFrgmThemSP = view.findViewById(R.id.userFrgmThemSP);
        userFrgmLoaiSP=view.findViewById(R.id.userFrgmLoaiSP);
        userFrgmDangXuat = view.findViewById(R.id.userFrgmDangXuat);
        userFrgmQLuser=view.findViewById(R.id.userFrgmQLuser);


        txtUserName = view.findViewById(R.id.txtUserName);

        //set quyen
        daoUser=new UserDAO(getContext());
        SharedPreferences pref = getActivity().getSharedPreferences("USER_FILE", getActivity().MODE_PRIVATE);
        int id_user = pref.getInt("MA", 0);
        User user = daoUser.getUser(id_user);
        if (user != null) {
            txtUserName.setText(user.getTen_user());

            int quyenUser = user.getChucvu();
            if (quyenUser != 1) {
                userFrgmLoaiSP.setVisibility(View.GONE);
                userFrgmThemSP.setVisibility(View.GONE);
                userFrgmQLuser.setVisibility(View.GONE);

            }
        } else {
            // Xử lý trường hợp không tìm thấy người dùng, ví dụ: hiển thị thông báo lỗi
            Toast.makeText(getContext(), "Không tìm thấy người dùng", Toast.LENGTH_SHORT).show();
        }

        userFrgmLoaiSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new LoaiSPFragment());
            }
        });
        userFrgmThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new ThemSPFragment());
            }
        });
        userFrgmQLuser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                loadFragment(new QLy_user_Fragment());
            }
        });
        userFrgmDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Dialog dialog = new Dialog(getActivity());
                dialog.setContentView(R.layout.dialog_confirm);
                dialog.getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                TextView dialog_confirm_content = dialog.findViewById(R.id.dialog_confirm_content);
                dialog_confirm_content.setText("Bạn muốn đăng xuất?");
                EditText btnDialogHuy = dialog.findViewById(R.id.btnDialogHuy);
                EditText btnDialogXN = dialog.findViewById(R.id.btnDialogXN);
                btnDialogXN.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getActivity(), LoginActivity.class);
                        Toast.makeText(getContext(), "Đăng xuất", Toast.LENGTH_SHORT).show();
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK |Intent.FLAG_ACTIVITY_CLEAR_TASK );
                        startActivity(intent);
                        dialog.dismiss();

                    }
                });
                btnDialogHuy.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                dialog.show();
            }
        });



        return view;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }


}
