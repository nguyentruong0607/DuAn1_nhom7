package com.example.duan1_nhom7.Fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.duan1_nhom7.DAO.GioHangDAO;
import com.example.duan1_nhom7.DTO.GioHang;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.MainActivity;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTiet_SP_gioHang extends Fragment {

    SanPham sanPham;
    String sizeCheck;
    TextView txtChiTietTenSp, txtChiTietGiaSP, txtChiTietMoTaSP, txtChiTietTongTien, txtChiTietSL;

    ImageView img_sp, btnSoLuongTang, btnSoLuongGiam;
    double donGia = 0;
    int soLuong;
    double tongTien;

    String mauCheck;

    GioHangDAO daoGioHang;

    public ChiTiet_SP_gioHang() {
        // Required empty public constructor
    }
    public ChiTiet_SP_gioHang(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiet_sp_giohang, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        txtChiTietTenSp = view.findViewById(R.id.txtChiTietTenSp);
        txtChiTietGiaSP = view.findViewById(R.id.txtChiTietGiaSP);
        txtChiTietMoTaSP = view.findViewById(R.id.txtChiTietMoTaSP);
        txtChiTietSL = view.findViewById(R.id.txtChiTietSL);
        img_sp = view.findViewById(R.id.imgCTSanPham);
        txtChiTietTongTien = view.findViewById(R.id.txtChiTietTongTien);
        btnSoLuongTang = view.findViewById(R.id.btnSoLuongTang);
        btnSoLuongGiam = view.findViewById(R.id.btnSoLuongGiam);

        daoGioHang = new GioHangDAO(getContext());



        RadioButton rdoMauDen = view.findViewById(R.id.rdoMauDen);
        RadioButton rdoMauTrang = view.findViewById(R.id.rdoMauTrang);
        RadioButton rdoMauVang = view.findViewById(R.id.rdoMauVang);
        RadioButton rdoMauXanh =view.findViewById(R.id.rdoMauXanh);

        int donGiaGoc = Integer.parseInt(sanPham.getGiaTienSP()+"");

        //Mau
        rdoMauDen.setChecked(true);
        mauCheck="Đen";
        donGia=0;
        rdoMauDen.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rdoMauTrang.setChecked(false);
                    rdoMauVang.setChecked(false);
                    rdoMauXanh.setChecked(false);
                    mauCheck="Đen";
                    donGia=50000;
                    tongTien=tinhTien(soLuong,donGia,donGiaGoc);
                    String mTinhTien=String.format("%,.0f",tongTien);
                    txtChiTietTongTien.setText(mTinhTien+"VNĐ");

                }
            }
        });
        rdoMauTrang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rdoMauDen.setChecked(false);
                    rdoMauVang.setChecked(false);
                    rdoMauXanh.setChecked(false);
                    mauCheck="Trắng";
                    donGia=30000;
                    tongTien=tinhTien(soLuong,donGia,donGiaGoc);
                    String mTinhTien=String.format("%,.0f",tongTien);
                    txtChiTietTongTien.setText(mTinhTien+"VNĐ");

                }
            }
        });

        rdoMauVang.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rdoMauTrang.setChecked(false);
                    rdoMauDen.setChecked(false);
                    rdoMauXanh.setChecked(false);
                    mauCheck="Vàng";
                    donGia=10000;
                    tongTien=tinhTien(soLuong,donGia,donGiaGoc);
                    String mTinhTien=String.format("%,.0f",tongTien);
                    txtChiTietTongTien.setText(mTinhTien+"VNĐ");

                }
            }
        });
        rdoMauXanh.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked){
                    rdoMauTrang.setChecked(false);
                    rdoMauDen.setChecked(false);
                    rdoMauVang.setChecked(false);
                    mauCheck="Trắng";
                    donGia=20000;
                    tongTien=tinhTien(soLuong,donGia,donGiaGoc);
                    String mTinhTien=String.format("%,.0f",tongTien);
                    txtChiTietTongTien.setText(mTinhTien+"VNĐ");

                }
            }
        });









//        Set số lượng
        soLuong = 1;
        txtChiTietSL.setText("0" + soLuong);
        btnSoLuongGiam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong > 1){
                    soLuong --;
                    if (soLuong < 10){
                        txtChiTietSL.setText("0" + soLuong);
                    }
                    else {
                        txtChiTietSL.setText(soLuong + "");
                    }
                    tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                    String mTinhTien = String.format("%,.0f", tongTien);
                    txtChiTietTongTien.setText(mTinhTien + "VNĐ");
                }
            }
        });

        btnSoLuongTang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (soLuong < sanPham.getSoLuongSP()) {
                    soLuong++;
                    if (soLuong < 10) {
                        txtChiTietSL.setText("0" + soLuong);
                    } else {
                        txtChiTietSL.setText(soLuong + "");
                    }
                    tongTien = tinhTien(soLuong, donGia, donGiaGoc);
                    String mTinhTien = String.format("%,.0f", tongTien);
                    txtChiTietTongTien.setText(mTinhTien + "VNĐ");
                } else {
                    Toast.makeText(getActivity(), "Đã quá giới hạn số lượng" + " " + soLuong, Toast.LENGTH_SHORT).show();
                }


            }
        });

//        Set Data cho các View
        txtChiTietTenSp.setText(sanPham.getTenSP());
        double giaSP = sanPham.getGiaTienSP();
        String mGiaSP = String.format("%,.0f", giaSP);
        txtChiTietGiaSP.setText(mGiaSP + "VNĐ");
        txtChiTietMoTaSP.setText(sanPham.getMoTaSP());

        Picasso.get().load(sanPham.getAnhSP()).into(img_sp);

        tongTien = tinhTien(soLuong, donGia, donGiaGoc);
        String mTinhTien = String.format("%,.0f", tongTien);
        txtChiTietTongTien.setText(mTinhTien + " VNĐ");

        AppCompatButton btnChiTietAddToCart = view.findViewById(R.id.btnChiTietAddToCart);

//        Thêm sự kiện Button Add
        btnChiTietAddToCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                GioHang gioHang = new GioHang(1, sanPham.getId_sanPham(),sanPham.getAnhSP(), sanPham.getTenSP(), soLuong, mauCheck, (int) (donGia+donGiaGoc));

                ArrayList<GioHang> outList = daoGioHang.checkValidGioHang(gioHang);
                if (outList.size() != 0){
//                - Có: Update số lượng
                    GioHang gioHang1 = outList.get(0);
                    int newSL = gioHang1.getSoLuong() + gioHang.getSoLuong();
                    gioHang.setSoLuong(newSL);
                    boolean kiemtra = daoGioHang.updateGioHang(gioHang);
                    if (kiemtra){
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Update SL Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
                else {
//                - Không: Thêm sản phẩm
                    boolean check = daoGioHang.addGiohang(gioHang);
                    if (check){
                        Toast.makeText(getContext(), "Thêm sản phẩm thành công!", Toast.LENGTH_SHORT).show();
                    }
                    else {
                        Toast.makeText(getContext(), "Fail!", Toast.LENGTH_SHORT).show();
                    }
                }
                loadFragment(new StoreFragment());
                MainActivity.bottomNavigationView.setSelectedItemId(R.id.pageBanHang);
            }
        });

    }

    public double tinhTien(int mSoLuong, double mDonGia, double mDonGiaGoc){
        double tongTien = 0;
        tongTien = mSoLuong * (mDonGia + mDonGiaGoc);
        return tongTien;
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = (getActivity()).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
