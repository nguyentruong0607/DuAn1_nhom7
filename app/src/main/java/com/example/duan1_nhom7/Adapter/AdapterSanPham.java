package com.example.duan1_nhom7.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duan1_nhom7.DTO.SanPham;
import com.example.duan1_nhom7.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class AdapterSanPham extends RecyclerView.Adapter<AdapterSanPham.UserViewHolder> {

    private Context context;
    ArrayList<SanPham> list;
    BottomNavigationView bottomNavigationView;

    public AdapterSanPham(Context context, ArrayList<SanPham> list) {
        this.context = context;
        this.list = list;
    }

    @SuppressLint("MissingInflatedId")
    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.card_view_sanpham, parent, false);
        bottomNavigationView = view.findViewById(R.id.navigation);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
//        Set Data cho List Item
        SanPham sanPham = list.get(position);
        holder.TenSanPham.setText(sanPham.getTenSP());
        holder.GiaTien.setText(String.valueOf(sanPham.getGiaTienSP()+"VNĐ"));
        Picasso.get().load(sanPham.getAnhSP()).into(holder.img_SanPham);




    }

    @Override
    public int getItemCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    public class UserViewHolder extends RecyclerView.ViewHolder {
        private CardView cardView;
        private ImageView info_sanpham, img_SanPham;
        private TextView TenSanPham;
        private TextView GiaTien;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            cardView = itemView.findViewById(R.id.cardview_sanPham);
//            info_sanpham = itemView.findViewById(R.id.info_sanpham);
            img_SanPham = itemView.findViewById(R.id.img_sanpham);
            TenSanPham = itemView.findViewById(R.id.ten_sanpham);
            GiaTien = itemView.findViewById(R.id.gia_sanpham);
        }
    }

    private void loadFragment(Fragment fragment) {
        FragmentTransaction transaction = ((FragmentActivity) context).getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frame_container, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }
}
