package com.example.duan1_nhom7.Fragment;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
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

import com.example.duan1_nhom7.Activity.Edit_Activity;
import com.example.duan1_nhom7.Activity.LoginActivity;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.DonHangActivity;
import com.example.duan1_nhom7.MainActivity;
import com.example.duan1_nhom7.R;

public class Account_Fragment extends Fragment {
    private LinearLayout  userFrgmQLuser, userFrgmThemSP, userFrgmLoaiSP, userFrgmThemNhanVien, userFrgmDangXuat,user_edit_pass;
    TextView txtUserName, txtUserKH;
    UserDAO daoUser;

    int id;
    String pass;
    TextView tvedit;
    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);


        userFrgmThemSP = view.findViewById(R.id.userFrgmThemSP);
        userFrgmDangXuat = view.findViewById(R.id.userFrgmDangXuat);
        userFrgmQLuser = view.findViewById(R.id.userFrgmQLuser);
        user_edit_pass = view.findViewById(R.id.user_edit_pass);
        tvedit = view.findViewById(R.id.tv_edit);


        txtUserName = view.findViewById(R.id.txtUserName);
        txtUserKH=view.findViewById(R.id.txtUserName2);
        //set quyen
        daoUser = new UserDAO(getContext());
        SharedPreferences sharedPreferences = getContext().getSharedPreferences("luuDangNhap", Context.MODE_PRIVATE);
        String quyen = sharedPreferences.getString("quyen", "");
        String taiKhoan = sharedPreferences.getString("TK", "");

        User user1 =    daoUser.getUserByName(taiKhoan);
        Log.i("zzzzzzzzzzzzzz",user1.toString());
        id = user1.getId_user();
        pass = user1.getPassword();
        Bundle bundle = new Bundle();
        bundle.putSerializable("user", user1);
        user_edit_pass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChangePasswordDialog();
            }
        });
        // Pass the Bundle to another activity


        if (quyen.equalsIgnoreCase("khachhang")) {
            txtUserName.setVisibility(View.GONE);
            userFrgmThemSP.setVisibility(View.GONE);
            userFrgmQLuser.setVisibility(View.GONE);


        }
        if (quyen.equalsIgnoreCase("admin")){
            txtUserKH.setVisibility(View.GONE);
        }
        tvedit.setOnClickListener(v -> {
            Intent intent = new Intent(getActivity(), Edit_Activity.class);
            intent.putExtras(bundle);
            startActivity(intent);
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

                        SharedPreferences sharedPreferences = getActivity().getSharedPreferences("luuDangNhap", Context.MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove("TK");
                        editor.apply();

                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
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

    private void openChangePasswordDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_update_pass, null);

        EditText currentPasswordEditText = dialogView.findViewById(R.id.currentPasswordEditText);
        EditText newPasswordEditText = dialogView.findViewById(R.id.newPasswordEditText);
        EditText confirmPasswordEditText = dialogView.findViewById(R.id.confirmPasswordEditText);

        builder.setView(dialogView)
                .setTitle("Change Password")
                .setPositiveButton("Change", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Handle password change logic here
                        String currentPassword = currentPasswordEditText.getText().toString();
                        String newPassword = newPasswordEditText.getText().toString();
                        String confirmPassword = confirmPasswordEditText.getText().toString();
                        if(currentPassword.equals(pass)){
                            if (newPassword.equals(confirmPassword)) {
                                User user = new User(id,newPassword);
                                if ( daoUser.updatePass(user)>0){
                                    Toast.makeText(getContext(), "Thay đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                }  else {
                                    Toast.makeText(getContext(), "Thay đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                Toast.makeText(getContext(), "Mật khẩu mới phải giống mật khẩu cũ ", Toast.LENGTH_SHORT).show();
                            }
                        }else {
                            Toast.makeText(getContext(), "Mật khẩu cũ không đúng ", Toast.LENGTH_SHORT).show();
                        }
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.cancel();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }
}

