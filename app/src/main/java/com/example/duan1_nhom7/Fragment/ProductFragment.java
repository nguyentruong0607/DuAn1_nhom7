package com.example.duan1_nhom7.Fragment;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.Adapter.AdapterSanPham;
import com.example.duan1_nhom7.DAO.SanPhamDAO;
import com.example.duan1_nhom7.DTO.LoaiSP;
import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;

import java.util.ArrayList;
import java.util.List;

public class ProductFragment extends Fragment {
    List<SanPham> list;
    AdapterSanPham adapterSanPham;
    SanPhamDAO sanPhamDAO;
    RecyclerView rcvProduct;
    SanPham sanPham;
    AutoCompleteTextView ed_search;
    TextView txt_search;
    ArrayList<LoaiSP> listLoai;
    public ProductFragment(SanPham sanPham) {
        this.sanPham = sanPham;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.product_fragment, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rcvProduct=view.findViewById(R.id.rcvProduct);
        txt_search=view.findViewById(R.id.txt_Search);
        ed_search=view.findViewById(R.id.ed_search);
        sanPhamDAO=new SanPhamDAO(getContext());


//        if (sanPham != null) {
//            int idLoai = sanPham.getId_Loai();
            list = sanPhamDAO.getProductsByCategoryId(sanPham.getId_Loai());
            adapterSanPham = new AdapterSanPham(getContext(), (ArrayList<SanPham>) list);
            rcvProduct.setAdapter(adapterSanPham);
        //}



    }
}
