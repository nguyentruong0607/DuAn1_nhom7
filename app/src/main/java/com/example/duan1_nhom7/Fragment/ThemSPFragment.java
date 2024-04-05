package com.example.duan1_nhom7.Fragment;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_nhom7.Adapter.SpinnerAdapter;
import com.example.duan1_nhom7.DAO.DAOLoaiSP;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class ThemSPFragment extends Fragment {
    private EditText edName, edPrice, edMoTa,edImage,edSoLuongSP,edNgaySP;
    Spinner edtLoaiSP;
    SanPhamDAO daoSanPham;
    Button btnAddSP, btnHuySP;

    SpinnerAdapter spinnerAdapter;
    LoaiSP loaiSP;
    List<LoaiSP> listLoai;
    DAOLoaiSP daoLoaiSP;
    String strTenSP, strGiaban, strngaySP, strMota,strImage,strSoLuongSP;
    SanPham sanPham;
    @SuppressLint({"MissingInflatedId", "WrongViewCast"})
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_them_sp, container, false);
        //ánh xạ
        daoSanPham = new SanPhamDAO(getActivity());
        sanPham=new SanPham();
        ImageView btnBackThemSP = view.findViewById(R.id.btnBackThemSP);

        edImage=view.findViewById(R.id.edImageSP);
        edName = view.findViewById(R.id.edNameSP);
        edPrice = view.findViewById(R.id.edPrice);
        edSoLuongSP=view.findViewById(R.id.edSoLuongSP);
        edMoTa = view.findViewById(R.id.edMoTa);
        edtLoaiSP = view.findViewById(R.id.edtLoaiSP);
        btnAddSP = view.findViewById(R.id.btn_themSP);
        btnHuySP = view.findViewById(R.id.btn_huy_themSP);
        edNgaySP=view.findViewById(R.id.edNgaySP);
        edNgaySP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTimeInMillis(System.currentTimeMillis());
                DatePickerDialog dialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int i, int i1, int i2) {
                        int nam = i;
                        int thang = i1;
                        int ngay = i2;
                        edNgaySP.setText(ngay + "/" + (thang + 1) + "/" + nam);

                    }
                },
                        calendar.get(Calendar.YEAR),
                        calendar.get(Calendar.MONTH),
                        calendar.get(Calendar.DATE)
                );
                dialog.show();
            }
        });


        //Set data cho spinner loại sản phẩm
        daoLoaiSP=new DAOLoaiSP(getContext());
        listLoai=daoLoaiSP.getAllLoaiSP();
        spinnerAdapter=new SpinnerAdapter(listLoai,getContext());
        edtLoaiSP.setAdapter(spinnerAdapter);
        edtLoaiSP.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sanPham.setId_Loai(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
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
                strngaySP=edNgaySP.getText().toString();
                LoaiSP loaiSP1= (LoaiSP) edtLoaiSP.getSelectedItem();


                if (checkEdt()) {

                    daoSanPham.insertData(strImage, strTenSP, Integer.parseInt(strGiaban),'1', strMota,Integer.parseInt(strSoLuongSP),strngaySP);
                    Toast.makeText(getActivity(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                    resetEdt();

                }
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
        edMoTa.setText("");
        edMoTa.setHintTextColor(Color.BLACK);
        edSoLuongSP.setText("");
        edSoLuongSP.setHintTextColor(Color.BLACK);
        edNgaySP.setText("");
        edNgaySP.setHintTextColor(Color.BLACK);
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
        if (strngaySP.isEmpty()) {
            edNgaySP.setError("Vui lòng nhập!");
            edNgaySP.setHintTextColor(Color.RED);
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
