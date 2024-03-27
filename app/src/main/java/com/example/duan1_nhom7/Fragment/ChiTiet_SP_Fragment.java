package com.example.duan1_nhom7.Fragment;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.example.duan1_nhom7.DAO.DAOLoaiSP;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ChiTiet_SP_Fragment extends Fragment {
    SanPhamDAO dao;
    SanPham sanPham;
    DAOLoaiSP daoLoaiSP;

    public ChiTiet_SP_Fragment(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.chitiet_sp_admin_fragment,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView txtCTSPTenSp = view.findViewById(R.id.txtCTSPTenSp);
        TextView txtCTSPGiaSP = view.findViewById(R.id.txtCTSPGiaSP);
        TextView txtCTSPLoaiSP = view.findViewById(R.id.txtCTSPLoaiSP);
        TextView txtCTSPMoTaSP = view.findViewById(R.id.txtCTSPMoTaSP);
        TextView txtCTSPSluongSP = view.findViewById(R.id.txtCTSPSoLuongSP);
        ImageView img1 = view.findViewById(R.id.img_SP);
        dao = new SanPhamDAO(getContext());
        daoLoaiSP=new DAOLoaiSP(getContext());
        // set text cho view
        txtCTSPTenSp.setText(sanPham.getTenSP());

        int maLSP = sanPham.getId_Loai();
        String tenLSP = "";
        ArrayList<LoaiSP> listTheLoai = (ArrayList<LoaiSP>) daoLoaiSP.getAllLoaiSP();
        for (int i = 0; i < listTheLoai.size(); i++) {
            LoaiSP theLoai = listTheLoai.get(i);
            if (theLoai.getId()== maLSP){
                tenLSP = theLoai.getTenLoaiSP();
            }
        }
        txtCTSPLoaiSP.setText("Loại sản phẩm: " + tenLSP);
        txtCTSPMoTaSP.setText(sanPham.getMoTaSP());

        txtCTSPSluongSP.setText("Số lượng: "+sanPham.getSoLuongSP()+"");

        Picasso.get().load(sanPham.getAnhSP()).into(img1);

        txtCTSPGiaSP.setText(sanPham.getGiaTienSP() + "");
//        String outGia = String.format("%,.0f", sanPham.getGiaTienSP());
//        txtCTSPGiaSP.setText(outGia + " VNĐ");


    }
}
