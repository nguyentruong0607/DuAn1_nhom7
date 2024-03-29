package com.example.duan1_nhom7.Fragment;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterUser;
import com.example.duan1_nhom7.Adapter.SpinnerAdapter;
import com.example.duan1_nhom7.DAO.DAOLoaiSP;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DAO.UserDAO;
import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.DTO.User;
import com.example.duan1_nhom7.R;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class QLy_user_Fragment extends Fragment {
    RecyclerView rcv;
    User user;
    ArrayList<User> list;
    AdapterUser adapterUser;
    UserDAO dao;
    ImageView img_add;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_qly_user, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcv = view.findViewById(R.id.rcv_user);
        img_add = view.findViewById(R.id.img_add_user);
        img_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Dialog dialog = new Dialog(getContext());
                dialog.setContentView(R.layout.dialog_add_user);
                TextInputEditText ed_them_fullname, ed_them_sdtuser, ed_them_tenuser, ed_them_mkuser, ed_them_diaChiUser;
                ed_them_fullname = dialog.findViewById(R.id.ed_them_fullname);
                ed_them_sdtuser = dialog.findViewById(R.id.ed_them_sdtuser);
                ed_them_tenuser = dialog.findViewById(R.id.ed_them_tenuser);
                ed_them_mkuser = dialog.findViewById(R.id.ed_them_mkuser);
                ed_them_diaChiUser = dialog.findViewById(R.id.ed_them_diaChi_user);
                TextInputLayout elt_them_fullname, elt_them_sdtuser, elt_them_tenuser, elt_them_mkuser, elt_them_diaChiUser;
                elt_them_fullname = dialog.findViewById(R.id.elt_them_fullname);
                elt_them_sdtuser = dialog.findViewById(R.id.elt_them_sdtuser);
                elt_them_tenuser = dialog.findViewById(R.id.etl_them_tenuser);
                elt_them_mkuser = dialog.findViewById(R.id.etl_them_mkuser);
                elt_them_diaChiUser = dialog.findViewById(R.id.etl_them_diaChi_user);

                Button btn_them_user = dialog.findViewById(R.id.btn_them_user);
                Button btn_huy_user = dialog.findViewById(R.id.btn_huy_user);
                btn_huy_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                btn_them_user.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String fullname = ed_them_fullname.getText().toString();
                        String sdt = ed_them_sdtuser.getText().toString();
                        String tenuser = ed_them_tenuser.getText().toString();
                        String mk = ed_them_mkuser.getText().toString();
                        String diaChi = ed_them_diaChiUser.getText().toString();
                        dao = new UserDAO(getContext());

                        User obj = new User();
                        obj.setFullname(fullname);
                        obj.setSodienthoai(sdt);
                        obj.setTen_user(tenuser);
                        obj.setPassword(mk);
                        obj.setDiaChi(diaChi);
                        if (checkTrong()) {
                            return;
                        }
                        if (kiemTraKyTu()) {
                            return;
                        }
                        long res = dao.insert(obj);
                        if (res > 0) {
                            list.clear();
                            list.addAll(dao.getAllUser());
                            adapterUser.notifyDataSetChanged();
                            dialog.dismiss();
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Lỗi, xem log đi", Toast.LENGTH_SHORT).show();
                        }
                    }

                    private boolean checkTrong() {
                        boolean isEmpty = false;
                        if (ed_them_fullname.getText().toString().isEmpty()) {
                            elt_them_fullname.setError("Vui lòng nhập họ tên !");
                            isEmpty = true;
                        } else {
                            elt_them_fullname.setErrorEnabled(false);

                        }
                        if (ed_them_sdtuser.getText().toString().isEmpty()) {
                            elt_them_sdtuser.setError("Vui lòng nhập số điện thoại!");
                            isEmpty = true;
                        } else {
                            elt_them_sdtuser.setErrorEnabled(false);

                        }

                        if (ed_them_tenuser.getText().toString().isEmpty()) {
                            elt_them_tenuser.setError("Vui lòng nhập tên đăng nhập!");
                            isEmpty = true;
                        } else {
                            elt_them_tenuser.setErrorEnabled(false);

                        }
                        if (ed_them_mkuser.getText().toString().isEmpty()) {
                            elt_them_mkuser.setError("Vui lòng nhập mật khẩu");
                            isEmpty = true;
                        } else {
                            elt_them_mkuser.setErrorEnabled(false);

                        }
                        if (ed_them_diaChiUser.getText().toString().isEmpty()) {
                            elt_them_diaChiUser.setError("Vui lòng nhập địa chỉ!");
                            isEmpty = true;
                        } else {
                            elt_them_diaChiUser.setErrorEnabled(false);

                        }
                        return isEmpty;
                    }

                    public boolean kiemTraKyTu() {
                        boolean isEmpty = false;
                        if (ed_them_tenuser.getText().toString().length() < 4) {
                            elt_them_tenuser.setError("Tài khoản nhập tối thiểu 4 ký tự!");
                            isEmpty = true;
                        } else {
                            elt_them_tenuser.setErrorEnabled(false);

                        }
                        if (ed_them_mkuser.getText().toString().length() < 4) {
                            elt_them_mkuser.setError("Mật khẩu nhập tối thiểu 4 ký tự!");
                            isEmpty = true;
                        } else {
                            elt_them_mkuser.setErrorEnabled(false);

                        }
                        if (!ed_them_sdtuser.getText().toString().matches("^0[3589]{1}\\d{8}$")) {
                            elt_them_sdtuser.setError("Số điện thoại phải đúng định dạng!");
                            isEmpty = true;
                        } else {
                            elt_them_sdtuser.setErrorEnabled(false);

                        }
                        return isEmpty;
                    }
                });
                dialog.show();
            }

        });
        dao = new UserDAO(getContext());
        list = (ArrayList<User>) dao.getAllUser();
        adapterUser = new AdapterUser(list, getContext());
        rcv.setAdapter(adapterUser);
    }


}

