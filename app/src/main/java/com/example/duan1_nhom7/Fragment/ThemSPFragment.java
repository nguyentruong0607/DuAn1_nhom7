package com.example.duan1_nhom7.Fragment;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.R;

public class ThemSPFragment extends Fragment {
    private EditText edName, edPrice, edMoTa,edImage,edSoLuongSP;
    AutoCompleteTextView edtLoaiSP;
    SanPhamDAO daoSanPham;
    Button btnAddSP, btnHuySP;


    String strTenSP, strGiaban, strLoaiSP, strMota,strImage,strSoLuongSP;

    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_sp, container, false);
        //ánh xạ
        daoSanPham = new SanPhamDAO(getActivity());
        ImageView btnBackThemSP = view.findViewById(R.id.btnBackThemSP);

        edImage=view.findViewById(R.id.edImageSP);
        edName = view.findViewById(R.id.edNameSP);
        edPrice = view.findViewById(R.id.edPrice);
        edSoLuongSP=view.findViewById(R.id.edSoLuongSP);
        edMoTa = view.findViewById(R.id.edMoTa);
        edtLoaiSP = view.findViewById(R.id.edtLoaiSP);
        btnAddSP = view.findViewById(R.id.btn_themSP);
        btnHuySP = view.findViewById(R.id.btn_huy_themSP);

        btnBackThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadFragment(new Account_Fragment());
            }
        });

//        Set sự kiện Click Button Thêm
        btnAddSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                strImage=edImage.getText().toString();
                strTenSP = edName.getText().toString();
                strGiaban = edPrice.getText().toString();
                strMota = edMoTa.getText().toString();
                strSoLuongSP=edSoLuongSP.getText().toString();
                strLoaiSP = edtLoaiSP.getText().toString();

                daoSanPham.insertData(strImage, strTenSP, Integer.parseInt(strGiaban),'1', strMota,Integer.parseInt(strSoLuongSP));
                Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                resetEdt();

            }

        });

//        Set sự kiện Click Button Hủy
        btnHuySP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                resetEdt();
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

    //    Reset Edittext
    private void resetEdt() {
        edImage.setText("");
        edName.setHintTextColor(Color.BLACK);
        edName.setText("");
        edName.setHintTextColor(Color.BLACK);
        edPrice.setText("");
        edPrice.setHintTextColor(Color.BLACK);
        edtLoaiSP.setText("");
        edtLoaiSP.setHintTextColor(Color.BLACK);
        edMoTa.setText("");
        edMoTa.setHintTextColor(Color.BLACK);
        edSoLuongSP.setText("");
        edSoLuongSP.setHintTextColor(Color.BLACK);
    }

    //    Check Form
    private boolean checkEdt() {

        boolean checkAdd = true;
        if (strTenSP.isEmpty()) {
            edName.setError("Vui lòng nhập!");
            edName.setHintTextColor(Color.RED);
            checkAdd = false;
        }

        if (strGiaban.isEmpty()) {
            edPrice.setError("Vui lòng nhập!");
            edPrice.setHintTextColor(Color.RED);
            checkAdd = false;
        }

        if (strLoaiSP.isEmpty()) {
            edtLoaiSP.setError("Vui lòng nhập!");
            edtLoaiSP.setHintTextColor(Color.RED);
            checkAdd = false;
        }
        if (strSoLuongSP.isEmpty()) {
            edSoLuongSP.setError("Vui lòng nhập!");
            edSoLuongSP.setHintTextColor(Color.RED);
            checkAdd = false;
        } else {
            int soLuong = Integer.parseInt(strSoLuongSP);
            if (soLuong <= 0 || soLuong > 50) {
                edSoLuongSP.setError("Số lượng không hợp lệ! (1 - 50)");
                edSoLuongSP.setHintTextColor(Color.RED);
                checkAdd = false;
            }
        }
        if (strMota.isEmpty()) {
            edMoTa.setError("Vui lòng nhập!");
            edMoTa.setHintTextColor(Color.RED);
            checkAdd = false;
        }
        if (strImage.isEmpty()) {
            edImage.setError("Vui lòng nhập!");
            edImage.setHintTextColor(Color.RED);
            checkAdd = false;
        }
        return checkAdd;
    }
}
